package jp.co.tis.gsp.tools.dba.mojo;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.Properties;

import org.apache.maven.plugin.Mojo;
import org.junit.Test;
import org.junit.experimental.theories.Theories;
import org.junit.runner.RunWith;

import jp.co.tis.gsp.test.util.DBTestUtil;
import jp.co.tis.gsp.test.util.MojoTestFixture;
import jp.co.tis.gsp.test.util.TestCasePattern;
import jp.co.tis.gsp.test.util.TestDB;
import junit.framework.AssertionFailedError;

@RunWith(Theories.class)
public class ExecuteDdlMojoTest extends AbstractDdlMojoTest<ExecuteDdlMojo> {

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

			// pom.xmlより指定ゴールのMojoを取得し実行。Mavenプロファイルを指定する(DB)
			Mojo mojo = this.lookupConfiguredMojo(pom, EXECUTE_DDL, mf.testDb);
			mojo.execute();

		}
	}

	/**
	 * データベースの基本機能を使ったDDLの実行テスト。
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
	@TestCasePattern(testCase = "basic_test", testDb = { TestDB.oracle, TestDB.postgresql, TestDB.db2, TestDB.h2,
			TestDB.sqlserver, TestDB.mysql })
	public void testBasic() throws Exception {

		// 指定されたケース及びテスト対象のDBだけループ
		for (MojoTestFixture mf : mojoTestFixtureList) {

			// テストケース対象プロジェクトのpom.xmlを取得
			File pom = new File(getTestCaseDBPath(mf) + "/pom.xml");

			// pom.xmlより指定ゴールのMojoを取得し実行。Mavenプロファイルを指定する(DB)
			Mojo mojo = this.lookupConfiguredMojo(pom, EXECUTE_DDL, mf.testDb);
			mojo.execute();

		}
	}

	/**
	 * スキーマが存在する・存在しない場合のテスト。
	 * 
	 * @throws Exception
	 */
	@Test
	@TestCasePattern(testCase = "exist_schema", testDb = { TestDB.oracle, TestDB.postgresql, TestDB.db2, TestDB.h2,
			TestDB.sqlserver })
	public void testExistSchema() throws Exception {

		// 指定されたケース及びテスト対象のDBだけループ
		for (MojoTestFixture mf : mojoTestFixtureList) {

			// テストケース対象プロジェクトのpom.xmlを取得
			File pom = new File(getTestCaseDBPath(mf) + "/pom.xml");

			// pom.xmlより指定ゴールのMojoを取得し実行。Mavenプロファイルを指定する(DB)
			ExecuteDdlMojo mojo = this.lookupConfiguredMojo(pom, EXECUTE_DDL, mf.testDb);

			// スキーマのドロップ
			DBTestUtil.dropSchema(mojo.schema, mojo.adminUser, mojo.adminPassword, mojo.url, mf.testDb);

			// 1回目
			mojo.execute();

			// 2回目
			mojo.execute();

		}
	}

	/**
	 * スキーマが存在する・存在しない場合のテスト。
	 * 
	 * @throws Exception
	 */
	@Test
	@TestCasePattern(testCase = "exist_another_schema", testDb = { TestDB.oracle, TestDB.postgresql, TestDB.db2,
			TestDB.sqlserver })
	public void testExistAnotherSchema() throws Exception {

		// 指定されたケース及びテスト対象のDBだけループ
		for (MojoTestFixture mf : mojoTestFixtureList) {

			// テストケース対象プロジェクトのpom.xmlを取得
			File pom = new File(getTestCaseDBPath(mf) + "/pom.xml");

			// pom.xmlより指定ゴールのMojoを取得し実行。Mavenプロファイルを指定する(DB)
			ExecuteDdlMojo mojo = this.lookupConfiguredMojo(pom, EXECUTE_DDL, mf.testDb);

			// スキーマのドロップ
			DBTestUtil.dropSchema(mojo.schema, mojo.adminUser, mojo.adminPassword, mojo.url, mf.testDb);

			// 1回目
			mojo.execute();

			// 2回目
			mojo.execute();

		}
	}

	/**
	 * パラメータ：extraDdlDirectoryのテスト。
	 * 
	 * @throws Exception
	 */
	@Test
	@TestCasePattern(testCase = "extraDdlDirectory_test", testDb = { TestDB.oracle, TestDB.postgresql, TestDB.db2,
			TestDB.h2, TestDB.sqlserver, TestDB.mysql })
	public void testExtraDdlDirectory() throws Exception {

		// 指定されたケース及びテスト対象のDBだけループ
		for (MojoTestFixture mf : mojoTestFixtureList) {

			// テストケース対象プロジェクトのpom.xmlを取得
			File pom = new File(getTestCaseDBPath(mf) + "/pom.xml");

			// pom.xmlより指定ゴールのMojoを取得し実行。Mavenプロファイルを指定する(DB)
			Mojo mojo = this.lookupConfiguredMojo(pom, EXECUTE_DDL, mf.testDb);
			mojo.execute();

		}
	}

	/**
	 * ユーザ名とスキーマ名が違う設定でユーザが別スキーマのテーブルを操作できるかテスト。
	 * 
	 * @throws Exception
	 */
	@Test
	@TestCasePattern(testCase = "another_schema_crud", testDb = { TestDB.oracle, TestDB.postgresql, TestDB.db2,
			TestDB.h2, TestDB.sqlserver, TestDB.mysql })
	public void testAnotherSchemaCrud() throws Exception {

		// 指定されたケース及びテスト対象のDBだけループ
		for (MojoTestFixture mf : mojoTestFixtureList) {

			// テストケース対象プロジェクトのpom.xmlを取得
			File pom = new File(getTestCaseDBPath(mf) + "/pom.xml");

			// pom.xmlより指定ゴールのMojoを取得し実行。Mavenプロファイルを指定する(DB)
			ExecuteDdlMojo mojo = this.lookupConfiguredMojo(pom, EXECUTE_DDL, mf.testDb);
			mojo.execute();

			Properties props = new Properties();
			props.put("user", mojo.user);
			props.put("password", mojo.password);
			Connection conn = DriverManager.getConnection(mojo.url, props);
			Statement stmt = conn.createStatement();

			try {
				stmt.executeQuery("SELECT * FROM " + mojo.schema + ".TEST_TBL1");
				stmt.executeUpdate("INSERT INTO " + mojo.schema + ".TEST_TBL1(NAME) VALUES('TEST_INSERT')");
				stmt.executeUpdate("UPDATE " + mojo.schema + ".TEST_TBL1 SET NAME = 'TEST_UPDATE'");
				stmt.executeUpdate("DELETE FROM " + mojo.schema + ".TEST_TBL1");
			} catch (Exception e) {
				throw new AssertionFailedError(e.getMessage());
			}

			stmt.close();
			conn.close();

		}
	}

}
