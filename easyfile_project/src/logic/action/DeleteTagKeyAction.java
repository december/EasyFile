/**
 * 删除标签类型操作
 */
package logic.action;

import adapter.*;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.IStructuredSelection;

import util.EasyFileUtil;
import basic.LGKLabel;
import display.ManageTagDialog;

/**
 * 删除标签类型操作
 * 
 * @author Eternal_Answer
 *
 */
public class DeleteTagKeyAction extends Action {
	ManageTagDialog window;

	/**
	 * 构造函数
	 * 
	 * @param _window
	 *            父窗口
	 */
	public DeleteTagKeyAction(ManageTagDialog _window) {
		window = _window;
		setText("Delete");
		setImageDescriptor(ImageDescriptor.createFromURL(EasyFileUtil
				.newURL("file:icons/delete.bmp")));
	}

	public void run() {
		IStructuredSelection selection = window.getTableSelection();
		if (selection.size() != 1) {
			return;
		}
		LGKLabel selectedTag = (LGKLabel) selection.getFirstElement();
		FTPAdapter.getInstance().delKey(selectedTag);
		System.out.println("Delete key" + selectedTag.getKey());
		window.refresh();
	}
}
