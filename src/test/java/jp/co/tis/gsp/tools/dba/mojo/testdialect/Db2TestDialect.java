package jp.co.tis.gsp.tools.dba.mojo.testdialect;

import org.seasar.extension.jdbc.gen.dialect.GenDialectRegistry;

import jp.co.tis.gsp.tools.dba.dialect.Db2Dialect;

public class Db2TestDialect extends Db2Dialect {

	public Db2TestDialect() {
		GenDialectRegistry.register(jp.co.tis.gsp.tools.dba.mojo.testdialect.MockDialect.class,
				new ExtendedDb2TestGenDialect());
	}

}
