package jp.co.tis.gsp.tools.dba.mojo;


import java.io.File;

import org.apache.maven.plugin.Mojo;
import org.junit.Test;

import jp.co.tis.gsp.test.util.DBTestUtil;
import jp.co.tis.gsp.test.util.MojoTestFixture;
import jp.co.tis.gsp.test.util.TestCasePattern;
import jp.co.tis.gsp.test.util.TestDB;
import static org.junit.Assert.assertThat;
import static org.hamcrest.CoreMatchers.*;

public class LoadDataMojoTest extends AbstractDdlMojoTest<LoadDataMojo> {

	/**
	 * GSPでサポートするデータ型でDDL生成テスト。
	 * 
	 * @throws Exception
	 */
	@Test
	@TestCasePattern(testCase = "type_test", testDb = { TestDB.oracle, TestDB.postgresql, TestDB.db2, TestDB.h2,
			TestDB.sqlserver, TestDB.mysql })
	public void testType() throws Exception {

		// 指定されたケース及びテスト対象のDBだけループ
		for (MojoTestFixture mf : mojoTestFixtureList) {

			// テストケース対象プロジェクトのpom.xmlを取得
			File pom = new File(getTestCaseDBPath(mf) + "/pom.xml");

			ExecuteDdlMojoTest ddlTest = new ExecuteDdlMojoTest();
			ddlTest.setUp();
			ExecuteDdlMojo ddlMojo = ddlTest.lookupConfiguredMojo(pom, EXECUTE_DDL, mf.testDb);
			ddlMojo.execute();

			// pom.xmlより指定ゴールのMojoを取得し実行
			LoadDataMojo mojo = this.lookupConfiguredMojo(pom, LOAD_DATA, mf.testDb);
			mojo.execute();
			
			// 検証
			String sql = "SELECT COUNT(*) FROM TYPETEST;";
			int cnt = 0;
			cnt = DBTestUtil.getCount(sql, mojo.url, mojo.user, mojo.password);
			assertThat(cnt, is(1));
		
		}
	}

	/**
	 * 外部制約・データの依存関係を考慮したテスト。
	 * 
	 * @throws Exception
	 */
	@Test
	@TestCasePattern(testCase = "depend_test", testDb = { TestDB.oracle, TestDB.postgresql, TestDB.db2, TestDB.h2,
			TestDB.sqlserver, TestDB.mysql })
	public void testDepend() throws Exception {

		// 指定されたケース及びテスト対象のDBだけループ
		for (MojoTestFixture mf : mojoTestFixtureList) {

			// テストケース対象プロジェクトのpom.xmlを取得
			File pom = new File(getTestCaseDBPath(mf) + "/pom.xml");

			ExecuteDdlMojoTest ddlTest = new ExecuteDdlMojoTest();
			ddlTest.setUp();
			ExecuteDdlMojo ddlMojo = ddlTest.lookupConfiguredMojo(pom, EXECUTE_DDL, mf.testDb);
			ddlMojo.execute();

			// pom.xmlより指定ゴールのMojoを取得し実行
			LoadDataMojo mojo = this.lookupConfiguredMojo(pom, LOAD_DATA, mf.testDb);
			mojo.execute();
			
			// 検証
			String sql = "SELECT COUNT(*) FROM A_TABLE;";
			int cnt = 0;
			cnt = DBTestUtil.getCount(sql, mojo.url, mojo.user, mojo.password);
			assertThat(cnt, is(300));
			
			sql = "SELECT COUNT(*) FROM B_TABLE;";
			cnt = DBTestUtil.getCount(sql, mojo.url, mojo.user, mojo.password);
			assertThat(cnt, is(300));
			
			sql = "SELECT COUNT(*) FROM C_TABLE;";
			cnt = DBTestUtil.getCount(sql, mojo.url, mojo.user, mojo.password);
			assertThat(cnt, is(300));
			
			sql = "SELECT COUNT(*) FROM D_TABLE;";
			cnt = DBTestUtil.getCount(sql, mojo.url, mojo.user, mojo.password);
			assertThat(cnt, is(300));

		}
	}

	/**
	 * ユーザ名とスキーマ名が異なる場合のテスト。
	 * 
	 * @throws Exception
	 */
	@Test
	@TestCasePattern(testCase = "another_schema", testDb = { TestDB.oracle, TestDB.postgresql, TestDB.db2,
			TestDB.sqlserver, TestDB.mysql })
	public void testAnotherSchema() throws Exception {

		// 指定されたケース及びテスト対象のDBだけループ
		for (MojoTestFixture mf : mojoTestFixtureList) {

			// テストケース対象プロジェクトのpom.xmlを取得
			File pom = new File(getTestCaseDBPath(mf) + "/pom.xml");

			ExecuteDdlMojoTest ddlTest = new ExecuteDdlMojoTest();
			ddlTest.setUp();
			ExecuteDdlMojo ddlMojo = ddlTest.lookupConfiguredMojo(pom, EXECUTE_DDL, mf.testDb);
			ddlMojo.execute();

			// pom.xmlより指定ゴールのMojoを取得し実行
			LoadDataMojo mojo = this.lookupConfiguredMojo(pom, LOAD_DATA, mf.testDb);
			mojo.execute();
			
			// 検証
			String sql = "SELECT COUNT(*) FROM A_TABLE;";
			int cnt = 0;
			cnt = DBTestUtil.getCount(sql, mojo.url, mojo.user, mojo.password);
			assertThat(cnt, is(300));
			
			sql = "SELECT COUNT(*) FROM B_TABLE;";
			cnt = DBTestUtil.getCount(sql, mojo.url, mojo.user, mojo.password);
			assertThat(cnt, is(300));
			
			sql = "SELECT COUNT(*) FROM C_TABLE;";
			cnt = DBTestUtil.getCount(sql, mojo.url, mojo.user, mojo.password);
			assertThat(cnt, is(300));
			
			sql = "SELECT COUNT(*) FROM D_TABLE;";
			cnt = DBTestUtil.getCount(sql, mojo.url, mojo.user, mojo.password);
			assertThat(cnt, is(300));

		}
	}
	
	/**
	 * パラメータ：specifiedEncodingFilesのテスト。
	 * 
	 * @throws Exception
	 */
	@Test
	@TestCasePattern(testCase = "specifiedEncodingFiles_test", testDb = { TestDB.oracle, TestDB.postgresql, TestDB.db2, TestDB.h2,
			TestDB.sqlserver, TestDB.mysql })
	public void testSpecifiedEncodingFiles() throws Exception {

		// 指定されたケース及びテスト対象のDBだけループ
		for (MojoTestFixture mf : mojoTestFixtureList) {

			// テストケース対象プロジェクトのpom.xmlを取得
			File pom = new File(getTestCaseDBPath(mf) + "/pom.xml");

			ExecuteDdlMojoTest ddlTest = new ExecuteDdlMojoTest();
			ddlTest.setUp();
			ExecuteDdlMojo ddlMojo = ddlTest.lookupConfiguredMojo(pom, EXECUTE_DDL, mf.testDb);
			ddlMojo.execute();

			// pom.xmlより指定ゴールのMojoを取得し実行
			LoadDataMojo mojo = this.lookupConfiguredMojo(pom, LOAD_DATA, mf.testDb);
			mojo.execute();
			
			// 検証
			String sql = "SELECT COUNT(*) FROM A_TABLE;";
			int cnt = 0;
			cnt = DBTestUtil.getCount(sql, mojo.url, mojo.user, mojo.password);
			assertThat(cnt, is(300));
			
			sql = "SELECT COUNT(*) FROM B_TABLE;";
			cnt = DBTestUtil.getCount(sql, mojo.url, mojo.user, mojo.password);
			assertThat(cnt, is(300));
			
			sql = "SELECT COUNT(*) FROM C_TABLE;";
			cnt = DBTestUtil.getCount(sql, mojo.url, mojo.user, mojo.password);
			assertThat(cnt, is(300));
			
			sql = "SELECT COUNT(*) FROM D_TABLE;";
			cnt = DBTestUtil.getCount(sql, mojo.url, mojo.user, mojo.password);
			assertThat(cnt, is(300));

		}
	}

}
