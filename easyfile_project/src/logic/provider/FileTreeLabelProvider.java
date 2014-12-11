package logic.provider;

import adapter.*;

import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;

import util.EasyFileUtil;

public class FileTreeLabelProvider extends LabelProvider {
	public String getText(Object element) {
		// System.out.println((String)element);
		// System.out.println(FTPAdapter.getInstance().getFile((String)element));
		if (((String) element).equals("/"))
			return "Ftp: " + FTPAdapter.getInstance().getCurServer();
		else
			return FTPAdapter.getInstance().getFile((String) element).getName();
	}

	public Image getImage(Object element) {

		if (!((String) element).equals("/")) {
			return EasyFileUtil.getImageRegistry().get("folder");
		} else {
			return EasyFileUtil.getImageRegistry().get("ftp");
		}
	}

}
