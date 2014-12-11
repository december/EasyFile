/**
 * 列出所有非文件夹文件的筛选器
 */
package logic.filter;

import adapter.*;

import org.apache.commons.net.ftp.FTPFile;
import org.eclipse.jface.viewers.*;

/**
 * 列出所有非文件夹文件的筛选器
 * 
 * @author luyf
 */
public class AllowOnlyFilesFilter extends ViewerFilter {
	/**
	 * 列出所有非文件夹文件
	 * @param viewer 列表窗口
	 * @param parent 上级对象
	 * @param element 待筛选对象
	 * @return 是否为非文件夹文件
	 */
	public boolean select(Viewer viewer, Object parent, Object element) {
		if (((String) element).equals("/"))
			return false;
		else {
			// System.out.println("here       " + (String)element);
			FTPFile file = (FTPAdapter.getInstance().getFile((String) element));
			// System.out.println("here1111       " + file.isDirectory());
			return file.isFile();
		}
	}
}
