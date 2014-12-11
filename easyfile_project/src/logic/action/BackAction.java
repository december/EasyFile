/**
 * 后退操作
 */
package logic.action;

import adapter.*;

import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;

import display.MainWindow;

/**
 * 后退操作
 * 
 * @author Eternal_Answer
 *
 */
public class BackAction implements Listener {
	MainWindow window;

	/**
	 * 构造函数
	 * 
	 * @param _window
	 *            父窗口
	 */
	public BackAction(MainWindow w) {
		window = w;
		// setText("Back");
		// setToolTipText();
		// setImageDescriptor();
	}

	@Override
	public void handleEvent(Event arg0) {
		FTPAdapter.getInstance().changetoParentFTPDirectory();
		String path = FTPAdapter.getInstance().getCurrentFTPPath();
		// String path = new String("D:\\锟斤拷锟�\\program");
		System.out.println(path);
		window.gotoPath(path);
		window.setStatus("back to " + window.getFTPText() + " / "
				+ window.getAddressText());

	}
}
