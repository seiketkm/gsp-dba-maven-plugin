package jp.co.tis.gsp.test.util.dozer;

import org.dozer.CustomConverter;

import jp.co.tis.gsp.tools.db.LengthSemantics;

public class LengthSemanticsConverter implements CustomConverter {

	public Object convert(Object destination, Object sem, Class destClass, Class sourceClass) {

		if (sem == null)
			return LengthSemantics.BYTE;

		if (!destClass.equals(LengthSemantics.class))
			return null;

		return LengthSemantics.valueOf(sem.toString());

	}
}