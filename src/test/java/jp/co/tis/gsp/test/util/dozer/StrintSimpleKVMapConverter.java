package jp.co.tis.gsp.test.util.dozer;

import java.util.HashMap;
import java.util.Map;

import org.dozer.CustomConverter;

/**
 * 文字列をkey,valueのMapに変換する簡易コンバータ。
 * 
 * <pre>
 * 書式<br />
 * 
 * db2をキー、/の後ろのクラスを値にマップを生成。
 * 
 * optionalDialects=db2/jp.co.tis.gsp.tools.dba.mojo.testdialect.Db2TestDialect;
 * 
 * </pre>
 * 
 * 
 */
public class StrintSimpleKVMapConverter implements CustomConverter {

	public Object convert(Object destination, Object optDialects, Class destClass, Class sourceClass) {

		if (optDialects == null)
			return null;

		if (!destClass.equals(Map.class))
			return null;

		String kvStr = optDialects.toString();

		Map<String, String> map = new HashMap<String, String>();

		for (String str : kvStr.split(";")) {
			String[] kv = str.split("\\/");
			map.put(kv[0], kv[1]);
		}

		return map;

	}
}