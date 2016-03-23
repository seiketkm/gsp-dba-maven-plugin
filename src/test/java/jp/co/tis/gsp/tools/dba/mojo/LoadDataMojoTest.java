package jp.co.tis.gsp.tools.dba.mojo;

import org.junit.Test;

import jp.co.tis.gsp.test.util.MojoTestFixture;
import jp.co.tis.gsp.test.util.TestCasePattern;

public class LoadDataMojoTest extends AbstractDdlMojoTest<LoadDataMojo> {

	/**
	 * 様々なデータ型でのDDL生成テスト。
	 * 
	 * @throws Exception
	 */
	@Test
	@TestCasePattern(testCase = "type_test", testDb = { TestDB.oracle, TestDB.postgresql, TestDB.db2, TestDB.h2,
			TestDB.sqlserver, TestDB.mysql })
	public void testCase1() throws Exception {

		// 指定されたケース及びテスト対象のDBだけループ
		for (MojoTestFixture mf : mojoTestFixtureList) {

			// テスト用のDDL実行
			ExecuteDdlMojo executeDdlMojo = new ExecuteDdlMojoTest().setUpMojo(mf,
					getTestCaseDBPath(mf) + "/ddl_mojo_pram.properties");
			executeDdlMojo.execute();

			// ケース、データベースに応じてmojoにパラメータをバインドしてmojoを生成
			mojo = setUpMojo(mf, getTestCaseDBPath(mf) + "/mojo_pram.properties");

			// mojo実行
			mojo.execute();
			
			// 検証

		}
	}

}
