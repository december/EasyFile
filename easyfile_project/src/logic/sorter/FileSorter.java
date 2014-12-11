/**
 * 以修改时间、文件名称、文件类型、文件大小为index的排序器
 */
package logic.sorter;

import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerSorter;
import org.apache.commons.net.ftp.*;

import util.FileUtil;

/**
 * 以修改时间、文件名称、文件类型、文件大小为index的排序器
 * 
 * @author luyf
 */
public class FileSorter extends ViewerSorter {
	private int column;

	public void doSort(int column) {

		this.column = column;
	}

	public int compare(Viewer viewer, Object e1, Object e2) {
		// do category first
		int cat1 = category(e1);
		int cat2 = category(e2);

		if (cat1 != cat2) {
			return cat1 - cat2;
		}

		FTPFile file1 = (FTPFile) e1;
		FTPFile file2 = (FTPFile) e2;

		// the value of column determine the method of sort
		switch (column) {
		// don't sort
		case 0:
			return 0;
			// sort by filename Desc
		case 1: {
			String str1 = file1.getName();
			String str2 = file2.getName();
			int nameDesc = str2.compareToIgnoreCase(str1);
			return nameDesc;
		}
		// sort by filename Asc
		case -1: {
			String str1 = file1.getName();
			String str2 = file2.getName();
			int nameAsc = str1.compareToIgnoreCase(str2);
			return nameAsc;
		}
		// sort by filesize Desc
		case 2: {
			Long l1 = file1.getSize();
			Long l2 = file2.getSize();
			int sizeDesc = l2.compareTo(l1);
			return sizeDesc;
		}
		// sort by filesize Asc
		case -2: {
			Long l1 = file1.getSize();
			Long l2 = file2.getSize();
			int sizeAsc = l1.compareTo(l2);
			return sizeAsc;
		}
		// sort by file type Desc
		case 3: {
			String str1 = FileUtil.getFileType(file1);
			String str2 = FileUtil.getFileType(file2);
			int typeDesc = str2.compareToIgnoreCase(str1);
			return typeDesc;
		}
		// sort by file type Asc
		case -3: {
			String str1 = FileUtil.getFileType(file1);
			String str2 = FileUtil.getFileType(file2);
			int typeAsc = str1.compareToIgnoreCase(str2);
			return typeAsc;
		}
		// sort by file last modified time Desc
		case 4: {
			Long l1 = file1.getTimestamp().getTimeInMillis();
			Long l2 = file2.getTimestamp().getTimeInMillis();
			int lastModifiedDesc = l2.compareTo(l1);
			return lastModifiedDesc;
		}
		// sort by filesize Asc
		case -4: {
			Long l1 = file1.getTimestamp().getTimeInMillis();
			Long l2 = file2.getTimestamp().getTimeInMillis();
			int lastModifiedAsc = l1.compareTo(l2);
			return lastModifiedAsc;
		}
		// sort by file status Desc
		case 5: {
			/*
			 * String str1 = FileUtil.getFileStatus( file1 ); String str2 =
			 * FileUtil.getFileStatus( file2 ); int typeDesc =
			 * str2.compareToIgnoreCase( str1 ); return typeDesc;
			 */
			return 0;
		}
		// sort by file status Asc
		case -5: {
			/*
			 * String str1 = FileUtil.getFileStatus( file1 ); String str2 =
			 * FileUtil.getFileStatus( file2 ); int typeAsc =
			 * str1.compareToIgnoreCase( str2 ); return typeAsc;
			 */
			return 0;
		}
		}

		return 0;
	}

	public int category(Object element) {
		return ((FTPFile) element).isDirectory() ? 0 : 1;
	}

}
