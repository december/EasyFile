/**
 * 筛选出较大的的文件
 */
package logic.action;

import logic.filter.BigSizeFilter;

import org.eclipse.jface.action.Action;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;

import display.MainWindow;

/**
 * 筛选出较大的的文件
 * 
 * @author luyf
 */
public class ChooseBigAction extends Action implements MouseListener {
	MainWindow window;

	/**
	 * 构造函数
	 * 
	 * @param MainWindow
	 *            父窗口
	 */
	public ChooseBigAction(MainWindow w) {
		window = w;
		setText("Big Files(1MB or Bigger)");
		setToolTipText("Filter Bigger Files");
	}

	public void run() {
		// window.getTableViewer().resetFilters();
		window.getTableViewer().addFilter(new BigSizeFilter());
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
