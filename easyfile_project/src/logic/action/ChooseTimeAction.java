/**
 * 按修改时间筛选文件
 */
package logic.action;

import logic.filter.TimeFilter;
import org.eclipse.jface.action.Action;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;

import display.MainWindow;

/**
 * 按修改时间筛选文件
 * 
 * @author luyf
 */
public class ChooseTimeAction extends Action implements MouseListener {
	MainWindow window;
	int time;

	/**
	 * 构造函数
	 * 
	 * @param MainWindow 父窗口
	 * @param t 时间要求
	 * @return 在近t天内修改过的文件
	 *            
	 */
	public ChooseTimeAction(MainWindow w, int t) {
		window = w;
		time = t;
		setText("Last " + t + " days");
		setToolTipText("Files Modified in " + "Last " + t + " days");
	}

	public void run() {
		// window.getTableViewer().resetFilters();
		window.getTableViewer().addFilter(new TimeFilter(time));
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