/**
 * 打开文件上传界面
 */
package logic.action;

import adapter.*;

import org.eclipse.jface.action.*;
import org.eclipse.jface.resource.ImageDescriptor;

import display.*;
import util.EasyFileUtil;

/**
 * 打开文件上传界面
 * 
 * @author Eternal_Answer
 *
 */
public class UploadAction extends Action {
	private MainWindow window;

	/**
	 * 构造函数
	 * 
	 * @param w
	 *            父窗口
	 */
	public UploadAction(MainWindow w) {
		window = w;
		setText("&Upload@Alt+U");
		setToolTipText("");
		setImageDescriptor(ImageDescriptor.createFromURL(EasyFileUtil
				.newURL("file:icons/upLoad.bmp")));
	}

	public void run() {
		if (window.getMode() == FTPAdapter.WPMODE)
			return;
		UploadDialog uploadDialog = new UploadDialog(window);
		uploadDialog.setBlockOnOpen(true);
		uploadDialog.open();
	}
}
