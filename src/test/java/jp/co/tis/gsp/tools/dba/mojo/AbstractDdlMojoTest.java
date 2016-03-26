package jp.co.tis.gsp.tools.dba.mojo;

import java.io.File;
import java.io.FileInputStream;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

import org.apache.maven.DefaultMaven;
import org.apache.maven.Maven;
import org.apache.maven.artifact.Artifact;
import org.apache.maven.artifact.installer.ArtifactInstaller;
import org.apache.maven.artifact.repository.ArtifactRepository;
import org.apache.maven.artifact.repository.ArtifactRepositoryPolicy;
import org.apache.maven.artifact.repository.MavenArtifactRepository;
import org.apache.maven.artifact.repository.layout.DefaultRepositoryLayout;
import org.apache.maven.execution.DefaultMavenExecutionRequest;
import org.apache.maven.execution.DefaultMavenExecutionResult;
import org.apache.maven.execution.MavenExecutionRequest;
import org.apache.maven.execution.MavenExecutionRequestPopulator;
import org.apache.maven.execution.MavenExecutionResult;
import org.apache.maven.execution.MavenSession;
import org.apache.maven.model.Plugin;
import org.apache.maven.plugin.LegacySupport;
import org.apache.maven.plugin.Mojo;
import org.apache.maven.plugin.MojoExecution;
import org.apache.maven.plugin.PluginParameterExpressionEvaluator;
import org.apache.maven.plugin.descriptor.MojoDescriptor;
import org.apache.maven.plugin.testing.AbstractMojoTestCase;
import org.apache.maven.project.MavenProject;
import org.apache.maven.project.ProjectBuilder;
import org.apache.maven.project.ProjectBuildingRequest;
import org.apache.maven.repository.RepositorySystem;
import org.apache.maven.settings.MavenSettingsBuilder;
import org.apache.maven.settings.Settings;
import org.codehaus.plexus.component.configurator.ComponentConfigurationException;
import org.codehaus.plexus.component.configurator.ComponentConfigurator;
import org.codehaus.plexus.component.configurator.expression.ExpressionEvaluator;
import org.codehaus.plexus.configuration.PlexusConfiguration;
import org.codehaus.plexus.configuration.xml.XmlPlexusConfiguration;
import org.codehaus.plexus.util.xml.Xpp3Dom;
import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.TestRule;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.seasar.framework.util.StringUtil;
import org.sonatype.aether.util.DefaultRepositorySystemSession;

import jp.co.tis.gsp.test.util.MojoTestFixture;
import jp.co.tis.gsp.test.util.TestCasePattern;

@RunWith(BlockJUnit4ClassRunner.class)
public abstract class AbstractDdlMojoTest<E> extends AbstractMojoTestCase {

	public static final String GENERATE_DDL = "generate-ddl";
	public static final String EXECUTE_DDL = "execute-ddl";
	public static final String GENERATE_ENTITY = "generate-entity";
	public static final String EXPORT_SCHEMA = "export-schema";
	public static final String IMPORT_SCHEMA = "import-schema";
	public static final String LOAD_DATA = "load-data";

	protected Type mojoType = ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
	protected E mojo;
	protected MavenExecutionRequest currentMavenExecutionRequest;

	protected MavenProject currentProject;

	/** 各Mojoテストメソッド実行時に参照される`{@code MojoTestFixture}`のリスト */
	List<MojoTestFixture> mojoTestFixtureList;

	@Before
	public void setUp() throws Exception {
		super.setUp();
	}

	/**
	 * テストメソッド実行時に、`{@code TestCasePattern}`アノテーションから`
	 * {@code mojoTestFixtureList}`を生成.
	 */
	@Rule
	public TestRule caseSetUpper = new TestWatcher() {
		protected void starting(Description d) {

			TestCasePattern tp = d.getAnnotation(TestCasePattern.class);
			if (tp != null) {
				mojoTestFixtureList = new ArrayList<MojoTestFixture>();
				for (TestDB db : getDbCase(tp)) {
					mojoTestFixtureList.add(new MojoTestFixture(d.getTestClass(), tp.testCase(), db));
				}
			}

		};

		// プロパティファイルとTestCasePattern#testDb要素から実行するデータベースパターンを取得する
		private TestDB[] getDbCase(TestCasePattern tp) {

			// プロパティファイルにtestDBが設定されている場合。
			// 設定されたtestDBがアノテーションに含まれている場合はそのtestDB要素だけの配列を返す
			try {
				String propPath = getMojoTestRoot() + "mojoTest.properties";
				Properties prop = new Properties();
				prop.load(new FileInputStream(new File(propPath)));

				String testDb = prop.getProperty("testDB");
				if (!StringUtil.isBlank(testDb) && Arrays.asList(tp.testDb()).contains(TestDB.valueOf(testDb))) {
					return new TestDB[] { TestDB.valueOf(testDb) };
				}

			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}

			// 上記以外は、アノテーション設定値をそのまま返す
			return tp.testDb();

		}

		protected void finished(Description description) {
			mojoTestFixtureList = null;
		}
	};

	/**
	 * テストデータベースの列挙子
	 */
	public enum TestDB {
		oracle, db2, postgresql, mysql, sqlserver, h2;
	}

	/**
	 * Mojoテストのルートディレクトリを取得する。
	 * 
	 * @return ルートディレクトリのパス
	 * @throws Exception
	 */
	protected String getMojoTestRoot() throws Exception {
		return this.getClass().getResource("").getPath().replaceFirst(System.getProperty("file.separator") + "$", "");
	}

	/**
	 * テストクラス/テストケース/テストＤＢ までの絶対パスを取得する。
	 * 
	 * @param mf
	 * @return - パス
	 * @throws Exception
	 */
	protected String getTestCaseDBPath(MojoTestFixture mf) throws Exception {
		Class<?> mojoClass = Class.forName(mojoType.getTypeName());
		String mojoSimpleName = mojoClass.getSimpleName();
		return getMojoTestRoot() + mojoSimpleName + "/" + mf.caseName + "/" + mf.testDb;
	}

	/**
	 * 期待値ファイルが格納されているルートフォルダ.
	 * 
	 * Mojo実行後に生成されるファイルと突合する際に利用する.
	 * 
	 * @param mf
	 *            - Mojo生成パラメータ
	 * @return 期待値ファイルが格納されているルートフォルダ
	 */
	protected String getExpectedPath(MojoTestFixture mf) throws Exception {
		Class<?> mojoClass = Class.forName(mojoType.getTypeName());
		String mojoSimpleName = mojoClass.getSimpleName();
		return getMojoTestRoot() + mojoSimpleName + "/" + mf.caseName + "/" + mf.testDb + "/expected";
	}

	/**
	 * 指定ローカルリポジトリにMavenのArtifactをインストールします。
	 * 
	 * @param artifact
	 *            - アーティファクト
	 * @param localRep
	 *            - ローカルリポジトリ
	 * @throws Exception
	 *             - 例外
	 */
	protected void installArtifactToTestRepo(Artifact artifact, ArtifactRepository localRep) throws Exception {
		ArtifactInstaller ai = this.lookup(ArtifactInstaller.class);
		ai.install(artifact.getFile(), artifact, localRep);
	}

	/**
	 * オリジナルの実装がおかしいと思われるのでコピー修正実装。
	 * 
	 */
	protected Mojo lookupConfiguredMojo(MavenSession session, MojoExecution execution)
			throws Exception, ComponentConfigurationException {
		MavenProject project = session.getCurrentProject();
		MojoDescriptor mojoDescriptor = execution.getMojoDescriptor();

		Mojo mojo = (Mojo) lookup(mojoDescriptor.getRole(), mojoDescriptor.getRoleHint());

		ExpressionEvaluator evaluator = new PluginParameterExpressionEvaluator(session, execution);

		Xpp3Dom configuration = null;
		Plugin plugin = project.getPlugin(mojoDescriptor.getPluginDescriptor().getPluginLookupKey());
		if (plugin != null) {
			configuration = (Xpp3Dom) plugin.getConfiguration();
		}
		if (configuration == null) {
			configuration = new Xpp3Dom("configuration");
		}

		// ここ。マージ元、マージ先を逆にする。
		configuration = Xpp3Dom.mergeXpp3Dom(configuration, execution.getConfiguration());

		PlexusConfiguration pluginConfiguration = new XmlPlexusConfiguration(configuration);
		ComponentConfigurator configurator = getContainer().lookup(ComponentConfigurator.class, "basic");
		configurator.configureComponent(mojo, pluginConfiguration, evaluator, getContainer().getContainerRealm(), null);

		return mojo;
	}

	protected E lookupConfiguredMojo(File pom, String goal, TestDB testDb) throws Exception {

		// Mojoテストリソースディレクトリのパスをシステムプロパティに設定しておく
		System.setProperty("MojoTestRoot", getMojoTestRoot());

		// Maven実行リクエスト初期化
		MavenExecutionRequest executionRequest = new DefaultMavenExecutionRequest();

		// テスト用のsettingファイルで初期化
		File settings = new File(this.getClass().getResource("settings.xml").getPath());
		MavenExecutionRequestPopulator populator = getContainer().lookup(MavenExecutionRequestPopulator.class);
		MavenSettingsBuilder mb = this.lookup(MavenSettingsBuilder.class);
		Settings st = mb.buildSettings(settings);
		populator.populateFromSettings(executionRequest, st);

		// カレントディレクトリをセット
		executionRequest.setBaseDirectory(pom.getParentFile());

		// プロファイルを指定DBのプロファイルにセット
		executionRequest.setActiveProfiles(Collections.singletonList(testDb.name()));

		// ローカルリポジトリオブジェクトのセット
		executionRequest.setLocalRepository(new MavenArtifactRepository(RepositorySystem.DEFAULT_LOCAL_REPO_ID,
				executionRequest.getLocalRepositoryPath().toURI().toURL().toString(), new DefaultRepositoryLayout(),
				new ArtifactRepositoryPolicy(), new ArtifactRepositoryPolicy()));

		// 実行プロジェクトを作成
		ProjectBuildingRequest buildingRequest = executionRequest.getProjectBuildingRequest();
		DefaultMaven maven = (DefaultMaven) getContainer().lookup(Maven.class);
		DefaultRepositorySystemSession repoSession = (DefaultRepositorySystemSession) maven
				.newRepositorySession(executionRequest);
		repoSession.setOffline(true);
		buildingRequest.setRepositorySession(repoSession);
		ProjectBuilder projectBuilder = this.lookup(ProjectBuilder.class);
		MavenProject project = projectBuilder.build(pom, buildingRequest).getProject();

		currentMavenExecutionRequest = executionRequest;

		// 指定ゴールを持つMojoを取得
		Mojo mojo = this.lookupConfiguredMojo(project, goal);

		currentProject = project;
		return (E) mojo;
	}

	@Override
	protected MavenSession newMavenSession(MavenProject project) {
		MavenExecutionResult result = new DefaultMavenExecutionResult();

		MavenSession session = new MavenSession(getContainer(),
				project.getProjectBuildingRequest().getRepositorySession(), currentMavenExecutionRequest, result);
		session.setCurrentProject(project);
		session.setProjects(Arrays.asList(project));

		try {
			LegacySupport legacySupport = this.lookup(LegacySupport.class);
			legacySupport.setSession(session);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return session;
	}

}
