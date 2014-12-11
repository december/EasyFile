package logic.action;

import logic.filter.AllowAllFilter;
/**
 * 列出所有文件
 * 
 * @author luyf
 */
import org.eclipse.jface.action.Action;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;

import display.MainWindow;

/**
 * 列出所有文件
 * 
 * @author luyf
 */
public class ListAllAction extends Action implements MouseListener {
	MainWindow window;

	/**
	 * 构造函数
	 * 
	 * @param MainWindow
	 *            父窗口
	 */
	public ListAllAction(MainWindow w) {
		window = w;
		setText("All Files");
		setToolTipText("List All Files");
	}

	public void run() {
		window.getTableViewer().resetFilters();
		window.getTableViewer().addFilter(new AllowAllFilter());
	}

	@Override
	public void mouseDoubleClick(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseDown(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseUp(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

}
