/**
 * 管理标签的界面
 */
package display;

import org.eclipse.jface.dialogs.Dialog;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.action.*;
import org.eclipse.jface.viewers.*;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.*;

import swing2swt.layout.BorderLayout;
import logic.action.*;
import logic.provider.*;

/**
 * 管理标签的界面
 * 
 * @author Eternal_Answer
 *
 */
public class ManageTagDialog extends Dialog {
	MainWindow window;
	ArrayList<Composite> tagList;
	AddTagKeyAction addTagKeyAction;
	DeleteTagKeyAction deleteTagKeyAction;
	ModifyTagAction modifyTagAction;

	GridData fillData;
	Composite tagCom;
	TableViewer tagTable;
	Table table;

	/**
	 * 构造函数
	 * 
	 * @param _window
	 *            父窗口
	 */
	public ManageTagDialog(MainWindow _window) {
		super(_window.getShell());
		window = _window;
		addTagKeyAction = new AddTagKeyAction(this);
		deleteTagKeyAction = new DeleteTagKeyAction(this);
		modifyTagAction = new ModifyTagAction(this);
	}

	/**
	 * @Override
	 */
	protected void configureShell(Shell newShell) {
		super.configureShell(newShell);
		newShell.setText("Manage Tags");
		newShell.setSize(650, 450);
	}

	/**
	 * 创建右键菜单
	 * 
	 * @return
	 */
	protected MenuManager createFilePopMenuManager() {
		MenuManager menuManager = new MenuManager();
		menuManager.add(deleteTagKeyAction);
		menuManager.add(modifyTagAction);
		return menuManager;
	}

	protected void createButtonsForButtonBar(Composite parent) {
	}

	/**
	 * 构造对话框
	 */
	protected Control createDialogArea(Composite parent) {
		Composite container = new Composite(parent, SWT.NONE);
		container.setLayout(new BorderLayout(0, 0));
		// Head composite
		Composite headCom = new Composite(container, SWT.NONE);
		headCom.setLayout(new GridLayout(3, false));
		headCom.setLayoutData(BorderLayout.NORTH);
		// Bottom composite
		Composite bottomCom = new Composite(container, SWT.NONE);
		bottomCom.setLayout(new GridLayout(2, false));
		bottomCom.setLayoutData(BorderLayout.SOUTH);
		// set fill data
		fillData = new GridData();
		fillData.grabExcessVerticalSpace = true;
		fillData.grabExcessHorizontalSpace = true;
		fillData.verticalAlignment = GridData.FILL;
		fillData.horizontalAlignment = GridData.FILL;
		// Fill head composite
		Label headLabel = new Label(headCom, SWT.NONE);
		headLabel.setText("Tag list:");
		Label fillLabel = new Label(headCom, SWT.NONE);
		fillLabel.setLayoutData(fillData);
		Button addTagKeyButton = new Button(headCom, SWT.PUSH);
		addTagKeyButton.setText("Add Tag");
		addTagKeyButton.addMouseListener(new MouseAdapter() {
			public void mouseDown(MouseEvent event) {
				addTagKeyAction.run();
			}
		});
		// Fill bottom composite
		Label fillLabel1 = new Label(bottomCom, SWT.NONE);
		fillLabel1.setLayoutData(fillData);
		Button enterButton = new Button(bottomCom, SWT.NONE);
		enterButton.setText("OK");
		GridData buttonData = new GridData();
		buttonData.widthHint = 70;
		buttonData.heightHint = 30;
		enterButton.setLayoutData(buttonData);
		enterButton.addMouseListener(new MouseAdapter() {
			public void mouseUp(MouseEvent event) {
				closeDialog();
			}
		});
		// Fill tag composite
		/*
		 * 设置右边部分的文件展示列表
		 */
		tagTable = new TableViewer(container, SWT.BORDER | SWT.FULL_SELECTION
				| SWT.MULTI);
		/*
		 * 显示文件
		 */
		tagTable.setContentProvider(new TagTableContentProvider());

		table = tagTable.getTable();
		table.setLayout(new BorderLayout(0, 0));
		table.setLayoutData(BorderLayout.CENTER);
		table.setHeaderVisible(true);

		final List<String> lables = new ArrayList<String>();
		lables.add("Tag Name");
		lables.add("Description");
		lables.add("Default Value");
		String[] columnNames = new String[lables.size()];
		for (int i = 0; i < lables.size(); ++i)
			columnNames[i] = lables.get(i);
		List<Integer> columnWidth = new ArrayList<Integer>();
		columnWidth.add(new Integer(200));
		columnWidth.add(new Integer(200));
		columnWidth.add(new Integer(200));
		TableViewerColumn[] columns = new TableViewerColumn[lables.size()];
		for (int i = 0; i < columns.length; i++) {
			TableViewerColumn column = columns[i];
			String columnName = lables.get(i);
			column = new TableViewerColumn(tagTable,
					"Tag Name".equalsIgnoreCase(columnName) ? SWT.LEFT
							: SWT.RIGHT);
			column.setLabelProvider(new TagColumnLabelProvider(i));
			column.getColumn().setWidth(columnWidth.get(i));
			column.getColumn().setText(lables.get(i));
		}
		tagTable.getTable().addListener(SWT.MenuDetect, new Listener() {
			public void handleEvent(Event event) {
				Table t = (Table) event.widget;

				IStructuredSelection selection = getTableSelection();
				MenuManager popMenuManager;
				System.out.println("selection number = " + selection.size());
				if (selection.size() == 1) {
					popMenuManager = createFilePopMenuManager();
					t.setMenu(popMenuManager.createContextMenu(t));
				}
			}
		});

		tagTable.setInput(new String(""));

		return container;
	}

	/**
	 * 得到选中的标签
	 * 
	 * @return
	 */
	public IStructuredSelection getTableSelection() {
		return (IStructuredSelection) (tagTable.getSelection());
	}

	/**
	 * 关闭对话框
	 */
	public void closeDialog() {
		window.refresh();
		close();
	}

	/**
	 * 刷新标签表格
	 */
	public void refresh() {
		tagTable.setInput(new String("1"));
	}
}
