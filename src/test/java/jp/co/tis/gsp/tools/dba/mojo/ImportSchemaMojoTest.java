package jp.co.tis.gsp.tools.dba.mojo;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.io.File;

import org.apache.maven.artifact.Artifact;
import org.apache.maven.plugin.Mojo;
import org.apache.maven.repository.RepositorySystem;
import org.junit.Test;
import org.junit.experimental.theories.Theories;
import org.junit.runner.RunWith;

import jp.co.tis.gsp.test.util.DBTestUtil;
import jp.co.tis.gsp.test.util.MojoTestFixture;
import jp.co.tis.gsp.test.util.TestCasePattern;
import jp.co.tis.gsp.test.util.TestDB;

@RunWith(Theories.class)
public class ImportSchemaMojoTest extends AbstractDdlMojoTest<ImportSchemaMojo> {

	/**
	 * データベースの基本機能を使ったImportのテスト。
	 * 
	 * スキーマが存在しない場合のテストを兼ねる。
	 * 
	 * <ul>
	 * <li>主キー</li>
	 * <li>関連・外部キー</li>
	 * <li>シーケンス</li>
	 * <li>ビュー</li>
	 * </ul>
	 * 
	 * @throws Exception
	 */
	@Test
	@TestCasePattern(testCase = "basic_test_schema_nonexist", testDb = { TestDB.postgresql, TestDB.h2, TestDB.mysql })
	public void basicNonSchemaExistTest() throws Exception {

		// 指定されたケース及びテスト対象のDBだけループ
		for (MojoTestFixture mf : mojoTestFixtureList) {

			// テストケース対象プロジェクトのpom.xmlを取得
			File pom = new File(getTestCaseDBPath(mf) + "/pom.xml");

			// currentProjectのセットアップ。 ImportSchemaMojoのセットアップ。
			ImportSchemaMojo mojo = this.lookupConfiguredMojo(pom, IMPORT_SCHEMA, mf.testDb);

			// テスト用ダンプjarをインストール
			RepositorySystem rs = this.lookup(RepositorySystem.class);
			Artifact artifact = rs.createArtifact("jp.co.tis", "gsp-testdata", "1.0.0", "jar");
			artifact.setFile(new File(getTestCaseDBPath(mf) + "/gsp-test-testdata-1.0.0.jar"));
			installArtifactToTestRepo(artifact, currentProject.getProjectBuildingRequest().getLocalRepository());

			// スキーマのドロップ
			DBTestUtil.dropSchema(mojo.schema, mojo.user, mojo.password, mojo.adminUser, mojo.adminPassword, mojo.url,
					mojo.driver, mf.testDb);

			// スキーマが存在しない状態で。
			mojo.execute();

			// 検証
			String sql = "SELECT COUNT(*) FROM TEST_TBL1;";
			int cnt = 0;
			cnt = DBTestUtil.getCount(sql, mojo.url, mojo.user, mojo.password);
			assertThat(cnt, is(1000));

			sql = "SELECT COUNT(*) FROM TEST_TBL2;";
			cnt = DBTestUtil.getCount(sql, mojo.url, mojo.user, mojo.password);
			assertThat(cnt, is(1000));

			sql = "SELECT COUNT(*) FROM TEST_TBL3;";
			cnt = DBTestUtil.getCount(sql, mojo.url, mojo.user, mojo.password);
			assertThat(cnt, is(1000));
		}
	}

	/**
	 * スキーマが既に存在する場合のテスト。
	 * 
	 */
	@Test
	@TestCasePattern(testCase = "basic_test_schema_exist", testDb = { TestDB.postgresql, TestDB.h2, TestDB.mysql })
	public void basicSchemaExistTest() throws Exception {

		// 指定されたケース及びテスト対象のDBだけループ
		for (MojoTestFixture mf : mojoTestFixtureList) {

			// テストケース対象プロジェクトのpom.xmlを取得
			File pom = new File(getTestCaseDBPath(mf) + "/pom.xml");

			// currentProjectのセットアップ。 ImportSchemaMojoのセットアップ。
			ImportSchemaMojo mojo = this.lookupConfiguredMojo(pom, IMPORT_SCHEMA, mf.testDb);

			// テスト用ダンプjarをインストール
			RepositorySystem rs = this.lookup(RepositorySystem.class);
			Artifact artifact = rs.createArtifact("jp.co.tis", "gsp-testdata", "1.0.0", "jar");
			artifact.setFile(new File(getTestCaseDBPath(mf) + "/gsp-test-testdata-1.0.0.jar"));
			installArtifactToTestRepo(artifact, currentProject.getProjectBuildingRequest().getLocalRepository());

			// スキーマを事前に作成
			ExecuteDdlMojoTest ddlTest = new ExecuteDdlMojoTest();
			ddlTest.setUp();
			ExecuteDdlMojo ddlMojo = ddlTest.lookupConfiguredMojo(pom, EXECUTE_DDL, mf.testDb);
			ddlMojo.execute();

			// スキーマが既に存在する状態で。
			mojo = this.lookupConfiguredMojo(pom, IMPORT_SCHEMA, mf.testDb);
			mojo.execute();

			// 検証
			String sql = "SELECT COUNT(*) FROM TEST_TBL1;";
			int cnt = 0;
			cnt = DBTestUtil.getCount(sql, mojo.url, mojo.user, mojo.password);
			assertThat(cnt, is(1000));

			sql = "SELECT COUNT(*) FROM TEST_TBL2;";
			cnt = DBTestUtil.getCount(sql, mojo.url, mojo.user, mojo.password);
			assertThat(cnt, is(1000));

			sql = "SELECT COUNT(*) FROM TEST_TBL3;";
			cnt = DBTestUtil.getCount(sql, mojo.url, mojo.user, mojo.password);
			assertThat(cnt, is(1000));

		}
	}

}
