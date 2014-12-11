/**
 * 筛选出以其它字符开头的文件
 */
package logic.action;

import logic.filter.OtherNameFilter;

import org.eclipse.jface.action.Action;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;

import display.MainWindow;

/**
 * 筛选出以其它字符开头的文件
 * 
 * @author luyf
 */
public class OtherNameAction extends Action implements MouseListener {
	MainWindow window;

	/**
	 * 构造函数
	 * 
	 * @param MainWindow
	 *            父窗口
	 */
	public OtherNameAction(MainWindow w) {
		window = w;
		setText("Others");
		setToolTipText("Filename Start with other characters");
	}

	public void run() {
		// window.getTableViewer().resetFilters();
		window.getTableViewer().addFilter(new OtherNameFilter());
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
