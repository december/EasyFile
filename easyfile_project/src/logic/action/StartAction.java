/**
 * 开始上传操作
 */
package logic.action;

import java.io.File;
import adapter.*;
import java.util.ArrayList;

import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;

import basic.LGKLabel;
import display.*;

/**
 * 开始上传操作
 * 
 * @author Eternal_Answer
 *
 */
public class StartAction implements MouseListener {
	UploadDialog window;

	/**
	 * 构造函数
	 * 
	 * @param w
	 *            父窗口
	 */
	public StartAction(UploadDialog _window) {
		window = _window;
	}

	public void mouseDoubleClick(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	public void mouseUp(MouseEvent arg0) {
		System.out.println("addlabel");
		File localFile = new File(window.getFileText());
		if (!localFile.exists())
			return;
		/*
		 * String label = window.getLabelText(); // get input label
		 * ArrayList<String> labels = new ArrayList<String>();
		 * labels.add(label);
		 * 
		 * System.out.println("Label " + label);
		 */
		ArrayList<LGKLabel> labels = new ArrayList<LGKLabel>();
		for (int i = 0; i < window.tagTable.getTable().getItemCount(); ++i) {
			labels.add((LGKLabel) (window.tagTable.getTable().getItem(i)
					.getData()));
		}
		window.window.setStatus("Upload   " + localFile.getName() + " to "
				+ window.window.getFTPText() + ": "
				+ window.window.getAddressText());
		// FTPAdapter.getInstance().newUploadProcess(window.window.getAddressText()
		// + '/' + localFile.getName(), window.getFileText(), saveToWP);
		FTPAdapter.getInstance().addTag(
				window.window.getAddressText() + '/' + localFile.getName(),
				labels);
		// window.window.newUpload(window.getFileText(),
		// window.window.getAddressText() + '/' + localFile.getName(),
		// FTPAdapter.getInstance().getCurMode(), window.window.getSaveToWP());

		window.close();

	}

	public void mouseDown(MouseEvent event) {
	}
}
