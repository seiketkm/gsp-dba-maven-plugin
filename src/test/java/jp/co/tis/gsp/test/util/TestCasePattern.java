package jp.co.tis.gsp.test.util;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jp.co.tis.gsp.tools.dba.mojo.AbstractDdlMojoTest.TestDB;

@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.METHOD })
public @interface TestCasePattern {

	String testCase() default "";

	TestDB[] testDb() default {};

}