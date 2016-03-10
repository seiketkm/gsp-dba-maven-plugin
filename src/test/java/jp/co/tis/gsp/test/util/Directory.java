package jp.co.tis.gsp.test.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class Directory extends Entry {

	protected List<Entry> list = new ArrayList<Entry>();

	public Directory(String path) {
		this.path = path;
	}

	public List<Entry> getList() {
		return this.list;
	}

	public Entry add(Entry entry) {
		// ルートディレクトリの設定.
		if (this.root == null) {
			this.root = this.path;
		}

		entry.root = this.path;

		list.add(entry);
		return this;
	}

	@Override
	public void debugPrint() {
		System.out.println(this);
		System.out.println(root);
		Iterator<Entry> it = list.iterator();
		while (it.hasNext()) {
			Entry entry = it.next();
			entry.debugPrint();
		}
	}

	@Override
	public boolean equals(Object obj) {

		// ディレクトリ判定
		if (!(obj instanceof Directory)) {
			return false;
		}

		// ディレクトリ名の比較
		if (!super.equals(obj)) {
			return false;
		}

		// ディレクトリ配下の比較
		List<Entry> testList = ((Directory) obj).list;

		// ディレクトリ配下のエントリ数比較
		if (list.size() != testList.size()) {
			return false;
		}

		// 比較のために名前ソート
		Collections.sort(list);
		Collections.sort(testList);

		Iterator<Entry> ite = list.iterator();
		Iterator<Entry> testIte = testList.iterator();

		// 一応、比較対象も一緒にhasNext
		while (ite.hasNext() && testIte.hasNext()) {
			Entry e = ite.next();
			Entry t = testIte.next();
			if (!e.equals(t)) {
				return false;
			}
		}

		return true;

	}

}
