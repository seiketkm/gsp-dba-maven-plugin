package jp.co.tis.gsp.test.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.seasar.extension.jdbc.util.ConnectionUtil;
import org.seasar.framework.util.DriverManagerUtil;
import org.seasar.framework.util.StatementUtil;

import jp.co.tis.gsp.tools.dba.dialect.Db2Dialect;
import jp.co.tis.gsp.tools.dba.dialect.Dialect;
import jp.co.tis.gsp.tools.dba.dialect.SqlserverDialect;

public class DBTestUtil {

	static public void dropSchema(String schema, String user, String password, String adminUser, String adminPassword,
			String url, String driver, TestDB testDb) throws SQLException {

		Connection conn = null;
		Statement stmt = null;
		String sql = null; // drop schema
		String existSql = ""; // schema存在確認

		try {

			DriverManagerUtil.registerDriver(driver);
			conn = DriverManager.getConnection(url, adminUser, adminPassword);
			stmt = conn.createStatement();
			Dialect dialect;

			switch (testDb) {
			case oracle:
				existSql = String.format("SELECT count(*) AS num FROM dba_users WHERE username='%s'", schema);
				if (getCount(existSql, url, adminUser, adminPassword) == 0)
					return;

				sql = String.format("DROP USER %s CASCADE", schema);
				stmt.execute(sql);
				break;
			case db2:
				existSql = String.format("select count(*) as num from SYSIBM.SYSSCHEMAAUTH where SCHEMANAME='%s'",
						schema);
				if (getCount(existSql, url, adminUser, adminPassword) == 0)
					return;

				dialect = new Db2Dialect();
				dialect.dropAll(user, password, adminUser, adminPassword, schema);
				sql = String.format("DROP SCHEMA %s RESTRICT", schema);
				break;
			case h2:
				// PUBLIC固定
				existSql = String.format(
						"SELECT COUNT(SCHEMA_NAME) AS num FROM INFORMATION_SCHEMA.SCHEMATA WHERE SCHEMA_NAME='%s'",
						"PUBLIC");
				if (getCount(existSql, url, adminUser, adminPassword) == 0)
					return;

				sql = String.format("DROP SCHEMA %s", "PUBLIC");
				break;
			case postgresql:
				existSql = String.format(
						"SELECT COUNT(SCHEMA_NAME) as num FROM INFORMATION_SCHEMA.SCHEMATA WHERE SCHEMA_NAME='%s'",
						schema);
				if (getCount(existSql, url, adminUser, adminPassword) == 0)
					return;

				sql = String.format("DROP SCHEMA %s CASCADE", schema);
				break;
			case sqlserver:
				existSql = String.format("SELECT COUNT(*) as num FROM sys.schemas WHERE name ='%s'", schema);
				if (getCount(existSql, url, adminUser, adminPassword) == 0)
					return;

				dialect = new SqlserverDialect();
				dialect.setUrl(url);
				dialect.dropAll(user, password, adminUser, adminPassword, schema);
				sql = String.format("DROP SCHEMA %s", schema);
				break;
			case mysql:
				sql = String.format("DROP DATABASE IF EXISTS %s", schema);
				break;
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			stmt.close();
			conn.close();
		}

	}

	static public int getCount(String sql, String url, String user, String password) {

		Statement stmt = null;
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(url, user, password);
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			rs.next();
			return rs.getInt(1);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			StatementUtil.close(stmt);
			ConnectionUtil.close(conn);
		}

		return 0;

	}
}
