/**
 * 修改单个文件的标签对话框
 */
package display;

import org.eclipse.jface.dialogs.Dialog;
import adapter.*;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.viewers.*;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.*;

import swing2swt.layout.BorderLayout;
import logic.action.*;
import logic.provider.*;
import basic.*;

/**
 * 修改单个文件的标签对话框
 * 
 * @author Eternal_Answer
 *
 */
public class ModifyFileTagDialog extends Dialog {
	String filePath;
	Label wrongLabel;
	UploadFileAction uploadFileAction;
	UploadDirectoryAction uploadDirectoryAction;
	CloseAction closeAction;
	StartAction startAction;
	int labelNum;
	Composite inputCom;
	/**
	 * 显示标签的TableViewer
	 */
	public TableViewer tagTable;
	Table table;
	/**
	 * 父窗口
	 */
	public MainWindow window;

	/**
	 * 构造函数
	 * 
	 * @param _window
	 *            父窗口
	 * @param _filePath
	 *            文件路径
	 */
	public ModifyFileTagDialog(MainWindow _window, String _filePath) {
		super(_window.getShell());
		window = _window;
		filePath = _filePath;
	}

	/**
	 * @Override
	 */
	protected void configureShell(Shell newShell) {
		super.configureShell(newShell);
		newShell.setText("Modify File Tag");
		newShell.setSize(650, 450);
	}

	protected void createButtonsForButtonBar(Composite parent) {
		// TODO Auto-generated method stub
	}

	/**
	 * 对标签列表的第三列加入EditingSupport
	 * 
	 * @author Eternal_Answer
	 *
	 */
	private class MyEditingSupport extends EditingSupport {
		private CellEditor textEditor;
		private CellEditor dropDownEditor;

		public MyEditingSupport(TableViewer viewer) {
			super(viewer);
			System.out.println("MyEditingSupport");
		}

		protected boolean canEdit(Object element) {
			System.out.println("true");
			return true;
		}

		protected CellEditor getCellEditor(Object element) {
			System.out.println("getCellEditor");
			if (((LGKLabel) element).getType() == 0) {
				textEditor = new TextCellEditor(table);
				return textEditor;
			} else {
				ArrayList<String> res = ((LGKLabel) element).getValueList();
				String[] elements = new String[res.size()];
				for (int i = 0; i < res.size(); ++i)
					elements[i] = res.get(i);
				dropDownEditor = new ComboBoxCellEditor(table, elements);
				return dropDownEditor;
			}
		}

		protected Object getValue(Object element) {
			if (((LGKLabel) element).getType() == 0)
				return ((LGKLabel) element).getValue();
			else
				return new Integer(0);
		}

		protected void setValue(Object element, Object value) {
			if (((LGKLabel) element).getType() == 0) {
				((LGKLabel) element).setValue((String) value);
			} else {
				((LGKLabel) element).setValue((((LGKLabel) element)
						.getValueList()).get(((Integer) value).intValue()));
			}
			tagTable.refresh();
		}

	}

	/**
	 * 构造对话框
	 */
	protected Control createDialogArea(Composite parent) {
		final Composite container = new Composite(parent, SWT.NONE);
		container.setLayout(new BorderLayout(0, 0));
		// 锟斤拷锟斤拷锟斤拷锟斤拷虿糠锟�
		inputCom = new Composite(container, SWT.NONE);
		inputCom.setLayout(new GridLayout(1, true));
		inputCom.setLayoutData(BorderLayout.NORTH);
		// 锟斤拷锟斤拷锟斤拷锟津部凤拷
		Composite fileCom = new Composite(inputCom, SWT.NONE);
		fileCom.setLayout(new RowLayout());
		// 锟斤拷锟斤拷募锟斤拷锟斤拷锟斤拷
		Label fileLabel = new Label(fileCom, SWT.NONE);
		fileLabel.setText("File Name: " + (new File(filePath)).getName());
		// 锟斤拷锟矫帮拷钮锟斤拷锟斤拷
		final Composite buttonCom = new Composite(container, SWT.NONE);
		GridLayout gl = new GridLayout(3, false);
		gl.marginRight = 0;
		buttonCom.setLayout(gl);
		buttonCom.setLayoutData(BorderLayout.SOUTH);
		// Fill tag value composite
		/*
		 * set tag Table
		 */
		tagTable = new TableViewer(container, SWT.BORDER | SWT.FULL_SELECTION
				| SWT.MULTI);
		/*
		 * ��剧ず���浠�
		 */
		tagTable.setContentProvider(new TagTableContentProvider());

		table = tagTable.getTable();
		table.setLayout(new BorderLayout(0, 0));
		table.setLayoutData(BorderLayout.CENTER);
		table.setHeaderVisible(true);
		table.setLinesVisible(true);

		final List<String> lables = new ArrayList<String>();
		lables.add("Tag Name");
		lables.add("Description");
		lables.add("Value");
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
			if (i == 2)
				column.setEditingSupport(new MyEditingSupport(tagTable));
		}
		tagTable.setInput(new String(""));
		// set fill data
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
				ArrayList<LGKLabel> labels = new ArrayList<LGKLabel>();
				for (int i = 0; i < tagTable.getTable().getItemCount(); ++i) {
					System.out.println("current tag = " + ((LGKLabel) (tagTable.getTable().getItem(i)
							.getData())).getValue());
					labels.add((LGKLabel) (tagTable.getTable().getItem(i)
							.getData()));
				}
				FTPAdapter.getInstance().changeLabelOfFile(filePath, labels);
				window.refresh();
				close();
			}
		});
		Button cancelButton = new Button(buttonCom, SWT.PUSH);
		cancelButton.setText("Cancel");
		cancelButton.addMouseListener(new MouseAdapter() {
			public void mouseUp(MouseEvent event) {
				window.refresh();
				close();
			}
		});
		cancelButton.setLayoutData(buttonData);

		return container;
	}
}
