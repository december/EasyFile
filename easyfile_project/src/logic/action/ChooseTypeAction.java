/**
 * 按文件类型筛选文件
 */
package logic.action;

import logic.filter.TypeFilter;
import org.eclipse.jface.action.Action;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;

import display.MainWindow;

/**
 * 按文件类型筛选文件
 * 
 * @author luyf
 */
public class ChooseTypeAction extends Action implements MouseListener {
	MainWindow window;
	String typeName;

	/**
	 * 构造函数
	 * 
	 * @param MainWindow 父窗口
	 * @param type 类型要求
	 * @return 相应类型的文件
	 *            
	 */
	public ChooseTypeAction(MainWindow w, String type) {
		window = w;
		typeName = type;
		setText(typeName);
		setToolTipText("Files whose types are " + typeName);
	}

	public void run() {
		// window.getTableViewer().resetFilters();
		window.getTableViewer().addFilter(new TypeFilter(typeName));
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
