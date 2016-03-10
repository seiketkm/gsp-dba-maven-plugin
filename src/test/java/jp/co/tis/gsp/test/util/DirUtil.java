package jp.co.tis.gsp.test.util;

import java.io.File;

public class DirUtil {

	/**
	 * 指定ディレクトリ配下のファイル・ディレクトリエントリを収集します.
	 * 
	 * @param path
	 *            - ルートディレクトリのパス
	 * @return ルートディレクトリエントリ
	 */
	public static Entry collectEntry(String path) {

		File rootDir = new File(path);
		File[] list = rootDir.listFiles();

		if (list == null)
			return null;

		Entry root = new Directory(path);

		try {
			for (File f : list) {
				if (f.isDirectory()) {
					root.add(new Directory(f.getAbsolutePath()));
					collectEntry(f.getAbsolutePath());
				} else {
					root.add(new FileEntry(f.getAbsolutePath()));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return root;
	}
}
