/**
 * 管理标签系统操作
 */
package logic.action;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.resource.ImageDescriptor;

import util.EasyFileUtil;
import display.MainWindow;
import display.ManageTagDialog;

/**
 * 管理标签系统操作
 * 
 * @author Eternal_Answer
 *
 */
public class ManageTagAction extends Action {
	MainWindow window;

	/**
	 * 构造函数
	 * 
	 * @param _window
	 *            父窗口
	 */
	public ManageTagAction(MainWindow _window) {
		window = _window;
		setText("Manage Tags");
		setImageDescriptor(ImageDescriptor.createFromURL(EasyFileUtil
				.newURL("file:icons/manage.bmp")));
	}

	public void run() {
		ManageTagDialog manageTagDialog = new ManageTagDialog(window);
		manageTagDialog.setBlockOnOpen(true);
		manageTagDialog.open();
	}
}
