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
import org.apache.maven.plugin.Mojo;
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

	/**
	 * 基本機能のテスト。
	 * 
	 * @throws Exception
	 */
	@Test
	@TestCasePattern(testCase = "basic_test", testDb = { TestDB.oracle, TestDB.postgresql, TestDB.h2, TestDB.mysql })
	public void basicTest() throws Exception {

		// 指定されたケース及びテスト対象のDBだけループ
		for (MojoTestFixture mf : mojoTestFixtureList) {

			// テストケース対象プロジェクトのpom.xmlを取得
			File pom = new File(getTestCaseDBPath(mf) + "/pom.xml");

			// pom.xmlより指定ゴールのMojoを取得し実行。Mavenプロファイルを指定する(DB)
			Mojo mojo = this.lookupConfiguredMojo(pom, IMPORT_SCHEMA, mf.testDb);
			
			// テスト用ダンプjarをインストール
			RepositorySystem rs = this.lookup(RepositorySystem.class);
			Artifact artifact = rs.createArtifact("jp.co.tis", "gsp-testdata", "1.0.0", "jar");
			artifact.setFile(new File(getTestCaseDBPath(mf) + "/gsp-test-testdata-1.0.0.jar"));
			installArtifactToTestRepo(artifact, currentProject.getProjectBuildingRequest().getLocalRepository());

			mojo.execute();

		}
	}



}
