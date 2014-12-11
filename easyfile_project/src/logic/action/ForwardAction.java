/**
 * 前进操作
 */
package logic.action;

import org.eclipse.jface.action.Action;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;

import display.MainWindow;

/**
 * 前进操作
 * 
 * @author Eternal_Answer
 *
 */
public class ForwardAction extends Action implements MouseListener {
	MainWindow window;

	/**
	 * 构造函数
	 * 
	 * @param w
	 *            父窗口
	 */
	public ForwardAction(MainWindow w) {
		window = w;
		/*
		 * setText("Forward"); setToolTipText(); setImageDescriptor();
		 */
	}

	public void run() {

	}

	@Override
	public void mouseDoubleClick(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseDown(MouseEvent arg0) {
		String path = new String("D:\\���\\program");
		window.gotoPath(path);
	}

	@Override
	public void mouseUp(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}
}
