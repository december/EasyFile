/**
 * Manage the label of progress table
 */
package logic.provider;

import java.io.File;
import java.util.List;

import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.swt.graphics.Image;

import basic.*;

public class ProgressTableLabelProvider implements ITableLabelProvider {

	public String getColumnText(Object element, int column_index) {
		if (column_index == 0) {
			List<String> fNameList = ((FileStatus) element).localNameList;
			File tempFile = new File(fNameList.get(0).trim());
			String fileName = tempFile.getName();
			if (fNameList.size() > 1) {
				fileName = fileName + "等 " + fNameList.size() + " 个文件";
			}
			return fileName;
		}
		if (column_index == 1) {
			return ((int) (((FileStatus) element).progress * 1000)) / 10.0
					+ "%";
		}
		if (column_index == 2) {
			switch (((FileStatus) element).status) {
			case -2:
				return "↑ (running)";
			case -1:
				if (((FileStatus) element).progress > 0.9999)
					return "↑ (finished)";
				else
					return "↑ (stopped)";
			case 1:
				if (((FileStatus) element).progress > 0.9999)
					return "↓ (finished)";
				else
					return "↓ (stopped)";
			case 2:
				return "↓ (running)";
			default:
				return "unknown";
			}
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

	@Override
	public Image getColumnImage(Object arg0, int arg1) {
		// TODO Auto-generated method stub
		return null;
	}

}
