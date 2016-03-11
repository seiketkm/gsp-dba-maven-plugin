package jp.co.tis.gsp.tools.dba.mojo;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import jp.co.tis.gsp.test.util.DirUtil;
import jp.co.tis.gsp.test.util.Entry;
import jp.co.tis.gsp.test.util.TestCasePattern;

public class GenerateDdlMojoTest extends AbstractDdlMojoTest<GenerateDdlMojo> {

	/**
	 * 様々なDBの型のテスト.
	 * 
	 * @throws Exception
	 */
	@Test
	@TestCasePattern(testCase = "case1", testDb = { TestDB.oracle, TestDB.postgresql, TestDB.db2, TestDB.h2,
			TestDB.sqlserver, TestDB.mysql })
	public void testCase1() throws Exception {

		// 指定されたケース及びテスト対象のDBだけループ
		for (PatternFixture pf : caseList) {

			// ケース、データベースに応じてmojoにパラメータをバインドしてmojoを生成
			mojo = setUpMojo(pf);

			// mojo実行
			mojo.execute();

			// 検証
			String actual = mojo.outputDirectory.getAbsolutePath();

			// 出力フォルダと期待値フォルダのファイルを収集
			Entry actualFiles = DirUtil.collectEntry(actual);
			Entry expectedFiles = DirUtil.collectEntry(getExpectedPath(pf) + "\\ddl");

			// フォルダ比較
			assertThat(actualFiles.equals(expectedFiles), is(true));
		}
	}

	/**
	 * 様々なDBの型のテスト.
	 * 
	 * @throws Exception
	 */
	@Test
	@TestCasePattern(testCase = "case2", testDb = { TestDB.oracle, TestDB.postgresql, TestDB.db2, TestDB.h2,
			TestDB.sqlserver, TestDB.mysql })
	public void testCase2() throws Exception {

		// 指定されたケース及びテスト対象のDBだけループ
		for (PatternFixture pf : caseList) {

			// ケース、データベースに応じてmojoにパラメータをバインドしてmojoを生成
			mojo = setUpMojo(pf);

			// mojo実行
			mojo.execute();

			// 検証
			String actual = mojo.outputDirectory.getAbsolutePath();

			// 出力フォルダと期待値フォルダのファイルを収集
			Entry actualFiles = DirUtil.collectEntry(actual);
			Entry expectedFiles = DirUtil.collectEntry(getExpectedPath(pf) + "\\ddl");

			// フォルダ比較
			assertThat(actualFiles.equals(expectedFiles), is(true));
		}

	}
}
