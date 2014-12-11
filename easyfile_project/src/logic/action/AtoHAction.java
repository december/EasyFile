/**
 * 筛选出以A-H开头的文件
 */
package logic.action;

import logic.filter.AtoHFilter;
import org.eclipse.jface.action.Action;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;

import display.MainWindow;

/**
 * 筛选出以A-H开头的文件
 * 
 * @author luyf
 */
public class AtoHAction extends Action implements MouseListener {
	MainWindow window;
	
	/**
	 * 构造函数
	 * 
	 * @param MainWindow
	 *            父窗口
	 */
	public AtoHAction(MainWindow w) {
		window = w;
		setText("A-H");
		setToolTipText("Filename Start with A-H");
	}

	public void run() {
		window.getTableViewer().addFilter(new AtoHFilter());
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
