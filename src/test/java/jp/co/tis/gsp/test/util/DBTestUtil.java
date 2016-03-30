package jp.co.tis.gsp.test.util;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import jp.co.tis.gsp.tools.dba.mojo.AbstractDdlMojoTest;
import jp.co.tis.gsp.tools.dba.mojo.ExecuteDdlMojo;
import jp.co.tis.gsp.tools.dba.mojo.ExecuteDdlMojoTest;

public class DBTestUtil {

	static public void dropSchema(String schema, String user, String password, String url, TestDB testDb)
			throws SQLException {

		Connection conn = null;
		Statement stmt = null;
		String sql = null;

		try {

			conn = DriverManager.getConnection(url, user, password);
			stmt = conn.createStatement();

			switch (testDb) {
			case oracle:
				sql = String.format("DROP USER %s CASCADE", schema);
				stmt.execute(sql);
				break;
			case db2:
				dropAllObject(testDb);
				sql = String.format("DROP SCHEMA %s RESTRICT", schema);
				break;
			case h2:
				// PUBLIC固定
				sql = String.format("DROP SCHEMA %s", "PUBLIC");
				break;
			case postgresql:
				sql = String.format("DROP SCHEMA %s CASCADE", schema);
				break;
			case sqlserver:
				dropAllObject(testDb);
				sql = String.format("DROP SCHEMA %s", schema);
				break;
			case mysql:
				sql = String.format("DROP DATABASE %s", schema);
				break;
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			stmt.close();
			conn.close();
		}

	}

	static public void dropAllObject(TestDB testDb) throws Exception {
		File pom = new File(DBTestUtil.class.getResource("").getPath() + "/DBTestUtil/dropSchema/pom.xml");

		ExecuteDdlMojoTest ddlTest = new ExecuteDdlMojoTest();
		ddlTest.setUp();
		ExecuteDdlMojo ddlMojo = ddlTest.lookupConfiguredMojo(pom, AbstractDdlMojoTest.EXECUTE_DDL, testDb);
		ddlMojo.execute();
	}

}
