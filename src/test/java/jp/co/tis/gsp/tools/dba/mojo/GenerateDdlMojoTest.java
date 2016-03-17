package jp.co.tis.gsp.tools.dba.mojo;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import jp.co.tis.gsp.test.util.DirUtil;
import jp.co.tis.gsp.test.util.Entry;
import jp.co.tis.gsp.test.util.MojoTestFixture;
import jp.co.tis.gsp.test.util.TestCasePattern;

public class GenerateDdlMojoTest extends AbstractDdlMojoTest<GenerateDdlMojo> {

	/**
	 * 様々なデータ型でのDDL生成テスト。
	 * 
	 * @throws Exception
	 */
	@Test
	@TestCasePattern(testCase = "case1", testDb = { TestDB.oracle, TestDB.postgresql, TestDB.db2, TestDB.h2,
			TestDB.sqlserver, TestDB.mysql })
	public void testCase1() throws Exception {

		// 指定されたケース及びテスト対象のDBだけループ
		for (MojoTestFixture mf : mojoTestFixtureList) {

			// ケース、データベースに応じてmojoにパラメータをバインドしてmojoを生成
			mojo = setUpMojo(mf, getTestCaseDBPath(mf) + "/mojo_pram.properties");

			// mojo実行
			mojo.execute();

			// 検証
			String actualPath = mojo.outputDirectory.getAbsolutePath();

			// 出力フォルダと期待値フォルダのファイルを収集
			Entry actualFiles = DirUtil.collectEntry(actualPath);
			Entry expectedFiles = DirUtil.collectEntry(getExpectedPath(mf) + "\\ddl");

			// フォルダ比較
			assertThat("TestDb:" + mf.testDb, actualFiles.equals(expectedFiles), is(true));
		}
	}

	/**
	 * 基本的なデータベースの機能を使ったテスト。以下の機能のDDL生成テストを行う。
	 * 
	 * <ul>
	 * <li>主キー</li>
	 * <li>外部キー</li>
	 * <li>シーケンス</li>
	 * </ul>
	 * 
	 * @throws Exception
	 */
	@Test
	@TestCasePattern(testCase = "case2", testDb = { TestDB.oracle, TestDB.postgresql, TestDB.db2, TestDB.h2,
			TestDB.sqlserver, TestDB.mysql })
	public void testCase2() throws Exception {

		// 指定されたケース及びテスト対象のDBだけループ
		for (MojoTestFixture mf : mojoTestFixtureList) {

			// ケース、データベースに応じてmojoにパラメータをバインドしてmojoを生成
			mojo = setUpMojo(mf, getTestCaseDBPath(mf) + "/mojo_pram.properties");

			// mojo実行
			mojo.execute();

			// 検証
			String actual = mojo.outputDirectory.getAbsolutePath();

			// 出力フォルダと期待値フォルダのファイルを収集
			// Entry actualFiles = DirUtil.collectEntry(actual);
			// Entry expectedFiles = DirUtil.collectEntry(getExpectedPath(pf) +
			// "\\ddl");
			//
			// // フォルダ比較
			// assertThat(actualFiles.equals(expectedFiles), is(true));
		}

	}
}
