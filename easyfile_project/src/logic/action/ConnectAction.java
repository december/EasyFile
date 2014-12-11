/**
 * 连接FTP操作
 */
package logic.action;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.resource.ImageDescriptor;

import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;

import util.EasyFileUtil;

import display.MainWindow;

/**
 * 连接FTP操作
 * 
 * @author Eternal_Answer
 *
 */
public class ConnectAction extends Action implements Listener {
	MainWindow window;

	/**
	 * 构造函数
	 * 
	 * @param _window
	 *            父窗口
	 */
	public ConnectAction(MainWindow w) {
		window = w;
		setText("&Login@Alt+L");
		setToolTipText("Login");
		setImageDescriptor(ImageDescriptor.createFromURL(EasyFileUtil
				.newURL("file:icons/login.bmp")));
	}

	public void run() {
		String ftpAddress = window.getFTPText();
		window.gotoFTP(ftpAddress);
	}

	@Override
	public void handleEvent(Event arg0) {
		String ftpAddress = window.getFTPText();
		window.gotoFTP(ftpAddress);
	}
}
