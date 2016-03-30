package jp.co.tis.gsp.tools.dba.mojo;

import java.io.File;

import org.apache.maven.artifact.Artifact;
import org.apache.maven.plugin.Mojo;
import org.apache.maven.repository.RepositorySystem;
import org.junit.Test;
import org.junit.experimental.theories.Theories;
import org.junit.runner.RunWith;

import jp.co.tis.gsp.test.util.MojoTestFixture;
import jp.co.tis.gsp.test.util.TestCasePattern;
import jp.co.tis.gsp.test.util.TestDB;

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
			
			ExecuteDdlMojoTest ddlTest = new ExecuteDdlMojoTest();
			ddlTest.setUp();
			ExecuteDdlMojo ddlMojo = ddlTest.lookupConfiguredMojo(pom, EXECUTE_DDL, mf.testDb);
			ddlMojo.execute();

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
