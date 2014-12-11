/**
 * 列出大于1MB文件的筛选器
 */
package logic.filter;

import org.apache.commons.net.ftp.FTPFile;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;

/**
 * 列出大于1MB文件的筛选器
 * 
 * @author luyf
 */
public class BigSizeFilter extends ViewerFilter {
	/**
	 * 选出大于1MB的文件
	 * @param viewer 列表窗口
	 * @param parent 上级对象
	 * @param element 待筛选对象
	 * @return 是否文件大小大于1MB
	 */
	public boolean select(Viewer viewer, Object parentElement, Object element) {
		FTPFile e = (FTPFile) element;
		return (e.getSize() >= 1000000);
	}
}