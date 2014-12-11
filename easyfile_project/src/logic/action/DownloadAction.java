/**
 * 下载文件操作
 */
package logic.action;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import adapter.*;

import org.apache.commons.net.ftp.FTPFile;
import org.eclipse.jface.action.*;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.DirectoryDialog;

import display.MainWindow;
import util.EasyFileUtil;

/**
 * 下载文件操作
 * 
 * @author Eternal_Answer
 *
 */
public class DownloadAction extends Action {
	private MainWindow window;

	/**
	 * 构造函数
	 * 
	 * @param _window
	 *            父窗口
	 */
	public DownloadAction(MainWindow w) {
		window = w;
		setText("&Download@Alt+D");
		setImageDescriptor(ImageDescriptor.createFromURL(EasyFileUtil
				.newURL("file:icons/download.bmp")));
	}

	public void run() {
		IStructuredSelection selection = window.getTableSelection();
		if (selection.size() != 1) {
			return;
		}
		FTPFile selectedFile = (FTPFile) selection.getFirstElement();
		DirectoryDialog fileDialog = new DirectoryDialog(window.getShell(),
				SWT.OPEN);
		fileDialog.setFilterPath("");
		fileDialog.setText("保存地址选择");

		String localFileName = fileDialog.open();
		// System.out.println(localFileName+" "+window.getAddressText()+File.separator+selectedFile.getName());
		if (localFileName == null)
			return;
		window.setStatus("dowload" + selectedFile.getName() + "  to"
				+ localFileName);
		List<String> l1 = new ArrayList<String>();
		List<String> l2 = new ArrayList<String>();
		l1.add(FTPAdapter.getInstance().getCurrentFTPPath() + '/'
				+ selectedFile.getName());
		l2.add(localFileName + File.separator + selectedFile.getName());
		FTPAdapter.getInstance().newDownloadProcess(l1, l2,
				FTPAdapter.getInstance().getCurMode());
		// window.gotoPath(window.getAddressText());
		window.newDownload(l1, l2, FTPAdapter.getInstance().getCurMode());
	}
}
