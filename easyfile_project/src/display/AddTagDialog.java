/**
 * 对于上传的文件添加标签的框
 */
package display;

import org.eclipse.jface.dialogs.Dialog;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.viewers.*;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.*;

import swing2swt.layout.BorderLayout;
import logic.action.*;
import logic.provider.*;
import adapter.FTPAdapter;
import basic.*;

/**
 * 对于上传的文件添加标签的对话框
 * 
 * @author Eternal_Answer
 *
 */
public class AddTagDialog extends Dialog {
	/**
	 * 需要上传的文件路径
	 */
	public String[] uploadFiles;
	private Button[] selectedButton;
	int tagNum;

	StartAction startAction;
	int labelNum;
	Composite inputCom;
	/**
	 * 显示标签的table
	 */
	public TableViewer tagTable;
	Table table;
	/**
	 * 是否同步至网盘的Button
	 */
	public Button saveToWPButton;
	Composite fileSelection;
	/**
	 * 当前上传文件的标签记录
	 */
	public LGKLabel[][] tagList;
	
	UploadDialog window;

	/**
	 * 构造函数
	 * 
	 * @param _window
	 *            父窗口
	 * @param _uploadFiles
	 *            上传的文件列表
	 */
	public AddTagDialog(UploadDialog _window, String[] _uploadFiles) {
		super(_window.getShell());
		window = _window;
		uploadFiles = _uploadFiles;
		ArrayList<LGKLabel> tags = FTPAdapter.getInstance().showAllKey();
		tagList = new LGKLabel[uploadFiles.length][tags.size()];
		tagNum = tags.size();
		for (int i = 0; i < uploadFiles.length; ++i)
			for (int j = 0; j < tags.size(); ++j)
				tagList[i][j] = new LGKLabel(tags.get(j).getKey(), tags.get(j)
						.getType(), tags.get(j).getValue());
	}

	/**
	 * @Override
	 */
	protected void configureShell(Shell newShell) {
		super.configureShell(newShell);
		newShell.setText("Set Tags");
		newShell.setSize(600, 500);
	}

	/**
	 * 删去原有的按键
	 */
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
		private CellEditor dropDownEditor;

		public MyEditingSupport(TableViewer viewer) {
			super(viewer);
		}

		protected boolean canEdit(Object element) {
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
	 * 构造Dialog区域
	 */
	protected Control createDialogArea(Composite parent) {
		// TODO Auto-generated method stub
		final Composite container = new Composite(parent, SWT.NONE);
		container.setLayout(new BorderLayout(0, 10, 600, 450));
		// title
		final Composite titleCom = new Composite(container, SWT.NONE);
		titleCom.setLayout(new GridLayout(3, false));
		titleCom.setLayoutData(BorderLayout.NORTH);
		Label title = new Label(titleCom, SWT.TITLE);
		title.setText("设置标签");
		GridData fillData1 = new GridData();
		fillData1.grabExcessVerticalSpace = true;
		fillData1.grabExcessHorizontalSpace = true;
		fillData1.verticalAlignment = GridData.FILL;
		fillData1.horizontalAlignment = GridData.FILL;
		Label fillLabel1 = new Label(titleCom, SWT.NONE);
		fillLabel1.setLayoutData(fillData1);
		final Button selectButton = new Button(titleCom, SWT.PUSH);
		selectButton.setText("设置");
		selectButton.addMouseListener(new MouseAdapter() {
			public void mouseDown(MouseEvent e) {
				for (int i = 0; i < uploadFiles.length; ++i)
					if (selectedButton[i].getSelection())
						for (int j = 0; j < tagNum; ++j)
							tagList[i][j].setValue(((LGKLabel) tagTable
									.getTable().getItem(j).getData())
									.getValue());
				for (int i = 0; i < selectedButton.length; ++i)
					selectedButton[i].setSelection(false);
			}
		});
		// set mainCom
		SashForm mainCom = new SashForm(container, SWT.HORIZONTAL | SWT.NULL);
		mainCom.setLayoutData(BorderLayout.CENTER);
		// buttonCom
		final Composite buttonCom = new Composite(container, SWT.NONE);
		buttonCom.setLayoutData(BorderLayout.SOUTH);
		GridLayout gl = new GridLayout(4, false);
		gl.marginRight = 0;
		buttonCom.setLayout(gl);
		// add file choose
		fileSelection = new Composite(mainCom, SWT.BORDER);
		fileSelection.setLayout(new GridLayout(1, false));
		fileSelection.setBackground(new Color(null, 255, 255, 255));
		selectedButton = new Button[uploadFiles.length];
		for (int i = 0; i < uploadFiles.length; ++i) {
			selectedButton[i] = new Button(fileSelection, SWT.CHECK);
			selectedButton[i].setText((new File(uploadFiles[i])).getName());
			selectedButton[i].setBackground(new Color(null, 255, 255, 255));
		}
		final Button selectAll = new Button(fileSelection, SWT.CHECK);
		selectAll.setBackground(new Color(null, 255, 255, 255));
		selectAll.setText("全选");
		selectAll.addMouseListener(new MouseAdapter() {
			public void mouseDown(MouseEvent e) {
				if (!selectAll.getSelection()) {
					for (int i = 0; i < uploadFiles.length; ++i)
						selectedButton[i].setSelection(true);
				} else {
					for (int i = 0; i < uploadFiles.length; ++i)
						selectedButton[i].setSelection(false);
				}
			}
		});
		// add tagTable to mainCom
		tagTable = new TableViewer(mainCom, SWT.BORDER | SWT.FULL_SELECTION
				| SWT.MULTI);
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
		columnWidth.add(new Integer(100));
		columnWidth.add(new Integer(100));
		columnWidth.add(new Integer(100));
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
		mainCom.setWeights(new int[] { 2, 3 });
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
		enterButton.addMouseListener(new MouseAdapter() {
			public void mouseDown(MouseEvent e) {
				int saveToWP;
				if (window.saveToWPButton.getSelection())
					saveToWP = 1;
				else
					saveToWP = 0;
				for (int i = 0; i < tagList.length; ++i) {
					ArrayList<LGKLabel> labels = new ArrayList<LGKLabel>();
					System.out.println(uploadFiles[i]);
					for (int j = 0; j < tagList[i].length; ++j) {
						labels.add(new LGKLabel(tagList[i][j].getKey(),
								tagList[i][j].getType(), tagList[i][j]
										.getValue()));
						System.out.println(labels.get(j).getValue());
					}
					FTPAdapter.getInstance().addTag(
							window.window.getAddressText() + '/'
									+ (new File(uploadFiles[i])).getName(),
							labels);
				}
				// window.window.newUpload(window.getFileText(),
				// window.window.getAddressText() + '/' + localFile.getName(),
				// FTPAdapter.getInstance().getCurMode(),
				// window.window.getSaveToWP());
				ArrayList<String> localFiles = new ArrayList<String>();
				ArrayList<String> ftpFiles = new ArrayList<String>();
				for (int i = 0; i < uploadFiles.length; ++i) {
					localFiles.add(uploadFiles[i]);
					ftpFiles.add(window.window.getAddressText() + '/'
							+ (new File(uploadFiles[i])).getName());
				}
				FTPAdapter.getInstance().newUploadProcess(ftpFiles, localFiles,
						saveToWP);
				window.window.newUpload(localFiles, ftpFiles, saveToWP,
						FTPAdapter.getInstance().getCurMode());
				window.close();
				close();
			}
		});
		GridData buttonData = new GridData();
		buttonData.widthHint = 70;
		buttonData.heightHint = 30;
		enterButton.setLayoutData(buttonData);

		Button cancelButton = new Button(buttonCom, SWT.PUSH);
		cancelButton.setText("Cancel");
		cancelButton.addMouseListener(new MouseAdapter() {
			public void mouseDown(MouseEvent e) {
				close();
			}
		});
		cancelButton.setLayoutData(buttonData);

		return container;
	}
}
