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
				for (TestDB db : tp.testDb()) {
					caseList.add(new PatternFixture(d.getTestClass(), tp.testCase(), db));
				}
			}

		};

		protected void finished(Description description) {
			caseList = null;
		}
	};

	List<PatternFixture> caseList;

	protected E bindParameter(PatternFixture pf) {
		Object rtn = null;
		try {
			String testClassName = pf.testClass.getSimpleName();
			String propPath = this.getClass()
					.getResource(testClassName + "/" + pf.caseName + "/" + pf.testDb + "/mojo_pram.properties")
					.getPath();
			Properties prop = new Properties();
			prop.load(new FileInputStream(new File(propPath)));
			DozerBeanMapper mapper = new DozerBeanMapper();
			List<String> myMappingFiles = new ArrayList<String>();

			String mapperFile = this.getClass().getResource(testClassName + "/paramMapper.xml").toURI().toURL()
					.toString();
			myMappingFiles.add(mapperFile);
			mapper.setMappingFiles(myMappingFiles);
			rtn = mapper.map(prop, Class.forName(mojoType.getTypeName()));
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
