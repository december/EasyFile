/**
 * 筛选出一个月内未修改的文件
 */
package logic.action;

import logic.filter.OldFileFilter;

import org.eclipse.jface.action.Action;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;

import display.MainWindow;

/**
 * 筛选出一个月内未修改的文件
 * 
 * @author luyf
 */
public class ChooseOldAction extends Action implements MouseListener {
	MainWindow window;

	/**
	 * 构造函数
	 * 
	 * @param MainWindow
	 *            父窗口
	 */
	public ChooseOldAction(MainWindow w) {
		window = w;
		setText("Earlier Before");
		setToolTipText("Files Modified before a month");
	}

	public void run() {
		// window.getTableViewer().resetFilters();
		window.getTableViewer().addFilter(new OldFileFilter());
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
