/**
 * 关闭主窗口操作
 */
package logic.action;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;

/**
 * 关闭父窗口
 * 
 * @author Eternal_Answer
 *
 */
public class CloseAction extends Action implements MouseListener {
	Dialog window;

	/**
	 * 构造函数
	 * 
	 * @param _window
	 *            父窗口
	 */
	public CloseAction(Dialog _window) {
		window = _window;
	}

	public void run() {
		window.close();
	}

	@Override
	public void mouseDoubleClick(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseDown(MouseEvent arg0) {
		window.close();
	}

	@Override
	public void mouseUp(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}
}
