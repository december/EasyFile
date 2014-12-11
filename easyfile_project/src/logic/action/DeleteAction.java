/**
 * 删除文件操作
 */
package logic.action;

import java.util.Iterator;

import adapter.*;

import org.apache.commons.net.ftp.FTPFile;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;

import util.EasyFileUtil;

import display.MainWindow;

/**
 * 删除文件操作
 * 
 * @author Eternal_Answer
 *
 */
public class DeleteAction extends Action implements MouseListener {
	MainWindow window;

	/**
	 * 构造函数
	 * 
	 * @param w
	 *            父窗口
	 */
	public DeleteAction(MainWindow w) {
		window = w;
		setText("Delete");
		setToolTipText("");
		setImageDescriptor(ImageDescriptor.createFromURL(EasyFileUtil
				.newURL("file:icons/delete.bmp")));
	}

	public void run() {
		IStructuredSelection selection = window.getTableSelection();

		// for (int i=0;i<selection.size();++i)
		for (Iterator it = selection.iterator(); it.hasNext();) {
			FTPFile selectedFile = (FTPFile) (it.next());
			System.out.println(FTPAdapter.getInstance().getCurrentFTPPath()
					+ '/' + selectedFile.getName());
			FTPAdapter.getInstance().deleteServerFile(
					FTPAdapter.getInstance().getCurrentFTPPath() + '/'
							+ selectedFile.getName());
		}
		window.gotoPath(window.getAddressText());
	}

	@Override
	public void mouseDoubleClick(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseDown(MouseEvent arg0) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mouseUp(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}
}
