/**
 * 增加标签类型
 */
package logic.action;

import org.eclipse.jface.action.Action;

import display.AddTagKeyDialog;
import display.ManageTagDialog;

/**
 * 增加标签类型
 * 
 * @author Eternal_Answer
 *
 */
public class AddTagKeyAction extends Action {
	ManageTagDialog window;

	/**
	 * 构造函数
	 * 
	 * @param _window
	 *            父窗口
	 */
	public AddTagKeyAction(ManageTagDialog _window) {
		window = _window;
		setText("Add Tag Key");
	}

	public void run() {
		AddTagKeyDialog addTagKeyDialog = new AddTagKeyDialog(window);
		addTagKeyDialog.setBlockOnOpen(true);
		addTagKeyDialog.open();
	}
}
