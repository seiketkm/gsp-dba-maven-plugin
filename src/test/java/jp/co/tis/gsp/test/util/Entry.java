package jp.co.tis.gsp.test.util;

import java.util.regex.Pattern;

public abstract class Entry implements Comparable<Entry> {

	protected String root;

	protected String path;

	public String getPath() {
		return path;
	}

	public Entry add(Entry entry) throws Exception {
		throw new Exception();
	}

	public String toString() {
		return getPath();
	}

	public abstract void debugPrint();

	@Override
	public boolean equals(Object obj) {
		Entry entry = (Entry) obj;

		String rr = Pattern.quote(root);
		String ss = Pattern.quote(entry.root);

		String mypath = this.getPath().replaceFirst(rr, "");
		String comparePath = entry.path.replaceFirst(ss, "");

		if (!mypath.equals(comparePath)) {
			return false;
		}

		return true;
	}

	@Override
	public int compareTo(Entry o) {
		return this.path.compareTo(o.path);
	}

}
