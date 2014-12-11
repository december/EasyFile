/**
 * 按文件类型筛选文件的筛选器
 */
package logic.filter;

import org.apache.commons.net.ftp.FTPFile;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;

import util.FileUtil;

/**
 * 按文件类型筛选文件的筛选器
 * 
 * @author luyf
 */
public class TypeFilter extends ViewerFilter {
	String typeName;

	/**
	 * 构造函数
	 * @param type 指定类型
	 * @return TimeFilter 对应类型的筛选器
	 */
	public TypeFilter(String type) {
		typeName = type;
	}

	/**
	 * 选出对应类型的文件
	 * @param viewer 列表窗口
	 * @param parent 上级对象
	 * @param element 待筛选对象
	 * @return 是否文件为指定类型
	 */
	public boolean select(Viewer viewer, Object parentElement, Object element) {
		FTPFile e = (FTPFile) element;
		String temp = FileUtil.getFileType(e);
		return (temp.equals(typeName));
	}
}
