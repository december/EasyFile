/**
 * 列出所有文件的筛选器
 */
package logic.filter;

import org.eclipse.jface.viewers.*;

/**
 * 列出所有文件的筛选器
 * 
 * @author luyf
 */
public class AllowAllFilter extends ViewerFilter {
	/**
	 * 列出所有文件
	 * @param viewer 列表窗口
	 * @param parent 上级对象
	 * @param element 待筛选对象
	 * @return always true
	 */
	public boolean select(Viewer viewer, Object parent, Object element) {
		return true;
	}
}