/**
 * 修改文件标签操作
 */
package logic.action;

import org.apache.commons.net.ftp.FTPFile;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.viewers.IStructuredSelection;

import display.MainWindow;
import display.ModifyFileTagDialog;

/**
 * 修改文件标签操作
 * 
 * @author Eternal_Answer
 *
 */
public class ModifyFileTagAction extends Action {
	MainWindow window;

	/**
	 * 构造函数
	 * 
	 * @param _window
	 *            父窗口
	 */
	public ModifyFileTagAction(MainWindow _window) {
		window = _window;
		setText("Modify File Tag");
	}

	public void run() {
		IStructuredSelection selection = window.getTableSelection();
		if (selection.size() != 1) {
			return;
		}
		FTPFile selected_file = (FTPFile) selection.getFirstElement();

		ModifyFileTagDialog modifyFileTagDialog = new ModifyFileTagDialog(
				window, window.getAddressText() + "/" + selected_file.getName());
		modifyFileTagDialog.setBlockOnOpen(true);
		modifyFileTagDialog.open();
	}
}
