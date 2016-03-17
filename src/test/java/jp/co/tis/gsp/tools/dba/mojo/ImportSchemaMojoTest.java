package jp.co.tis.gsp.tools.dba.mojo;

import java.io.File;
import java.util.Collections;

import org.apache.maven.DefaultMaven;
import org.apache.maven.Maven;
import org.apache.maven.artifact.Artifact;
import org.apache.maven.artifact.repository.ArtifactRepositoryPolicy;
import org.apache.maven.artifact.repository.MavenArtifactRepository;
import org.apache.maven.artifact.repository.layout.DefaultRepositoryLayout;
import org.apache.maven.execution.DefaultMavenExecutionRequest;
import org.apache.maven.execution.DefaultMavenExecutionResult;
import org.apache.maven.execution.MavenExecutionRequest;
import org.apache.maven.execution.MavenExecutionRequestPopulator;
import org.apache.maven.execution.MavenSession;
import org.apache.maven.plugin.LegacySupport;
import org.apache.maven.project.MavenProject;
import org.apache.maven.repository.RepositorySystem;
import org.junit.Test;
import org.junit.experimental.theories.Theories;
import org.junit.runner.RunWith;
import org.sonatype.aether.util.DefaultRepositorySystemSession;

import jp.co.tis.gsp.test.util.MojoTestFixture;
import jp.co.tis.gsp.test.util.TestCasePattern;

@RunWith(Theories.class)
public class ImportSchemaMojoTest extends AbstractDdlMojoTest<ImportSchemaMojo> {

	@Override
	protected ImportSchemaMojo setUpMojo(MojoTestFixture mf, String caseMojoParamPath) throws Exception {

		// plexusセットアップ
		super.setUp();

		// Maven実行セッションのモック
		setUpDummyMavenSession();

		// Mojoにパラメータ設定
		mojo = super.setUpMojo(mf, caseMojoParamPath);

		// RepositorySystemコンポーネント設定
		RepositorySystem rs = this.lookup(RepositorySystem.class);
		mojo.repositorySystem = rs;

		// テスト用localRepositoryを設定
		String localRepoPath = this.getClass().getResource("ImportSchemaMojo/localRepo").toURI().toURL().toString();
		mojo.localRepository = new MavenArtifactRepository("local", localRepoPath, new DefaultRepositoryLayout(),
				new ArtifactRepositoryPolicy(true, ArtifactRepositoryPolicy.UPDATE_POLICY_NEVER,
						ArtifactRepositoryPolicy.CHECKSUM_POLICY_WARN),
				new ArtifactRepositoryPolicy(true, ArtifactRepositoryPolicy.UPDATE_POLICY_NEVER,
						ArtifactRepositoryPolicy.CHECKSUM_POLICY_WARN));

		// データベースダンプJarをテスト用ローカルリポジトリにインストール
		Artifact artifact = mojo.repositorySystem.createArtifact(mojo.groupId, mojo.artifactId, mojo.version, "jar");
		artifact.setFile(
				new File(getTestCaseDBPath(mf) + "/" + mojo.artifactId + "-testdata-" + mojo.version + ".jar"));
		installArtifactToTestRepo(artifact, mojo.localRepository);

		// Mavenプロジェクト設定。ローカルリポジトリのみ参照。
		MavenProject pj = new MavenProject();
		pj.setRemoteArtifactRepositories(Collections.singletonList(mojo.localRepository));
		mojo.project = pj;

		return mojo;
	}

	/**
	 * Maven実行機能で参照されるセッションのダミーをセットアップする。
	 * 
	 * @throws Exception
	 */
	protected void setUpDummyMavenSession() throws Exception {

		MavenExecutionRequest request = new DefaultMavenExecutionRequest();
		MavenExecutionRequestPopulator populator = getContainer().lookup(MavenExecutionRequestPopulator.class);
		populator.populateDefaults(request);
		request.setOffline(true);
		DefaultMaven maven = (DefaultMaven) getContainer().lookup(Maven.class);
		DefaultRepositorySystemSession repoSession = (DefaultRepositorySystemSession) maven
				.newRepositorySession(request);
		repoSession.setOffline(true);
		request.getProjectBuildingRequest().setRepositorySession(repoSession);

		LegacySupport legacySupport = this.lookup(LegacySupport.class);

		legacySupport
				.setSession(new MavenSession(getContainer(), repoSession, request, new DefaultMavenExecutionResult()));

	}

	/**
	 * 様々なデータ型でのDDL生成テスト。
	 * 
	 * @throws Exception
	 */
	@Test
	@TestCasePattern(testCase = "case1", testDb = { TestDB.oracle, TestDB.postgresql, TestDB.h2, TestDB.mysql })
	public void testCase1() throws Exception {

		// 指定されたケース及びテスト対象のDBだけループ
		for (MojoTestFixture mf : mojoTestFixtureList) {

			// ケース、データベースに応じてmojoにパラメータをバインドしてmojoを生成
			mojo = setUpMojo(mf, getTestCaseDBPath(mf) + "/mojo_pram.properties");

			// mojo実行検証
			mojo.execute();

		}
	}

}
