package jp.co.tis.gsp.test.util.dozer;

import java.io.File;

import org.dozer.CustomConverter;

/**
 * 指定されたファイル・フォルダパスから{@code java.io.File}オブジェクトを生成します.
 * 
 */
public class ClassPathFileConverter implements CustomConverter {

	public Object convert(Object destination, Object filePath, Class destClass, Class sourceClass) {

		if (filePath == null)
			return null;

		if (!destClass.equals(File.class))
			return null;

		File fileObj = null;
		try {
			String path = Thread.currentThread().getContextClassLoader().getResource("").getPath() + "/"
					+ filePath.toString();
			fileObj = new File(path);
		} catch (Exception e) {
		}

		return fileObj;

	}
}