/**
 * 筛选出较小的文件
 */
package logic.action;

import logic.filter.SmallSizeFilter;

import org.eclipse.jface.action.Action;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;

import display.MainWindow;

/**
 * 筛选出较小的文件
 * 
 * @author luyf
 */
public class ChooseSmallAction extends Action implements MouseListener {
	MainWindow window;

	/**
	 * 构造函数
	 * 
	 * @param MainWindow
	 *            父窗口
	 */
	public ChooseSmallAction(MainWindow w) {
		window = w;
		setText("Small Files(10KB or Smaller)");
		setToolTipText("Filter Smaller Files");
	}

	public void run() {
		// window.getTableViewer().resetFilters();
		window.getTableViewer().addFilter(new SmallSizeFilter());
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
