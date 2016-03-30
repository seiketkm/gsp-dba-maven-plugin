package jp.co.tis.gsp.tools.dba.mojo;

import java.io.File;

import org.apache.maven.plugin.Mojo;
import org.junit.Rule;
import org.junit.Test;
import org.junit.experimental.theories.Theories;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;

import jp.co.tis.gsp.test.util.MojoTestFixture;
import jp.co.tis.gsp.test.util.TestCasePattern;
import jp.co.tis.gsp.test.util.TestDB;

@RunWith(Theories.class)
public class ExportSchemaMojoTest extends AbstractDdlMojoTest<ExportSchemaMojo> {

	@Rule
	public ExpectedException expected = ExpectedException.none();

	/**
	 * 基本機能のテスト。
	 * 
	 * @throws Exception
	 */
	@Test
	@TestCasePattern(testCase = "basic_test", testDb = { TestDB.oracle, TestDB.postgresql, TestDB.h2, TestDB.mysql })
	public void testBasic() throws Exception {

		// 指定されたケース及びテスト対象のDBだけループ
		for (MojoTestFixture mf : mojoTestFixtureList) {

			// テストケース対象プロジェクトのpom.xmlを取得
			File pom = new File(getTestCaseDBPath(mf) + "/pom.xml");

			// pom.xmlより指定ゴールのMojoを取得し実行。Mavenプロファイルを指定する(DB)
			Mojo mojo = this.lookupConfiguredMojo(pom, EXPORT_SCHEMA, mf.testDb);
			mojo.execute();

		}
	}

	/**
	 * DB2とSQLServerはExport-Schema非対応のため例外テスト。
	 * 
	 * @throws Exception
	 */
	@TestCasePattern(testCase = "basic_test_exception", testDb = { TestDB.db2, TestDB.sqlserver })
	public void testBasicException() throws Exception {

		// 指定されたケース及びテスト対象のDBだけループ
		for (MojoTestFixture mf : mojoTestFixtureList) {

			expected.expect(UnsupportedOperationException.class);

			// テストケース対象プロジェクトのpom.xmlを取得
			File pom = new File(getTestCaseDBPath(mf) + "/pom.xml");

			// pom.xmlより指定ゴールのMojoを取得し実行。Mavenプロファイルを指定する(DB)
			Mojo mojo = this.lookupConfiguredMojo(pom, EXPORT_SCHEMA, mf.testDb);
			mojo.execute();

		}
	}

	/**
	 * ユーザ名とスキーマ名が異なる場合のテスト。
	 * 
	 * @throws Exception
	 */
	@Test
	@TestCasePattern(testCase = "basic_test", testDb = { TestDB.oracle, TestDB.postgresql, TestDB.h2, TestDB.mysql })
	public void testAnotherSchema() throws Exception {

		// 指定されたケース及びテスト対象のDBだけループ
		for (MojoTestFixture mf : mojoTestFixtureList) {

			// テストケース対象プロジェクトのpom.xmlを取得
			File pom = new File(getTestCaseDBPath(mf) + "/pom.xml");

			// pom.xmlより指定ゴールのMojoを取得し実行。Mavenプロファイルを指定する(DB)
			Mojo mojo = this.lookupConfiguredMojo(pom, EXPORT_SCHEMA, mf.testDb);
			mojo.execute();

		}
	}
}
