package jp.co.tis.gsp.tools.dba.mojo;

import org.apache.maven.project.MavenProject;
import org.codehaus.plexus.archiver.jar.JarArchiver;
import org.junit.Test;
import org.junit.experimental.theories.Theories;
import org.junit.runner.RunWith;

import jp.co.tis.gsp.test.util.MojoTestFixture;
import jp.co.tis.gsp.test.util.TestCasePattern;

@RunWith(Theories.class)
public class ExportSchemaMojoTest extends AbstractDdlMojoTest<ExportSchemaMojo> {

	@Override
	protected ExportSchemaMojo setUpMojo(MojoTestFixture mf, String caseMojoParamPath) throws Exception {
		mojo = super.setUpMojo(mf, caseMojoParamPath);
		mojo.jarArchiver = new JarArchiver();

		MavenProject pj = new MavenProject();
		pj.setArtifactId("gsptest");
		pj.setVersion("1.0.0");
		
		mojo.project = pj;

		return mojo;
	}

	/**
	 * 様々なデータ型でのDDL生成テスト。
	 * 
	 * @throws Exception
	 */
	@Test
	@TestCasePattern(testCase = "case1", testDb = { TestDB.oracle, TestDB.postgresql, TestDB.h2, TestDB.mysql })
	public void testCase1() throws Exception {

		// 指定されたケース及びテスト対象のDBだけループ
		for (MojoTestFixture mf : mojoTestFixtureList) {

			// ケース、データベースに応じてmojoにパラメータをバインドしてmojoを生成
			mojo = setUpMojo(mf, getTestCaseDBPath(mf) + "/mojo_pram.properties");

			// mojo実行検証
			mojo.execute();

		}
	}

}
