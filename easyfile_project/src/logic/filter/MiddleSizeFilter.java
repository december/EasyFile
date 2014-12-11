/**
 * 列出文件大小10KB-1MB的筛选器
 */
package logic.filter;

import org.apache.commons.net.ftp.FTPFile;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;

/**
 * 列出文件大小10KB-1MB的筛选器
 * 
 * @author luyf
 */
public class MiddleSizeFilter extends ViewerFilter {
	/**
	 * 选出10KB-1MB的文件
	 * @param viewer 列表窗口
	 * @param parent 上级对象
	 * @param element 待筛选对象
	 * @return 是否文件大小在10KB-1MB之间
	 */
	public boolean select(Viewer viewer, Object parentElement, Object element) {
		FTPFile e = (FTPFile) element;
		return (e.getSize() > 10000 && e.getSize() < 1000000);
	}
}