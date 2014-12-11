/**
 * 筛选出中等大小的文件
 */
package logic.action;

import logic.filter.MiddleSizeFilter;

import org.eclipse.jface.action.Action;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;

import display.MainWindow;

/**
 * 筛选出中等大小的文件
 * 
 * @author luyf
 */
public class ChooseMidAction extends Action implements MouseListener {
	MainWindow window;

	/**
	 * 构造函数
	 * 
	 * @param MainWindow
	 *            父窗口
	 */
	public ChooseMidAction(MainWindow w) {
		window = w;
		setText("Middle Files(10KB to 1MB)");
		setToolTipText("Filter Middle-sized Files");
	}

	public void run() {
		// window.getTableViewer().resetFilters();
		window.getTableViewer().addFilter(new MiddleSizeFilter());
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