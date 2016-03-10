package jp.co.tis.gsp.tools.dba.mojo;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.io.File;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.theories.Theories;
import org.junit.runner.RunWith;

import jp.co.tis.gsp.test.util.DirUtil;
import jp.co.tis.gsp.test.util.Entry;
import jp.co.tis.gsp.test.util.TestCasePattern;
import jp.co.tis.gsp.tools.db.LengthSemantics;
import jp.co.tis.gsp.tools.dba.mojo.AbstractDdlMojoTest.PatternFixture;
import jp.co.tis.gsp.tools.dba.mojo.AbstractDdlMojoTest.TestDB;

@RunWith(Theories.class)
public class ExecuteDdlMojoTest extends AbstractDdlMojoTest<ExecuteDdlMojo> {

	@Test
	@TestCasePattern(testCase = "case1", testDb = { TestDB.oracle, TestDB.postgresql })
	public void testCase1() throws Exception {

		// 指定されたケース及びテスト対象のDBだけループ
		for (PatternFixture pf : caseList) {

			// ケース、データベースに応じてmojoにパラメータをバインドしてmojoを生成
			mojo = bindParameter(pf);

			// mojo実行
			mojo.execute();

			// 検証
//			String actual = mojo.outputDirectory.getAbsolutePath();

			// 出力フォルダと期待値フォルダのファイルを収集
//			Entry actualFiles = DirUtil.collectEntry(actual);
//			Entry expectedFiles = DirUtil.collectEntry(getExpectedPath(pf));
//
//			// フォルダ比較
//			assertThat(actualFiles.equals(expectedFiles), is(true));
//		}

	}

}

}
