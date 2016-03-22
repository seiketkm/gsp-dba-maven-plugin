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

package jp.co.tis.gsp.tools.dba.mojo.testdialect;

import javax.persistence.TemporalType;

import org.seasar.extension.jdbc.gen.internal.dialect.Db2GenDialect;

public class ExtendedDb2TestGenDialect extends Db2GenDialect {
	
    public ExtendedDb2TestGenDialect() {
        super();
        columnTypeMap.put("date", ExtendedDb2ColumnType.DATE);
    }

    public static class ExtendedDb2ColumnType extends Db2ColumnType {
        private static ExtendedDb2ColumnType DATE = new ExtendedDb2ColumnType("date",
                String.class, false, TemporalType.DATE);
        
        public ExtendedDb2ColumnType(String dataType, Class<?> attributeClass,
                boolean lob, TemporalType temporalType) {
            super(dataType, attributeClass, lob);
        }
    }
}
