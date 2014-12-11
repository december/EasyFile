/**
 * 修改标签操作
 */
package logic.action;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.IStructuredSelection;

import util.EasyFileUtil;
import basic.LGKLabel;
import display.ManageTagDialog;
import display.ModifyTagDialog;

/**
 * 修改标签操作
 * 
 * @author Eternal_Answer
 *
 */
public class ModifyTagAction extends Action {
	ManageTagDialog window;

	/**
	 * 构造函数
	 * 
	 * @param _window
	 *            父窗口
	 */
	public ModifyTagAction(ManageTagDialog _window) {
		window = _window;
		setText("Modify");
		setImageDescriptor(ImageDescriptor.createFromURL(EasyFileUtil
				.newURL("file:icons/manage.bmp")));
	}

	public void run() {
		IStructuredSelection selection = window.getTableSelection();
		LGKLabel selectedTag = (LGKLabel) selection.getFirstElement();
		ModifyTagDialog modifyTagDialog = new ModifyTagDialog(window,
				selectedTag);
		modifyTagDialog.setBlockOnOpen(true);
		modifyTagDialog.open();
	}
}
