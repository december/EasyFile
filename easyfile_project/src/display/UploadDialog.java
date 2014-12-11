/**
 * 上传文件对话框
 */
package display;

import org.eclipse.jface.dialogs.Dialog;

import java.util.ArrayList;

import org.eclipse.jface.viewers.*;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowData;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.*;

import swing2swt.layout.BorderLayout;
import logic.action.*;

/**
 * 上传文件对话框
 * 
 * @author Eternal_Answer
 *
 */
public class UploadDialog extends Dialog {
	/**
	 * 需要上传的本地文件路径
	 */
	public String[] uploadFiles;

	Text fileText;
	Label wrongLabel;
	UploadFileAction uploadFileAction;
	UploadDirectoryAction uploadDirectoryAction;
	CloseAction closeAction;
	int labelNum;
	Composite inputCom;
	/**
	 * 显示标签的TableViewer
	 */
	public TableViewer tagTable;
	Table table;
	/**
	 * 是否保存至网盘的Button
	 */
	public Button saveToWPButton;
	Composite fileSelection;
	/**
	 * 父窗口
	 */
	public MainWindow window;

	/**
	 * 构造函数
	 * 
	 * @param _window
	 *            父窗口
	 */
	public UploadDialog(MainWindow _window) {
		super(_window.getShell());
		window = _window;
		uploadFileAction = new UploadFileAction(this);
		uploadDirectoryAction = new UploadDirectoryAction(this);
		closeAction = new CloseAction(this);
	}

	/**
	 * @Override
	 */
	protected void configureShell(Shell newShell) {
		super.configureShell(newShell);
		newShell.setText("Upload");
		newShell.setSize(600, 100);
	}

	protected void createButtonsForButtonBar(Composite parent) {
	}

	/**
	 * 构造对话框
	 */
	protected Control createDialogArea(Composite parent) {
		final Composite container = new Composite(parent, SWT.NONE);
		container.setLayout(new BorderLayout(0, 0, 600, 80));
		// inputCom
		inputCom = new Composite(container, SWT.NONE);
		inputCom.setLayoutData(BorderLayout.NORTH);
		inputCom.setLayout(new RowLayout());
		// buttonCom
		final Composite buttonCom = new Composite(container, SWT.NONE);
		buttonCom.setLayoutData(BorderLayout.SOUTH);
		GridLayout gl = new GridLayout(4, false);
		gl.marginRight = 0;
		buttonCom.setLayout(gl);
		// ����ļ������
		Label fileLabel = new Label(inputCom, SWT.NONE);
		fileLabel.setText("Upload File");
		fileText = new Text(inputCom, SWT.BORDER);
		fileText.setLayoutData(new RowData(200, 20));
		Button openFileButton = new Button(inputCom, SWT.PUSH);
		openFileButton.setText("Open File");
		openFileButton.addMouseListener(uploadFileAction);
		Button openDirectoryButton = new Button(inputCom, SWT.PUSH);
		openDirectoryButton.addMouseListener(uploadDirectoryAction);
		openDirectoryButton.setText("Open Directory");
		// set fill data
		saveToWPButton = new Button(buttonCom, SWT.CHECK);
		saveToWPButton.setText("是否要同步至网盘");
		saveToWPButton.setSelection((window.saveToWP == 1));
		GridData fillData = new GridData();
		fillData.grabExcessVerticalSpace = true;
		fillData.grabExcessHorizontalSpace = true;
		fillData.verticalAlignment = GridData.FILL;
		fillData.horizontalAlignment = GridData.FILL;
		Label fillLabel = new Label(buttonCom, SWT.NONE);
		fillLabel.setLayoutData(fillData);
		Button enterButton = new Button(buttonCom, SWT.PUSH);
		enterButton.setText("OK");
		final UploadDialog now = this;
		enterButton.addMouseListener(new MouseAdapter() {
			public void mouseDown(MouseEvent e) {
				AddTagDialog tagDialog = new AddTagDialog(now, uploadFiles);
				tagDialog.setBlockOnOpen(true);
				tagDialog.open();
			}
		});
		GridData buttonData = new GridData();
		buttonData.widthHint = 70;
		buttonData.heightHint = 30;
		enterButton.setLayoutData(buttonData);

		Button cancelButton = new Button(buttonCom, SWT.PUSH);
		cancelButton.setText("Cancel");
		cancelButton.addMouseListener(closeAction);
		cancelButton.setLayoutData(buttonData);

		return container;
	}

	/**
	 * 设置文件输入框的内容
	 * 
	 * @param fileName
	 *            显示内容
	 */
	public void setFileText(String fileName) {
		fileText.setText(fileName);
	}

	/**
	 * 获取文件文本框中的内容
	 * 
	 * @return
	 */
	public String getFileText() {
		return fileText.getText();
	}
}
