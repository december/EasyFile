/*
 * A filter that makes only file displayed in the file tree
 */
package logic.filter;

import adapter.*;

import org.apache.commons.net.ftp.FTPFile;
import org.eclipse.jface.viewers.*;

/**
 * 列出所有文件夹的筛选器
 * 
 * @author luyf
 */
public class AllowOnlyFoldersFilter extends ViewerFilter {
	/**
	 * 列出所有文件夹
	 * @param viewer 列表窗口
	 * @param parent 上级对象
	 * @param element 待筛选对象
	 * @return 是否为文件夹
	 */
	public boolean select(Viewer viewer, Object parent, Object element) {
		if (((String) element).equals("/"))
			return true;
		else {
			// System.out.println("here       " + (String)element);
			FTPFile file = (FTPAdapter.getInstance().getFile((String) element));
			// System.out.println("here1111       " + file.isDirectory());
			return file.isDirectory();
		}
	}
}
