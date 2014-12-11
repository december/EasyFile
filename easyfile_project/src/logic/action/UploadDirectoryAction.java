/**
 * 打开文件夹选择器
 */
package logic.action;

import org.eclipse.jface.action.*;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.widgets.DirectoryDialog;

import display.*;
import util.EasyFileUtil;

/**
 * 打开文件夹选择器
 * 
 * @author Eternal_Answer
 *
 */
public class UploadDirectoryAction extends Action implements MouseListener {
	private UploadDialog window;

	/**
	 * 构造函数
	 * 
	 * @param w
	 *            父窗口
	 */
	public UploadDirectoryAction(UploadDialog w) {
		window = w;
		setText("&UploadDirectory");
		setImageDescriptor(ImageDescriptor.createFromURL(EasyFileUtil
				.newURL("file:icons/upLoad.png")));
	}

	public void run() {
		DirectoryDialog directoryDialog = new DirectoryDialog(
				window.getShell(), SWT.OPEN);
		directoryDialog.setFilterPath("");
		directoryDialog.setText("选择文件夹");
		String localFileName = directoryDialog.open();
		if (localFileName == null)
			return;
		window.setFileText(localFileName);
		window.uploadFiles = new String[1];
		window.uploadFiles[0] = localFileName;
		// System.out.println("!!!!!!!!!!!!" + localFileName);
	}

	@Override
	public void mouseDoubleClick(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	/**
	 * �����ʱ�ص���һ��
	 */
	@Override
	public void mouseDown(MouseEvent arg0) {
		DirectoryDialog directoryDialog = new DirectoryDialog(
				window.getShell(), SWT.OPEN);
		directoryDialog.setFilterPath("");
		directoryDialog.setText("选择文件夹");
		String localFileName = directoryDialog.open();
		if (localFileName == null)
			return;
		window.setFileText(localFileName);
		window.uploadFiles = new String[1];
		window.uploadFiles[0] = localFileName;
		// System.out.println("!!!!!!!!!!!!" + localFileName);
	}

	@Override
	public void mouseUp(MouseEvent arg0) {
		// TODO Auto-generated method stub
	}
}
