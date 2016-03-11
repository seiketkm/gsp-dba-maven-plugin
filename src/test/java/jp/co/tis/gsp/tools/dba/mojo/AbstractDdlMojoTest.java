package jp.co.tis.gsp.tools.dba.mojo;

import java.io.File;
import java.io.FileInputStream;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.dozer.DozerBeanMapper;
import org.junit.Rule;
import org.junit.experimental.theories.Theories;
import org.junit.rules.TestRule;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import org.junit.runner.RunWith;

import edu.emory.mathcs.backport.java.util.Arrays;
import jp.co.tis.gsp.test.util.TestCasePattern;

@RunWith(Theories.class)
public abstract class AbstractDdlMojoTest<E> {

	protected Type mojoType = ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
	protected E mojo;

	@Rule
	public TestRule caseSetUpper = new TestWatcher() {
		protected void starting(Description d) {

			TestCasePattern tp = d.getAnnotation(TestCasePattern.class);
			if (tp != null) {
				caseList = new ArrayList<AbstractDdlMojoTest<E>.PatternFixture>();
				for (TestDB db : getDbCase(tp)) {
					caseList.add(new PatternFixture(d.getTestClass(), tp.testCase(), db));
				}
			}

		};

		// プロパティファイルとTestCasePattern#testDb要素から実行するデータベースパターンを取得する
		private TestDB[] getDbCase(TestCasePattern tp) {

			// プロパティファイルにtestDBが設定されている場合。
			// 設定されたtestDBがアノテーションに含まれている場合はそのtestDB要素だけの配列を返す
			try {
				String propPath = Thread.currentThread().getContextClassLoader()
						.getResource("jp/co/tis/gsp/tools/dba/mojo/mojoTest.properties").getPath();
				Properties prop = new Properties();
				prop.load(new FileInputStream(new File(propPath)));

				String testDb = prop.getProperty("testDB");
				if (testDb != null && Arrays.asList(tp.testDb()).contains(TestDB.valueOf(testDb))) {
					return new TestDB[] { TestDB.valueOf(testDb) };
				}

			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}

			// 上記以外は、アノテーション設定値をそのまま返す
			return tp.testDb();

		}

		protected void finished(Description description) {
			caseList = null;
		}
	};

	List<PatternFixture> caseList;

	protected E setUpMojo(PatternFixture pf) {
		Object rtn = null;
		try {
			String testClassName = pf.testClass.getSimpleName();

			// DB共通Mojoパラメータを設定
			String propPath = this.getClass().getResource(pf.testDb + ".properties").getPath();
			Properties prop = new Properties();
			prop.load(new FileInputStream(new File(propPath)));
			DozerBeanMapper mapper = new DozerBeanMapper();
			List<String> myMappingFiles = new ArrayList<String>();

			String mapperFile = this.getClass().getResource(testClassName + "/paramMapper.xml").toURI().toURL()
					.toString();
			myMappingFiles.add(mapperFile);
			mapper.setMappingFiles(myMappingFiles);
			rtn = mapper.map(prop, Class.forName(mojoType.getTypeName()));

			// ケース固有のMojoパラメータを設定
			propPath = this.getClass()
					.getResource(testClassName + "/" + pf.caseName + "/" + pf.testDb + "/mojo_pram.properties")
					.getPath();
			prop = new Properties();
			prop.load(new FileInputStream(new File(propPath)));

			// 共通設定に上書きマージ
			mapper.map(prop, rtn);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return (E) rtn;
	}

	public enum TestDB {
		oracle, db2, postgresql, mysql, sqlserver, h2;
	}

	protected class PatternFixture {
		public PatternFixture(Class<?> testClass, String caseName, TestDB testDb) {
			this.testClass = testClass;
			this.caseName = caseName;
			this.testDb = testDb;
		}

		public Class<?> testClass;
		public String caseName;
		public TestDB testDb;
	}

	protected String getExpectedPath(PatternFixture pf) {
		return new File(this.getClass()
				.getResource(pf.testClass.getSimpleName() + "/" + pf.caseName + "/" + pf.testDb + "/expected")
				.getPath()).getAbsolutePath();

	}
}
