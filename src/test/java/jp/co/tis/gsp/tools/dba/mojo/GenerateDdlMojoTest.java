package jp.co.tis.gsp.tools.dba.mojo;

import java.io.File;

import org.apache.maven.plugin.Mojo;
import org.junit.Test;

import jp.co.tis.gsp.test.util.MojoTestFixture;
import jp.co.tis.gsp.test.util.TestCasePattern;
import jp.co.tis.gsp.test.util.TestDB;

public class GenerateDdlMojoTest extends AbstractDdlMojoTest<GenerateDdlMojo> {

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

			// pom.xmlより指定ゴールのMojoを取得し実行
			Mojo mojo = this.lookupConfiguredMojo(pom, GENERATE_DDL, mf.testDb);
			mojo.execute();

		}
	}

	/**
	 * データベースの基本機能を使ったDDLの生成テスト。
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

			File pom = new File(getTestCaseDBPath(mf) + "/pom.xml");

			Mojo mojo = this.lookupConfiguredMojo(pom, GENERATE_DDL, mf.testDb);
			mojo.execute();

		}
	}

	/**
	 * パラメータ：ddlTemplateFileDirのテスト。
	 * 
	 * test_templateフォルダを見るようにしてテスト。
	 * 
	 * @throws Exception
	 */
	@Test
	@TestCasePattern(testCase = "ddlTemplateFileDir_test", testDb = { TestDB.oracle, TestDB.postgresql, TestDB.db2,
			TestDB.h2, TestDB.sqlserver, TestDB.mysql })
	public void testDdlTemplateFileDir() throws Exception {
		for (MojoTestFixture mf : mojoTestFixtureList) {

			// テストケース対象プロジェクトのpom.xmlを取得
			File pom = new File(getTestCaseDBPath(mf) + "/pom.xml");

			// pom.xmlより指定ゴールのMojoを取得し実行。Mavenプロファイルを指定する(DB)
			Mojo mojo = this.lookupConfiguredMojo(pom, GENERATE_DDL, mf.testDb);
			mojo.execute();

		}
	}

	/**
	 * パラメータ：lengthSemanticsのテスト。
	 * 
	 * oracleのみ。 {@code LengthSemantics.BYTE}の場合をテストする。
	 * 
	 * @throws Exception
	 */
	@Test
	@TestCasePattern(testCase = "lengthSemantics_test", testDb = { TestDB.oracle })
	public void testLengthSemantics() throws Exception {
		for (MojoTestFixture mf : mojoTestFixtureList) {

			// テストケース対象プロジェクトのpom.xmlを取得
			File pom = new File(getTestCaseDBPath(mf) + "/pom.xml");

			// pom.xmlより指定ゴールのMojoを取得し実行。Mavenプロファイルを指定する(DB)
			Mojo mojo = this.lookupConfiguredMojo(pom, GENERATE_DDL, mf.testDb);
			mojo.execute();

		}
	}

	/**
	 * ユーザ名とスキーマ名が異なる場合のテスト。
	 * 
	 * @throws Exception
	 */
	@Test
	@TestCasePattern(testCase = "another_schema_test", testDb = { TestDB.oracle, TestDB.postgresql, TestDB.sqlserver,
			TestDB.db2, TestDB.mysql })
	public void testAnotherSchema() throws Exception {
		for (MojoTestFixture mf : mojoTestFixtureList) {

			// テストケース対象プロジェクトのpom.xmlを取得
			File pom = new File(getTestCaseDBPath(mf) + "/pom.xml");

			// pom.xmlより指定ゴールのMojoを取得し実行。Mavenプロファイルを指定する(DB)
			Mojo mojo = this.lookupConfiguredMojo(pom, GENERATE_DDL, mf.testDb);
			mojo.execute();

		}
	}
}
