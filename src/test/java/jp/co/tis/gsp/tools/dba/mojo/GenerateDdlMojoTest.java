package jp.co.tis.gsp.tools.dba.mojo;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.io.File;

import org.junit.Test;

import jp.co.tis.gsp.test.util.DirUtil;
import jp.co.tis.gsp.test.util.Entry;
import jp.co.tis.gsp.test.util.TestCasePattern;
import jp.co.tis.gsp.tools.dba.mojo.AbstractDdlMojoTest.PatternFixture;

public class GenerateDdlMojoTest extends AbstractDdlMojoTest<GenerateDdlMojo> {

	/**
	 * DDL生成の検証.
	 * 
	 * @throws Exception
	 */
	@Test
	@TestCasePattern(testCase = "case1", testDb = { TestDB.postgresql })
	public void testCase1() throws Exception {

		// 指定されたケース及びテスト対象のDBだけループ
		for (PatternFixture pf : caseList) {

			// ケース、データベースに応じてmojoにパラメータをバインドしてmojoを生成
			mojo = bindParameter(pf);

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
