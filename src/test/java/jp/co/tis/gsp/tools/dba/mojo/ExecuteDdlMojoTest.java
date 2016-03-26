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

import jp.co.tis.gsp.test.util.MojoTestFixture;
import jp.co.tis.gsp.test.util.TestCasePattern;

@RunWith(Theories.class)
public class ExecuteDdlMojoTest extends AbstractDdlMojoTest<ExecuteDdlMojo> {

	/**
	 * 様々なデータ型でのDDL生成テスト。
	 * 
	 * @throws Exception
	 */
	@Test
	@TestCasePattern(testCase = "type_test", testDb = { TestDB.oracle, TestDB.postgresql, TestDB.db2, TestDB.h2,
			TestDB.sqlserver, TestDB.mysql })
	public void typeTest() throws Exception {

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
	 * 様々なデータ型でのDDL生成テスト。
	 * 
	 * @throws Exception
	 */
	@Test
	@TestCasePattern(testCase = "case1", testDb = { TestDB.oracle, TestDB.postgresql, TestDB.db2, TestDB.h2,
			TestDB.sqlserver, TestDB.mysql })
	public void testCrudForSchema() throws Exception {

		// 指定されたケース及びテスト対象のDBだけループ
		for (MojoTestFixture mf : mojoTestFixtureList) {

			// ケース、データベースに応じてmojoにパラメータをバインドしてmojoを生成
			// mojo = setUpMojo(mf, getTestCaseDBPath(mf) +
			// "/mojo_pram.properties");

			// mojo実行検証
			mojo.execute();

			Properties props = new Properties();
			props.put("user", mojo.user);
			props.put("password", mojo.password);
			Connection conn = DriverManager.getConnection(mojo.url, props);
			Statement stmt = conn.createStatement();

			stmt.executeQuery("SELECT * FROM " + mojo.schema + ".TEST1");
			stmt.executeUpdate("INSERT INTO " + mojo.schema + ".TEST1 VALUES()");
			stmt.executeUpdate("UPDATE " + mojo.schema + ".TEST1 SET ==");
			stmt.executeUpdate("DELETE FROM " + mojo.schema + ".TEST1");

			stmt.close();
			conn.close();

		}
	}

}
