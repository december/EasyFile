/**
 * 打开文件选择器
 */
package logic.action;

import java.io.File;

import org.eclipse.jface.action.*;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.widgets.FileDialog;

import display.*;
import util.EasyFileUtil;

/**
 * 打开文件选择器
 * 
 * @author Eternal_Answer
 *
 */
public class UploadFileAction extends Action implements MouseListener {
	private UploadDialog window;

	/**
	 * 构造函数
	 * 
	 * @param w
	 *            父窗口
	 */
	public UploadFileAction(UploadDialog w) {
		window = w;
		setText("&UploadFile");
		setToolTipText("�ϴ��ļ�");
		setImageDescriptor(ImageDescriptor.createFromURL(EasyFileUtil
				.newURL("file:icons/upLoad.png")));
	}

	public void run() {
		FileDialog fileDialog = new FileDialog(window.getShell(), SWT.OPEN
				| SWT.MULTI);
		fileDialog.setFilterPath("");
		fileDialog.setText("上传文件");
		String localFileName = fileDialog.open();
		String[] localFileNames = fileDialog.getFileNames();
		String path = fileDialog.getFilterPath();
		for (int i = 0; i < localFileNames.length; ++i)
			localFileNames[i] = path + File.separator + localFileNames[i];
		if (localFileName == null)
			return;
		if (localFileNames.length == 1)
			window.setFileText(localFileName);
		else
			window.setFileText(localFileName + "等" + localFileNames.length
					+ "个文件");
		window.uploadFiles = localFileNames;
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
		FileDialog fileDialog = new FileDialog(window.getShell(), SWT.OPEN
				| SWT.MULTI);
		fileDialog.setFilterPath("");
		fileDialog.setText("上传文件");
		String localFileName = fileDialog.open();
		String[] localFileNames = fileDialog.getFileNames();
		String path = fileDialog.getFilterPath();
		for (int i = 0; i < localFileNames.length; ++i)
			localFileNames[i] = path + File.separator + localFileNames[i];
		if (localFileName == null)
			return;
		if (localFileNames.length == 1)
			window.setFileText(localFileName);
		else
			window.setFileText(localFileName + "等" + localFileNames.length
					+ "个文件");
		window.uploadFiles = localFileNames;
	}

	@Override
	public void mouseUp(MouseEvent arg0) {
		// TODO Auto-generated method stub
	}
}
