/**
 * 重命名操作
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
 * 重命名操作
 * 
 * @author Eternal_Answer
 *
 */
public class RenameAction extends Action implements MouseListener {
	MainWindow window;

	/**
	 * 构造函数
	 * 
	 * @param w
	 *            父窗口
	 */
	public RenameAction(MainWindow w) {
		window = w;
		setText("Rename");
		setToolTipText("");
		// setImageDescriptor(ImageDescriptor.createFromURL(EasyFileUtil.newURL(
		// "file:icons/manag.bmp" )));
	}

	public void run() {
		IStructuredSelection selection = window.getTableSelection();
		if (selection.size() != 1) {
			return;
		}
		FTPFile selectedFile = (FTPFile) selection.getFirstElement();
		InputDialog inputDialog = new InputDialog(window.getShell(), "重命名", "",
				selectedFile.getName(), null);
		if (inputDialog.open() == InputDialog.OK) {
			FTPAdapter.getInstance().renameServerFile(selectedFile.getName(),
					inputDialog.getValue());
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
