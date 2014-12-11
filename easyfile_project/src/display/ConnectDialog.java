/**
 * 连接FTP时输入用户名密码的对话框
 */
package display;

import org.eclipse.jface.dialogs.Dialog;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.*;

import swing2swt.layout.BorderLayout;
import logic.action.*;

import adapter.*;

/**
 * 连接FTP时由用户输入用户名密码的对话框
 * 
 * @author Eternal_Answer
 *
 */
public class ConnectDialog extends Dialog {
	Text nameText;
	Text passwordText;
	Label wrongLabel;
	LoginAction loginAction;
	MainWindow window;

	/**
	 * 构造函数
	 * 
	 * @param _window
	 *            父窗口
	 */
	public ConnectDialog(MainWindow _window) {
		super(_window.getShell());
		window = _window;
		loginAction = new LoginAction(this);
	}

	/**
	 * @Override
	 */
	protected void configureShell(Shell newShell) {
		super.configureShell(newShell);
		newShell.setText("Connect");
		newShell.setSize(150, 150);
	}

	protected void createButtonsForButtonBar(Composite parent) {
		// TODO Auto-generated method stub
	}

	/**
	 * 构造主窗口
	 */
	protected Control createDialogArea(Composite parent) {
		Composite container = new Composite(parent, SWT.NONE);
		container.setLayout(new BorderLayout(0, 0));
		// 锟斤拷锟斤拷锟斤拷锟斤拷虿糠锟�
		Composite inputCom = new Composite(container, SWT.NONE);
		inputCom.setLayout(new GridLayout(2, false));
		inputCom.setLayoutData(BorderLayout.CENTER);
		// 锟斤拷锟矫帮拷钮锟斤拷锟斤拷
		Composite buttonCom = new Composite(container, SWT.NONE);
		buttonCom.setLayout(new GridLayout(3, false));
		buttonCom.setLayoutData(BorderLayout.SOUTH);
		// 锟斤拷锟斤拷锟斤拷锟津部凤拷
		Label nameLabel = new Label(inputCom, SWT.NONE);
		nameLabel.setText("Username");
		nameText = new Text(inputCom, SWT.BORDER);
		// To set "enter" listener to name text area
		nameText.addKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent e) {

				if (e.character == SWT.CR) {
					loginAction.mouseDown(null);
				}
			}
		});
		Label passwordLabel = new Label(inputCom, SWT.NONE);
		passwordLabel.setText("password");
		passwordText = new Text(inputCom, SWT.PASSWORD | SWT.BORDER);
		// to set "enter" listener to password text area
		passwordText.addKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent e) {

				if (e.character == SWT.CR) {
					loginAction.mouseDown(null);
				}
			}
		});
		// 锟斤拷浒磁ワ拷锟斤拷锟�
		Label fillLabel = new Label(buttonCom, SWT.PUSH);
		fillLabel.setLayoutData(new GridData(GridData.GRAB_HORIZONTAL));
		Button enterButton = new Button(buttonCom, SWT.PUSH);
		enterButton.setText("OK");
		enterButton.addMouseListener(loginAction);
		Button cancelButton = new Button(buttonCom, SWT.PUSH);
		cancelButton.setText("Cancel");
		cancelButton.addMouseListener(new MouseAdapter() {
			public void mouseDown(MouseEvent event) {
				closeDialog();
			}
		});

		return container;
	}

	/**
	 * 获取输入框中的用户名
	 * 
	 * @return
	 */
	public String getUsername() {
		return nameText.getText();
	}

	/**
	 * 获取输入框中的密码
	 * 
	 * @return
	 */
	public String getPassword() {
		return passwordText.getText();
	}

	/**
	 * 通过用户名密码进行登录
	 */
	public void login() {
		window.mode = FTPAdapter.FTPMODE;
		window.addFTPHistory(window.getFTPText());
		window.FTPItem.setSelection(true);
		window.WPItem.setSelection(false);
		// window.treeSetInput("");
		window.gotoPath("/");
		this.close();
	}

	/**
	 * 输入错误的用户名密码时，给出提示
	 */
	public void setWrong() {
		System.out.println("here");
		wrongLabel.setText("Wrong username and password");
	}

	/**
	 * 关闭窗口
	 */
	public void closeDialog() {
		close();
	}
}
