package jp.co.tis.gsp.tools.dba.mojo;

import java.io.File;

import org.apache.maven.plugin.Mojo;
import org.junit.Test;
import org.junit.experimental.theories.Theories;
import org.junit.runner.RunWith;

import jp.co.tis.gsp.test.util.MojoTestFixture;
import jp.co.tis.gsp.test.util.TestCasePattern;

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
	public void typeTest() throws Exception {

		// 指定されたケース及びテスト対象のDBだけループ
		for (MojoTestFixture mf : mojoTestFixtureList) {

			// テストケース対象プロジェクトのpom.xmlを取得
			File pom = new File(getTestCaseDBPath(mf) + "/pom.xml");

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
//			mojo = setUpMojo(mf, getTestCaseDBPath(mf) + "/mojo_pram.properties");

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
	@TestCasePattern(testCase = "genDialectClassName", testDb = { TestDB.oracle, TestDB.postgresql, TestDB.db2, TestDB.h2,
			TestDB.sqlserver, TestDB.mysql })
	public void testGenDialectClassName() throws Exception {

		// 指定されたケース及びテスト対象のDBだけループ
		for (MojoTestFixture mf : mojoTestFixtureList) {

			// ケース、データベースに応じてmojoにパラメータをバインドしてmojoを生成
//			mojo = setUpMojo(mf, getTestCaseDBPath(mf) + "/mojo_pram.properties");

			// mojo実行検証
			mojo.execute();

		}
	}

}
