/**
 * 新建文件夹操作
 */
package logic.action;

import adapter.*;

import org.apache.commons.net.ftp.FTPFile;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;

import display.MainWindow;

/**
 * 新建文件夹操作
 * 
 * @author Eternal_Answer
 *
 */
public class NewFileAction extends Action implements MouseListener {
	MainWindow window;

	/**
	 * 构造函数
	 * 
	 * @param w
	 *            父窗口
	 */
	public NewFileAction(MainWindow w) {
		window = w;
		setText("New Directory");
		setToolTipText("");
		// setImageDescriptor(ImageDescriptor.createFromURL(EasyFileUtil.newURL(
		// "file:icons/manag.bmp" )));
	}

	public void run() {
		InputDialog inputDialog = new InputDialog(window.getShell(), "新文件名", "",
				"新建文件夹", null);
		if (inputDialog.open() == InputDialog.OK) {
			System.out.println(FTPAdapter.getInstance().getCurrentFTPPath() + '/' + inputDialog.getValue());
			FTPAdapter.getInstance().makeServerDirectory(FTPAdapter.getInstance().getCurrentFTPPath() + '/' + inputDialog.getValue());
			window.gotoPath(window.getAddressText());
		}
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
