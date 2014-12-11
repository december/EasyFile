/**
 * 登陆操作
 */
package logic.action;

import adapter.*;

import org.eclipse.jface.action.Action;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;

import display.*;

/**
 * 登陆操作
 * 
 * @author Eternal_Answer
 *
 */
public class LoginAction extends Action implements MouseListener {
	ConnectDialog window;

	/**
	 * 构造函数
	 * 
	 * @param w
	 *            父窗口
	 */
	public LoginAction(ConnectDialog w) {
		window = w;
	}

	public void run() {

	}

	@Override
	public void mouseDoubleClick(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseDown(MouseEvent arg0) {
		String username = window.getUsername();
		String password = window.getPassword();

		boolean loginRes = FTPAdapter.getInstance().logIn(username, password);
		System.out.println("really out of login");
		if (loginRes)
			window.login();
		else
			window.setWrong();
	}

	@Override
	public void mouseUp(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}
}
