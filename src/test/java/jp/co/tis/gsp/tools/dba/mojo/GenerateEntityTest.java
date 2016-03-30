package jp.co.tis.gsp.tools.dba.mojo;

import java.io.File;

import org.apache.maven.plugin.Mojo;
import org.junit.Test;
import org.junit.experimental.theories.Theories;
import org.junit.runner.RunWith;

import jp.co.tis.gsp.test.util.MojoTestFixture;
import jp.co.tis.gsp.test.util.TestCasePattern;
import jp.co.tis.gsp.test.util.TestDB;

@RunWith(Theories.class)
public class GenerateEntityTest extends AbstractDdlMojoTest<GenerateEntity> {

	/**
	 * 様々なデータ型でのDDL生成テスト。
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

			// 先にインプットになるテーブル定義を作成するためexecute-ddl
			ExecuteDdlMojoTest ddlTest = new ExecuteDdlMojoTest();
			ddlTest.setUp();
			ExecuteDdlMojo ddlMojo = ddlTest.lookupConfiguredMojo(pom, EXECUTE_DDL, mf.testDb);
			ddlMojo.execute();

			// pom.xmlより指定ゴールのMojoを取得し実行。Mavenプロファイルを指定する(DB)
			Mojo mojo = this.lookupConfiguredMojo(pom, GENERATE_ENTITY, mf.testDb);
			mojo.execute();

		}
	}

	/**
	 * データベースの基本機能を使ったEntity生成のテスト。
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

			// 先にインプットになるテーブル定義を作成するためexecute-ddl
			ExecuteDdlMojoTest ddlTest = new ExecuteDdlMojoTest();
			ddlTest.setUp();
			ExecuteDdlMojo ddlMojo = ddlTest.lookupConfiguredMojo(pom, EXECUTE_DDL, mf.testDb);
			ddlMojo.execute();

			// pom.xmlより指定ゴールのMojoを取得し実行。Mavenプロファイルを指定する(DB)
			Mojo mojo = this.lookupConfiguredMojo(pom, GENERATE_ENTITY, mf.testDb);
			mojo.execute();

		}
	}

	/**
	 * ユーザ名とスキーマ名が異なる場合
	 * 
	 * @throws Exception
	 */
	@Test
	@TestCasePattern(testCase = "another_schema_test", testDb = { TestDB.oracle, TestDB.postgresql, TestDB.db2,
			TestDB.sqlserver, TestDB.mysql })
	public void testAnotherSchema() throws Exception {

		// 指定されたケース及びテスト対象のDBだけループ
		for (MojoTestFixture mf : mojoTestFixtureList) {

			// テストケース対象プロジェクトのpom.xmlを取得
			File pom = new File(getTestCaseDBPath(mf) + "/pom.xml");

			// 先にインプットになるテーブル定義を作成するためexecute-ddl
			ExecuteDdlMojoTest ddlTest = new ExecuteDdlMojoTest();
			ddlTest.setUp();
			ExecuteDdlMojo ddlMojo = ddlTest.lookupConfiguredMojo(pom, EXECUTE_DDL, mf.testDb);
			ddlMojo.execute();

			// pom.xmlより指定ゴールのMojoを取得し実行。Mavenプロファイルを指定する(DB)
			Mojo mojo = this.lookupConfiguredMojo(pom, GENERATE_ENTITY, mf.testDb);
			mojo.execute();

		}
	}

	/**
	 * パラメータ：ignoreTableNamePatternのテスト。
	 * 
	 * @throws Exception
	 */
	@Test
	@TestCasePattern(testCase = "ignoreTableNamePattern_test", testDb = { TestDB.oracle, TestDB.postgresql, TestDB.db2,
			TestDB.h2, TestDB.sqlserver, TestDB.mysql })
	public void testＩgnoreTableNamePattern() throws Exception {

		// 指定されたケース及びテスト対象のDBだけループ
		for (MojoTestFixture mf : mojoTestFixtureList) {

			// テストケース対象プロジェクトのpom.xmlを取得
			File pom = new File(getTestCaseDBPath(mf) + "/pom.xml");

			// 先にインプットになるテーブル定義を作成するためexecute-ddl
			ExecuteDdlMojoTest ddlTest = new ExecuteDdlMojoTest();
			ddlTest.setUp();
			ExecuteDdlMojo ddlMojo = ddlTest.lookupConfiguredMojo(pom, EXECUTE_DDL, mf.testDb);
			ddlMojo.execute();

			// pom.xmlより指定ゴールのMojoを取得し実行。Mavenプロファイルを指定する(DB)
			Mojo mojo = this.lookupConfiguredMojo(pom, GENERATE_ENTITY, mf.testDb);
			mojo.execute();

		}
	}

	/**
	 * パラメータ：useAccessorのテスト。
	 * 
	 * @throws Exception
	 */
	@Test
	@TestCasePattern(testCase = "useAccessor_test", testDb = { TestDB.oracle, TestDB.postgresql, TestDB.db2, TestDB.h2,
			TestDB.sqlserver, TestDB.mysql })
	public void testUseAccessor() throws Exception {

		// 指定されたケース及びテスト対象のDBだけループ
		for (MojoTestFixture mf : mojoTestFixtureList) {

			// テストケース対象プロジェクトのpom.xmlを取得
			File pom = new File(getTestCaseDBPath(mf) + "/pom.xml");

			// 先にインプットになるテーブル定義を作成するためexecute-ddl
			ExecuteDdlMojoTest ddlTest = new ExecuteDdlMojoTest();
			ddlTest.setUp();
			ExecuteDdlMojo ddlMojo = ddlTest.lookupConfiguredMojo(pom, EXECUTE_DDL, mf.testDb);
			ddlMojo.execute();

			// pom.xmlより指定ゴールのMojoを取得し実行。Mavenプロファイルを指定する(DB)
			Mojo mojo = this.lookupConfiguredMojo(pom, GENERATE_ENTITY, mf.testDb);
			mojo.execute();

		}
	}

	/**
	 * パラメータ：entityTemplateのテスト。
	 * 
	 * @throws Exception
	 */
	@Test
	@TestCasePattern(testCase = "entityTemplate_test", testDb = { TestDB.oracle, TestDB.postgresql, TestDB.db2,
			TestDB.h2, TestDB.sqlserver, TestDB.mysql })
	public void testEntityTemplarte() throws Exception {

		// 指定されたケース及びテスト対象のDBだけループ
		for (MojoTestFixture mf : mojoTestFixtureList) {

			// テストケース対象プロジェクトのpom.xmlを取得
			File pom = new File(getTestCaseDBPath(mf) + "/pom.xml");

			// 先にインプットになるテーブル定義を作成するためexecute-ddl
			ExecuteDdlMojoTest ddlTest = new ExecuteDdlMojoTest();
			ddlTest.setUp();
			ExecuteDdlMojo ddlMojo = ddlTest.lookupConfiguredMojo(pom, EXECUTE_DDL, mf.testDb);
			ddlMojo.execute();

			// pom.xmlより指定ゴールのMojoを取得し実行。Mavenプロファイルを指定する(DB)
			Mojo mojo = this.lookupConfiguredMojo(pom, GENERATE_ENTITY, mf.testDb);
			mojo.execute();

		}
	}

	/**
	 * パラメータ：dialectClassName のテスト
	 * 
	 * @throws Exception
	 */
	@Test
	@TestCasePattern(testCase = "dialectClassName", testDb = { TestDB.oracle, TestDB.postgresql, TestDB.db2, TestDB.h2,
			TestDB.sqlserver, TestDB.mysql })
	public void testDialectClassName() throws Exception {

		// 指定されたケース及びテスト対象のDBだけループ
		for (MojoTestFixture mf : mojoTestFixtureList) {

			// ケース、データベースに応じてmojoにパラメータをバインドしてmojoを生成
			// mojo = setUpMojo(mf, getTestCaseDBPath(mf) +
			// "/mojo_pram.properties");

			// mojo実行検証
			mojo.execute();

		}
	}

	/**
	 * パラメータ：genDialectClassName のテスト
	 * 
	 * @throws Exception
	 */
	@Test
	@TestCasePattern(testCase = "genDialectClassName", testDb = { TestDB.oracle, TestDB.postgresql, TestDB.db2,
			TestDB.h2, TestDB.sqlserver, TestDB.mysql })
	public void testGenDialectClassName() throws Exception {

		// 指定されたケース及びテスト対象のDBだけループ
		for (MojoTestFixture mf : mojoTestFixtureList) {

			// ケース、データベースに応じてmojoにパラメータをバインドしてmojoを生成
			// mojo = setUpMojo(mf, getTestCaseDBPath(mf) +
			// "/mojo_pram.properties");

			// mojo実行検証
			mojo.execute();

		}
	}

}
