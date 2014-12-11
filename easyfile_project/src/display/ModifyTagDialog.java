/**
 * 修改单个标签的对话框
 */
package display;

import org.eclipse.jface.dialogs.Dialog;
import adapter.*;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.viewers.*;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowData;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.*;

import swing2swt.layout.BorderLayout;
import logic.provider.*;
import basic.*;

/**
 * 修改单个标签的对话框
 * 
 * @author Eternal_Answer
 *
 */
public class ModifyTagDialog extends Dialog {
	LGKLabel thisTag;
	/**
	 * 父窗口
	 */
	public ManageTagDialog window;
	TableViewer valueTable;
	Table table;
	Text nameText;

	/**
	 * 构造函数
	 * 
	 * @param _window
	 *            父窗口
	 * @param Tag
	 *            待修改的标签
	 */
	public ModifyTagDialog(ManageTagDialog _window, LGKLabel Tag) {
		super(_window.getShell());
		window = _window;
		thisTag = Tag;
	}

	/**
	 * @Override
	 */
	protected void configureShell(Shell newShell) {
		super.configureShell(newShell);
		newShell.setText("Modify Tag");
		newShell.setSize(650, 450);
	}

	protected void createButtonsForButtonBar(Composite parent) {
		// TODO Auto-generated method stub
	}

	/**
	 * 对于标签列表的第三列加入EdittingSupport
	 * 
	 * @author Eternal_Answer
	 *
	 */
	private class MyEditingSupport extends EditingSupport {
		private CellEditor textEditor;

		public MyEditingSupport(TableViewer viewer) {
			super(viewer);
		}

		protected boolean canEdit(Object element) {
			return true;
		}

		protected CellEditor getCellEditor(Object element) {
			textEditor = new TextCellEditor(table);
			return textEditor;
		}

		protected Object getValue(Object element) {
			return ((Sign) element).value;
		}

		protected void setValue(Object element, Object value) {
			((Sign) element).value = (String) value;
			valueTable.setInput("2");
		}
	}

	/**
	 * 创建对话框
	 */
	protected Control createDialogArea(Composite parent) {
		final Composite container = new Composite(parent, SWT.NONE);
		container.setLayout(new BorderLayout(0, 0));

		final Composite mainCom = new Composite(container, SWT.NONE);
		mainCom.setLayoutData(BorderLayout.CENTER);
		// set FillLayout
		FillLayout fillLayout = new FillLayout(SWT.VERTICAL);
		fillLayout.marginHeight = 5;
		fillLayout.marginWidth = 5;
		fillLayout.spacing = 1;
		mainCom.setLayout(fillLayout);

		final Composite buttonCom = new Composite(container, SWT.NONE);
		buttonCom.setLayoutData(BorderLayout.SOUTH);
		buttonCom.setLayout(new GridLayout(2, false));
		// fill mainCom
		final Composite nameCom = new Composite(mainCom, SWT.NONE);
		nameCom.setLayout(new RowLayout());
		final Composite typeCom = new Composite(mainCom, SWT.NONE);
		typeCom.setLayout(new RowLayout());
		// table
		// fill nameCom
		final Label nameLabel = new Label(nameCom, SWT.NONE);
		nameLabel.setText("Tag Name: ");
		nameText = new Text(nameCom, SWT.NONE);
		nameText.setText(thisTag.getKey());
		nameText.setLayoutData(new RowData(100, 20));
		nameText.setEditable(false);
		// fill typeCom
		final Label typeLabel = new Label(typeCom, SWT.NONE);
		typeLabel.setText("Tag Type: ");
		final Button typeButton1 = new Button(typeCom, SWT.RADIO);
		typeButton1.setText("Arbitrary");
		if (thisTag.getType() == 0)
			typeButton1.setSelection(true);
		final Button typeButton2 = new Button(typeCom, SWT.RADIO);
		typeButton2.setText("Selection");
		if (thisTag.getType() == 1)
			typeButton2.setSelection(true);
		final Button addTagButton = new Button(typeCom, SWT.PUSH);
		addTagButton.setText("Add Value");
		if (thisTag.getType() == 0)
			addTagButton.setEnabled(false);
		else
			addTagButton.setEnabled(true);
		addTagButton.addMouseListener(new MouseAdapter() {
			public void mouseUp(MouseEvent event) {
				valueTable.setInput("1");
			}
		});
		final ValueTableContentProvider provider = new ValueTableContentProvider(
				thisTag);
		typeButton2.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void widgetSelected(SelectionEvent arg0) {
				if (!typeButton2.getSelection()) {
					provider.thisTag.setType(0);
					provider.thisTag.setValue("");
					addTagButton.setEnabled(false);
				} else {
					provider.thisTag.setType(1);
					provider.thisTag.setValue("");
					ArrayList<String> st = new ArrayList<String>();
					st.add("");
					provider.thisTag.setValueList(st);
					addTagButton.setEnabled(true);
				}
				valueTable.setInput("0");
			}
		});
		// add value table
		/*
		 * set value Table
		 */
		valueTable = new TableViewer(mainCom, SWT.BORDER | SWT.FULL_SELECTION
				| SWT.MULTI);
		valueTable.setContentProvider(provider);

		table = valueTable.getTable();
		table.setLayout(new BorderLayout(0, 0));
		table.setHeaderVisible(false);
		table.setLinesVisible(true);

		List<Integer> columnWidth = new ArrayList<Integer>();
		columnWidth.add(new Integer(200));
		columnWidth.add(new Integer(200));

		TableViewerColumn[] columns = new TableViewerColumn[2];
		for (int i = 0; i < columns.length; i++) {
			TableViewerColumn column = columns[i];
			column = new TableViewerColumn(valueTable, SWT.NONE);
			column.setLabelProvider(new ValueColumnLabelProvider(i));
			column.getColumn().setWidth(columnWidth.get(i));
			if (i == 1)
				column.setEditingSupport(new MyEditingSupport(valueTable));
		}
		valueTable.setInput(new String("0"));
		// fill buttonCom
		GridData fillData = new GridData();
		fillData.grabExcessVerticalSpace = true;
		fillData.grabExcessHorizontalSpace = true;
		fillData.verticalAlignment = GridData.FILL;
		fillData.horizontalAlignment = GridData.FILL;
		Label fillLabel = new Label(buttonCom, SWT.NONE);
		fillLabel.setLayoutData(fillData);
		Button enterButton = new Button(buttonCom, SWT.PUSH);
		enterButton.setText("OK");
		GridData buttonData = new GridData();
		buttonData.widthHint = 70;
		buttonData.heightHint = 30;
		enterButton.setLayoutData(buttonData);
		enterButton.addMouseListener(new MouseAdapter() {
			public void mouseUp(MouseEvent event) {
				//System.out.println("new value = " + provider.thisTag.getValue());
				FTPAdapter.getInstance().changeLabelByKey(provider.thisTag);
				window.refresh();
				close();
			}
		});
		return container;
	}
}
