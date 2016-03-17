package jp.co.tis.gsp.tools.dba.mojo;

import java.io.File;
import java.io.FileInputStream;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import org.apache.maven.artifact.Artifact;
import org.apache.maven.artifact.installer.ArtifactInstaller;
import org.apache.maven.artifact.repository.ArtifactRepository;
import org.codehaus.plexus.PlexusTestCase;
import org.dozer.DozerBeanMapper;
import org.junit.Rule;
import org.junit.experimental.theories.Theories;
import org.junit.rules.TestRule;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import org.junit.runner.RunWith;
import org.seasar.framework.util.StringUtil;

import jp.co.tis.gsp.test.util.MojoTestFixture;
import jp.co.tis.gsp.test.util.TestCasePattern;

@RunWith(Theories.class)
public abstract class AbstractDdlMojoTest<E> extends PlexusTestCase {

	protected Type mojoType = ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
	protected E mojo;

	/** 各Mojoテストメソッド実行時に参照される`{@code MojoTestFixture}`のリスト */
	List<MojoTestFixture> mojoTestFixtureList;

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
				String propPath = Thread.currentThread().getContextClassLoader()
						.getResource("jp/co/tis/gsp/tools/dba/mojo/mojoTest.properties").getPath();
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
	 * `{@code PatternFixture}`よりテスト対象となるMojoクラスを生成.
	 * 
	 * @param mf
	 *            - Mojo生成パラメータ
	 * @return テスト対象のMojo
	 */
	protected E setUpMojo(MojoTestFixture mf, String caseMojoParamPath) throws Exception {
		Object rtn = null;
		try {
			Class<?> mojoClass = Class.forName(mojoType.getTypeName());
			String mojoSimpleName = mojoClass.getSimpleName();

			// 基本Mojoパラメータを設定
			String propPath = this.getClass().getResource(mf.testDb + ".properties").getPath();
			Properties prop = new Properties();
			prop.load(new FileInputStream(new File(propPath)));
			DozerBeanMapper mapper = new DozerBeanMapper();
			List<String> myMappingFiles = new ArrayList<String>();

			String abstractDbaMojoMapper = this.getClass().getResource("AbstractDbaMojoMapper.xml").toURI().toURL()
					.toString();
			String mojoMapper = this.getClass().getResource(mojoSimpleName + "/MojoMapper.xml").toURI().toURL()
					.toString();

			myMappingFiles.add(abstractDbaMojoMapper);
			myMappingFiles.add(mojoMapper);

			mapper.setMappingFiles(myMappingFiles);
			rtn = mapper.map(prop, mojoClass);

			// ケース独自のMojoパラメータを設定。後でこいつで基本Mojoパラメータを上書きする。
			prop = new Properties();
			prop.load(new FileInputStream(new File(caseMojoParamPath)));

			// 基本Mojoパラメータに上書きマージ
			mapper.map(prop, rtn);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return (E) rtn;
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
		return this.getClass().getResource(mojoSimpleName + "/" + mf.caseName + "/" + mf.testDb).getPath().toString();
	}

	/**
	 * テストデータベースの列挙子
	 */
	public enum TestDB {
		oracle, db2, postgresql, mysql, sqlserver, h2;
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

		return new File(this.getClass().getResource(mojoSimpleName + "/" + mf.caseName + "/" + mf.testDb + "/expected")
				.getPath()).getAbsolutePath();

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

}
