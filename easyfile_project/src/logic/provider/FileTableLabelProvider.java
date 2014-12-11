/**
 *主窗口文件列表的LabelProvider
 */
package logic.provider;

import adapter.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.swt.graphics.Image;

import util.*;

import org.apache.commons.net.ftp.FTPFile;

import display.MainWindow;
import basic.LGKLabel;

/**
 * 主窗口文件列表的LabelProvider
 * 
 * @author Eternal_Answer
 *
 */
public class FileTableLabelProvider implements ITableLabelProvider {
	private MainWindow window;

	public FileTableLabelProvider(MainWindow _window) {
		super();
		window = _window;
	}

	public String getColumnText(Object element, int column_index) {
		if (column_index == 0) {
			return ((FTPFile) element).getName();
		}
		if (column_index == 1) {
			if (((FTPFile) element).isDirectory()) {
				return "";
			} else {
				return FileUtil.getFileSize((FTPFile) element);
			}
		}
		if (column_index == 2) {
			return "" + FileUtil.getFileType((FTPFile) element);
		}
		if (column_index == 3) {
			// System.out.println(window.getMode());
			if (window.getMode() == FTPAdapter.FTPMODE) {
				Date lastModifiedDate = ((FTPFile) element).getTimestamp()
						.getTime();
				SimpleDateFormat dateFormat = new SimpleDateFormat(
						"yyyy/MM/dd HH:mm");
				return dateFormat.format(lastModifiedDate);
			} else
				return "";
		}
		if (column_index >= 4) {
			ArrayList<LGKLabel> Tags = FTPAdapter.getInstance().showTag(
					FTPAdapter.getInstance().getCurrentFTPPath() + "/"
							+ ((FTPFile) element).getName());
			// System.out.println("provider: " +
			// FTPAdapter.getInstance().getCurrentFTPPath() + "/" +
			// ((FTPFile)element).getName());
			// System.out.println(Tags.size());
			ArrayList<LGKLabel> TagKeys = FTPAdapter.getInstance().showAllKey();
			// System.out.println("this is Tags:" + Tags);
			for (int i = 0; i < Tags.size(); ++i)
				if (Tags.get(i).getKey()
						.equals(TagKeys.get(column_index - 4).getKey())) {
					return Tags.get(i).getValue();
				}
			return "";
		}
		return "";
	}

	public void addListener(ILabelProviderListener ilabelproviderlistener) {
	}

	public void dispose() {
	}

	public boolean isLabelProperty(Object obj, String s) {
		return false;
	}

	public void removeListener(ILabelProviderListener ilabelproviderlistener) {
	}

	public Image getColumnImage(Object element, int column_index) {
		if (column_index != 0) {
			return null;
		}
		if (((FTPFile) element).isDirectory()) {
			return EasyFileUtil.getImageRegistry().get("folder");
		} else {
			FTPFile file = (FTPFile) element;
			String fileType = FileUtil.getFileType(file);
			Image fileImage = EasyFileUtil.getImageRegistry().get(
					fileType.toLowerCase());
			if (fileImage == null) {
				return EasyFileUtil.getImageRegistry().get("file");
			} else {
				return fileImage;
			}
		}
	}
}
