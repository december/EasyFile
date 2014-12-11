/**
 * 进入文件夹操作
 */
package logic.action;

import java.io.File;

import org.apache.commons.net.ftp.FTPFile;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;

import util.EasyFileUtil;
import display.MainWindow;

/**
 * 进入文件架操作
 * 
 * @author Eternal_Answer
 *
 */
public class OpenAction extends Action implements ISelectionChangedListener,
		IDoubleClickListener

{
	MainWindow window;

	/**
	 * 构造函数
	 * 
	 * @param w
	 *            父窗口
	 */
	public OpenAction(MainWindow w) {
		window = w;
		setText("&Open@ALT+O");
		setToolTipText("Open the file");
		setImageDescriptor(ImageDescriptor.createFromURL(EasyFileUtil
				.newURL("file:icons/open.bmp")));
	}

	public void run() {
		IStructuredSelection selection = window.getTableSelection();
		if (selection.size() != 1) {
			return;
		}
		FTPFile selected_file = (FTPFile) selection.getFirstElement();
		if (selected_file.isDirectory()) {
			window.gotoPath(window.getAddressText() + "/"
					+ selected_file.getName());
			window.setStatus("��ǰFTP��" + window.getFTPText() + "   ����Ŀ¼��"
					+ window.getAddressText());
		}
	}

	public void selectionChanged(SelectionChangedEvent event) {
		setText("Run");
		setToolTipText("Run the associated program on a file");
		IStructuredSelection selection = window.getTableSelection();
		if (selection.size() != 1) {
			setEnabled(false);
			setToolTipText(getToolTipText()
					+ " (Only enabled when exactly one item is selected)");
			return;
		}
		FTPFile file = (FTPFile) selection.getFirstElement();
		if (file.isFile()) {
			setEnabled(true);
			setText("Run " + file.getName());
			setToolTipText("Run " + file.getName());
		} else if (file.isDirectory()) {
			setEnabled(true);
			setText("Open folder " + file.getName());
			setToolTipText("Open folder  " + file.getName());
		}
	}

	public void doubleClick(DoubleClickEvent event) {
		IStructuredSelection selection = window.getTableSelection();
		if (selection.size() != 1) {
			return;
		}
		FTPFile selected_file = (FTPFile) selection.getFirstElement();
		/*
		 * if( selected_file.isFile() ) { Program.launch(
		 * selected_file.getAbsolutePath() ); } else
		 */
		if (selected_file.isDirectory()) {
			window.gotoPath(window.getAddressText() + File.separator
					+ selected_file.getName());
			// FTPAdapter.getInstance().changeWorkingFTPDirectory(selected_file.getPath());
			// window.getTreeViewer().setSelection( selection );
			// window.getTreeViewer().setExpandedState( selected_file, true );
			// window.getTablebViewer().setInput( selected_file );
		}
	}

}
