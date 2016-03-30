/*
 * Copyright (C) 2015 coastland
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * You may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package jp.co.tis.gsp.tools.dba.dialect;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import org.apache.maven.plugin.MojoExecutionException;
import org.codehaus.plexus.util.StringUtils;
import org.seasar.extension.jdbc.gen.dialect.GenDialectRegistry;
import org.seasar.extension.jdbc.util.ConnectionUtil;
import org.seasar.framework.util.DriverManagerUtil;
import org.seasar.framework.util.StatementUtil;
import org.seasar.framework.util.StringUtil;

import jp.co.tis.gsp.tools.db.TypeMapper;

public class Db2SedDialect extends Db2Dialect {
	
    public void dropAll(String user, String password, String adminUser,
            String adminPassword, String schema) throws MojoExecutionException {
    	
    	System.out.println("@@@@@@@@@@@@@@ DROP ALL START @@@@@@@@@@@@@@");
    	
    	super.dropAll(user, password, adminUser, adminPassword, schema);
    	
    	System.out.println("@@@@@@@@@@@@@@ DROP ALL END @@@@@@@@@@@@@@");
    }

	/**
	 * {@inheritDoc}
	 */
	public void setObjectInStmt(PreparedStatement stmt, int parameterIndex, String value, int sqlType)
			throws SQLException {
		if (sqlType == UN_USABLE_TYPE) {
			stmt.setNull(parameterIndex, Types.NULL);
		} else {
			stmt.setObject(parameterIndex, value, sqlType);
		}
	}
	
}