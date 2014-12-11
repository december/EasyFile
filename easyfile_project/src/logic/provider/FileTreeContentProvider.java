package logic.provider;

import adapter.*;
import java.util.List;

import org.apache.commons.net.ftp.FTPFile;
import org.eclipse.jface.viewers.*;

public class FileTreeContentProvider implements ITreeContentProvider {
	public Object[] getChildren(Object element) {
		// System.out.println("get Children  " + (String)element);
		List<FTPFile> files = FTPAdapter.getInstance().getFileList(
				((String) element));
		Object[] kids = new Object[files.size()];
		for (int i = 0; i < files.size(); ++i) {
			if (((String) element).equals("/"))
				kids[i] = (String) element + files.get(i).getName();
			else
				kids[i] = (String) element + "/" + files.get(i).getName();
		}
		return kids == null ? new Object[0] : kids;
	}

	public Object[] getElements(Object element) {
		// return getChildren(element);
		Object[] root = new Object[1];
		root[0] = new String("/");
		System.out.println("getElement!!!!!!!!!!!!" + (String) root[0]);
		return root;
	}

	public boolean hasChildren(Object element) {
		return getChildren(element).length > 0;
	}

	public Object getParent(Object element) {
		String parent = null;
		try {
			parent = FTPAdapter.getInstance().getParentDirectory(
					((String) element));
		} catch (ClassCastException cce) {
			cce.printStackTrace();
		}
		return parent;
		// return parent == null ? element : parent;
	}

	public void dispose() {
	}

	public void inputChanged(Viewer viewer, Object old_input, Object new_input) {
	}
}
