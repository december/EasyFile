/**
 * 按修改时间筛选文件的筛选器
 */
package logic.filter;

import java.util.Calendar;
import java.util.Date;

import org.apache.commons.net.ftp.FTPFile;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;

/**
 * 按修改时间筛选文件的筛选器
 * 
 * @author luyf
 */
public class TimeFilter extends ViewerFilter {
	Date now = new Date();
	Calendar rightNow = Calendar.getInstance();

	/**
	 * 构造函数
	 * @param time 指定时间
	 * @return TimeFilter 对应时间的筛选器
	 */
	public TimeFilter(int time) {
		rightNow.setTime(now);
		rightNow.add(Calendar.DAY_OF_YEAR, -time);
		now = rightNow.getTime();
	}

	/**
	 * 选出最近指定时间内修改的文件
	 * @param viewer 列表窗口
	 * @param parent 上级对象
	 * @param element 待筛选对象
	 * @return 是否文件在指定时间内修改过
	 */
	public boolean select(Viewer viewer, Object parentElement, Object element) {
		FTPFile e = (FTPFile) element;
		Date temp = e.getTimestamp().getTime();
		return (!temp.before(now));
	}
}
