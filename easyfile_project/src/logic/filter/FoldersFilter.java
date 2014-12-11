/**
 * 列出所有文件夹的筛选器
 */
package logic.filter;

import org.apache.commons.net.ftp.FTPFile;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;

/**
 * 列出所有文件夹的筛选器
 * 
 * @author luyf
 */
public class FoldersFilter extends ViewerFilter {
	/**
	 * 列出所有非文件夹文件
	 * @param viewer 列表窗口
	 * @param parent 上级对象
	 * @param element 待筛选对象
	 * @return 是否为非文件夹文件
	 */
	public boolean select(Viewer viewer, Object parentElement, Object element) {
		FTPFile e = (FTPFile) element;
		return e.isDirectory();
	}
}