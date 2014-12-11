/**
 * 列出一个月内未被修改的文件的筛选器
 */
package logic.filter;

import java.util.Calendar;
import java.util.Date;

import org.apache.commons.net.ftp.FTPFile;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;

/**
 * 列出一个月内未被修改的文件的筛选器
 * 
 * @author luyf
 */
public class OldFileFilter extends ViewerFilter {

	/**
	 * 选出一个月内未被修改的文件
	 * @param viewer 列表窗口
	 * @param parent 上级对象
	 * @param element 待筛选对象
	 * @return 是否文件一个月内未被修改
	 */
	public boolean select(Viewer viewer, Object parentElement, Object element) {
		Date now = new Date();
		Calendar rightNow = Calendar.getInstance();
		rightNow.setTime(now);
		rightNow.add(Calendar.DAY_OF_YEAR, -30);
		now = rightNow.getTime();
		FTPFile e = (FTPFile) element;
		Date temp = e.getTimestamp().getTime();
		return (temp.before(now));
	}
}