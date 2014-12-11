/**
 * 主窗口文件列表的ContentProvider
 */
package logic.provider;

import adapter.*;
import java.util.List;

import org.eclipse.jface.viewers.*;

import org.apache.commons.net.ftp.FTPFile;

/**
 * 主窗口文件列表的ContentProvider
 * 
 * @author Eternal_Answer
 *
 */
public class FileTableContentProvider implements IStructuredContentProvider {
	/**
	 * 获取element路径下的文件列表
	 */
	public Object[] getElements(Object element) {
		System.out.println(FTPAdapter.getInstance().getCurMode());
		if (((String) element).equals("///////clear"))
			return new Object[0];
		Object[] kids = null;
		if (((String) element).equals(""))
			return new Object[0];
		// kids = (new File((String)element)).listFiles();
		FTPAdapter.getInstance().changeWorkingFTPDirectory((String) element);
		System.out.println("now start showList");
		List<FTPFile> files = FTPAdapter.getInstance().showServerFileList();
		System.out.println("now end showList");
		int cnt = 0;
		for (int i = 0; i < files.size(); ++i)
			if (files.get(i).getName() != null)
				++cnt;
		kids = new Object[cnt];
		for (int i = 0, j = 0; i < files.size(); ++i)
			if (files.get(i).getName() != null)
				kids[j++] = files.get(i);

		return kids;
	}

	public void dispose() {
	}

	public void inputChanged(Viewer viewer, Object old_object, Object new_object) {
	}
}
