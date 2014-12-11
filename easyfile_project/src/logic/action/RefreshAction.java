/**
 * 刷新操作
 */
package logic.action;

import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;

import display.MainWindow;

/**
 * 刷新操作
 * 
 * @author Eternal_Answer
 *
 */
public class RefreshAction implements Listener {
	MainWindow window;

	/**
	 * 构造函数
	 * 
	 * @param w
	 *            父窗口
	 */
	public RefreshAction(MainWindow w) {
		window = w;
	}

	@Override
	public void handleEvent(Event arg0) {
		window.gotoPath(window.getAddressText());

	}
}
