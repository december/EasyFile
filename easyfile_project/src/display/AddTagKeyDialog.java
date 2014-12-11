/**
 * 添加标签种类的窗口
 */
package display;

import org.eclipse.jface.dialogs.Dialog;

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

import adapter.*;

public class AddTagKeyDialog extends Dialog {
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
	 */
	public AddTagKeyDialog(ManageTagDialog _window) {
		super(_window.getShell());
		window = _window;
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
	 * 对于第三列加入EditingSupport
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
	 * 构造Dialog区域
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
		nameText.setLayoutData(new RowData(100, 20));
		// fill typeCom
		final Label typeLabel = new Label(typeCom, SWT.NONE);
		typeLabel.setText("Tag Type: ");
		final Button typeButton1 = new Button(typeCom, SWT.RADIO);
		typeButton1.setText("Arbitrary");
		final Button typeButton2 = new Button(typeCom, SWT.RADIO);
		typeButton2.setText("Selection");
		final Button addTagButton = new Button(typeCom, SWT.PUSH);
		addTagButton.setText("Add Value");
		addTagButton.setEnabled(false);

		addTagButton.addMouseListener(new MouseAdapter() {
			public void mouseUp(MouseEvent event) {
				valueTable.setInput("1");
			}
		});
		final ValueTableContentProvider provider = new ValueTableContentProvider(
				new LGKLabel("", 0, ""));
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
				provider.thisTag.setKey(nameText.getText());
				FTPAdapter.getInstance().addKey(provider.thisTag);
				window.refresh();
				close();
			}
		});
		return container;
	}
}
