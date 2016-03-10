package jp.co.tis.gsp.test.util.dozer;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;

import org.dozer.CustomConverter;

public class ClassPathFileConverter implements CustomConverter {

	public Object convert(Object destination, Object filePath, Class destClass, Class sourceClass) {

		if (filePath == null)
			return null;

		if (!destClass.equals(File.class))
			return null;

		File fileObj = null;
		try {
			URI path = Thread.currentThread().getContextClassLoader().getResource(filePath.toString()).toURI();
			fileObj = new File(path);
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}

		return fileObj;

	}
}