package display;

/*
 * 导入java标准库
 */
import java.io.*;
import adapter.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.List;
import java.util.Set;
import progress.*;
/*
 * 导入FTP包
 */
import org.apache.commons.net.ftp.FTPFile;
/*
 * 导入swt,jface库
 */
import org.eclipse.jface.action.*;
import org.eclipse.jface.viewers.*;
import org.eclipse.jface.window.*;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.events.FocusAdapter;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.*;
/**
 * 导入自己的包
 */
import swing2swt.layout.BorderLayout;
import util.FileUtil;
import common.EasyFileConstant;
import logic.action.*;

import logic.provider.*;
import logic.sorter.*;
import basic.*;
import display.ConnectDialog;

/**
 * MainWindow为用户主窗口的外框架 使用SWT/jface组件
 * 
 * @author Eternal_Answer
 *
 */
public class MainWindow extends ApplicationWindow {
	private int limit;// 权限
	private boolean sortStatus = true;
	/**
	 * 当前浏览模式
	 */
	public int mode;
	/**
	 * 是否默认保存至网盘
	 */
	public int saveToWP;
	private int modeChange;
	private ArrayList<String> ftpHistory;
	/*
	 * 定义容器及部件
	 */
	private Text pathText;
	private Combo ftpText;
	private Table table;
	private Table ptable;
	private Table ptableDone;
	private TableViewer fileTable;
	private TableViewer progressTable;
	private TableViewer progressTableDone;
	// private TreeViewer tv;
	// private Tree tree;
	public ToolItem WPItem, FTPItem;
	/*
	 * 定义Action
	 */
	private BackAction backAction;
	// private ForwardAction forwardAction;
	private OpenAction openAction;
	private UploadAction uploadAction;
	// private UploadFileAction uploadFileAction;
	// private UploadDirectoryAction uploadDirectoryAction;
	private DownloadAction downloadAction;
	private ConnectAction connectAction;
	private RefreshAction refreshAction;
	private DeleteAction deleteAction;
	private DeleteProgressAction deleteProgressAction;
	private RenameAction renameAction;
	private ProgressAction progressAction;
	private ManageTagAction manageTagAction;
	private ModifyFileTagAction modifyFileTagAction;
	private NewFileAction newFileAction;
	private ProgressManager pm;

	private ChooseBigAction chooseBigAction;
	private ChooseMidAction chooseMidAction;
	private ChooseSmallAction chooseSmallAction;
	private ListAllAction listAllAction;
	private AtoHAction atohAction;
	private ItoPAction itopAction;
	private QtoZAction qtozAction;
	private OtherNameAction otherNameAction;

	/**
	 * MainWindow的构造函数
	 */
	public MainWindow() {
		super(null);

		getFTPHistory();
		mode = FTPAdapter.FTPMODE;
		saveToWP = 0;
		modeChange = 0;
		openAction = new OpenAction(this);
		backAction = new BackAction(this);
		// forwardAction = new ForwardAction(this);
		uploadAction = new UploadAction(this);
		downloadAction = new DownloadAction(this);
		connectAction = new ConnectAction(this);
		refreshAction = new RefreshAction(this);
		renameAction = new RenameAction(this);
		deleteAction = new DeleteAction(this);
		manageTagAction = new ManageTagAction(this);
		modifyFileTagAction = new ModifyFileTagAction(this);
		pm = new ProgressManager(this);
		progressAction = new ProgressAction(this, pm);
		newFileAction = new NewFileAction(this);

		chooseBigAction = new ChooseBigAction(this);
		chooseMidAction = new ChooseMidAction(this);
		chooseSmallAction = new ChooseSmallAction(this);
		listAllAction = new ListAllAction(this);
		atohAction = new AtoHAction(this);
		itopAction = new ItoPAction(this);
		qtozAction = new QtoZAction(this);
		otherNameAction = new OtherNameAction(this);

		addMenuBar();
		addStatusLine();
		addToolBar(SWT.NONE);
	}

	/**
	 * 获取FTP的历史地址
	 */
	public void getFTPHistory() {
		ftpHistory = new ArrayList<String>();
		File f = new File("./history/ftpHistory.txt");
		if (!f.exists()) {
			System.out.println("File not Found");
			return;
		}
		try {
			BufferedReader br = new BufferedReader(new FileReader(
					"./history/ftpHistory.txt"));
			String data = br.readLine();
			while (data != null) {
				ftpHistory.add(data);
				data = br.readLine();
			}
			br.close();
		} catch (FileNotFoundException e) {
			System.out.println(e);
		} catch (IOException e) {
			System.out.println(e);
		}
	}

	/**
	 * 设置界面的基本信息
	 */
	protected void configureShell(Shell newShell) {
		super.configureShell(newShell);
		newShell.setText(EasyFileConstant.TITLE + " "
				+ EasyFileConstant.ABOUT_VERSION);
		newShell.setSize(1000, 600);
		// newShell.setBackground(new Color(null, 255, 255, 255));
		// newShell.setImage(EasyFileUtil.getImageRegistry().get("title"));
	}

	/**
	 * 创建工具栏
	 */
	protected ToolBarManager createToolBarManager(int style) {
		ToolBarManager tool_bar_manager = new ToolBarManager();
		// tool_bar_manager.add(createSortMenuManager());
		return tool_bar_manager;
	}

	/**
	 * 创建非单个文件选择时的悬浮菜单
	 * 
	 * @return
	 */
	protected MenuManager createNormalPopMenuManager() {
		MenuManager menuManager = new MenuManager();
		menuManager.add(connectAction);
		if (mode == FTPAdapter.FTPMODE && limit > 0)
			menuManager.add(uploadAction);
		if (mode == FTPAdapter.FTPMODE && limit > 1) {
			menuManager.add(newFileAction);
			menuManager.add(deleteAction);
			menuManager.add(manageTagAction);
		}
		return menuManager;
	}

	/**
	 * 标签右键菜单管理器
	 * 
	 * @param pos
	 *            所点击标签的序号
	 * @return menuManager 对应标签的右键菜单
	 */
	protected MenuManager headerMenuManager(int pos) {
		MenuManager menuManager = new MenuManager();
		switch (pos) {
		case 1:
			menuManager.add(atohAction);
			menuManager.add(itopAction);
			menuManager.add(qtozAction);
			menuManager.add(otherNameAction);
			menuManager.add(new Separator());
			menuManager.add(listAllAction);
			break;
		case 2:
			menuManager.add(chooseBigAction);
			menuManager.add(chooseMidAction);
			menuManager.add(chooseSmallAction);
			menuManager.add(new Separator());
			menuManager.add(new ChooseFolderAction(this));
			menuManager.add(new Separator());
			menuManager.add(listAllAction);
			break;
		case 3:
			Set<String> typeList = new HashSet<String>();
			// Table tb = fileTable.getTable();
			int total = table.getItemCount();
			for (int i = 0; i < total; i++) {
				FTPFile tempFile = (FTPFile) table.getItem(i).getData();
				typeList.add(FileUtil.getFileType(tempFile));
			}
			for (Iterator<String> it = typeList.iterator(); it.hasNext();)
				menuManager.add(new ChooseTypeAction(this, it.next()));
			menuManager.add(new Separator());
			menuManager.add(listAllAction);
			break;
		case 4:

			menuManager.add(new ChooseTimeAction(this, 3));
			menuManager.add(new ChooseTimeAction(this, 7));
			menuManager.add(new ChooseTimeAction(this, 30));
			menuManager.add(new ChooseOldAction(this));
			menuManager.add(new Separator());
			menuManager.add(listAllAction);
			break;
		default:
			System.out.println("Header Menu Error!");
			break;
		}
		return menuManager;
	}

	/**
	 * 创建单个文件选择时的菜单
	 * 
	 * @return
	 */
	protected MenuManager createFilePopMenuManager() {
		limit = 2;
		MenuManager menuManager = new MenuManager();
		menuManager.add(openAction);
		menuManager.add(new Separator());
		menuManager.add(connectAction);
		menuManager.add(new Separator());
		menuManager.add(downloadAction);
		if (limit > 0) {
			if (mode == FTPAdapter.FTPMODE)
				menuManager.add(uploadAction);
		}
		if (limit > 1) // can modify
		{
			if (mode == FTPAdapter.FTPMODE) {
				menuManager.add(newFileAction);
				menuManager.add(renameAction);
				menuManager.add(deleteAction);
				menuManager.add(manageTagAction);
				menuManager.add(modifyFileTagAction);
			}
		}
		return menuManager;
	}

	protected MenuManager createProgressPopMenuManager(int index, int done) {
		limit = 2;
		MenuManager menuManager = new MenuManager();

		deleteProgressAction = new DeleteProgressAction(this, index, done, pm);
		menuManager.add(deleteProgressAction);

		return menuManager;
	}

	/**
	 * 创建右键菜单接口
	 * 
	 * @return 菜单对象
	 */
	protected MenuManager createMenuManager() {
		MenuManager barMenu = new MenuManager("");
		MenuManager fileMenu = new MenuManager("&File");
		barMenu.add(fileMenu);
		if (mode == FTPAdapter.FTPMODE)
			fileMenu.add(uploadAction);
		return barMenu;
	}

	/**
	 * 重构文件显示列表
	 */
	protected void makeFileTable() {
		fileTable.setInput("///////clear");
		final List<String> lables = EasyFileConstant.getSortTypeList();
		ArrayList<LGKLabel> TagKeys = FTPAdapter.getInstance().showAllKey();
		for (int i = 0; i < TagKeys.size(); ++i) {
			lables.add(TagKeys.get(i).getKey());
		}
		List<Integer> columnWidth = new ArrayList<Integer>();
		columnWidth.add(new Integer(150));
		columnWidth.add(new Integer(70));
		columnWidth.add(new Integer(70));
		columnWidth.add(new Integer(150));
		for (int i = 0; i < TagKeys.size(); ++i)
			columnWidth.add(new Integer(70));
		TableColumn[] columns = table.getColumns();
		boolean[] visit = new boolean[columnWidth.size()];
		for (int i = 0; i < columnWidth.size(); ++i)
			visit[i] = false;
		for (int i = 0; i < columns.length; i++) {
			TableColumn column = columns[i];
			column.dispose();
		}
		for (int i = 0; i < columnWidth.size(); ++i) {
			TableColumn column;
			String columnName = lables.get(i);
			column = new TableColumn(table,
					"name".equalsIgnoreCase(columnName) ? SWT.LEFT : SWT.RIGHT);
			// column = new TableColumn(table, SWT.LEFT);
			column.setWidth(columnWidth.get(i));
			column.setText(lables.get(i));
			// lable排序

			final int index = i;
			column.addSelectionListener(new SelectionAdapter() {
				public void widgetSelected(SelectionEvent e) {
					System.out.println("Ranking...");
					Map<String, String> sortTypeMap = EasyFileConstant
							.getSortTypeMap();
					String sortTypeKey = lables.get(index);
					System.out.println(sortTypeKey);
					String sortTypeValue = sortTypeMap.get(sortTypeKey);
					System.out.println(sortTypeValue);
					doSort(sortTypeValue);
				}
			});
		}
		System.out.println("column size = " + table.getColumns().length);
	}

	/**
	 * 进行文件排序
	 */
	public void doSort(String sortTypeValue) {
		sortStatus = !sortStatus;

		Map<String, Integer> sortMap = EasyFileConstant.getSortMap();

		int column = sortMap.get(sortTypeValue);
		column = sortStatus ? -column : column;

		FileSorter fileSorter = (FileSorter) fileTable.getSorter();
		System.out.println("column = " + column);
		fileSorter.doSort(column);
		// System.out.println("column = " + column);
		fileTable.refresh();

	}

	/**
	 * 初始化ftp历史访问的Combo
	 */
	private void initFtpText() {
		for (int i = ftpHistory.size() - 1; i >= 0; --i) {
			ftpText.add(ftpHistory.get(i));
		}
		ftpText.select(0);
	}

	/**
	 * 构造主界面
	 */
	protected Control createContents(Composite parent) {
		Composite mainCom = new Composite(parent, SWT.NONE);
		mainCom.setLayout(new BorderLayout(0, 0));
		/*
		 * set Tool bar
		 */
		final ToolBar toolBar = new ToolBar(mainCom, SWT.FLAT | SWT.WRAP
				| SWT.RIGHT);
		toolBar.setLayoutData(BorderLayout.NORTH);
		toolBar.setBackground(new Color(null, 255, 255, 255));
		WPItem = new ToolItem(toolBar, SWT.RADIO);
		WPItem.setText("网盘模式");
		Image WPicon = new Image(mainCom.getDisplay(),
				"icons/toolbar/WPMODE.ico");
		WPItem.setImage(WPicon);
		FTPItem = new ToolItem(toolBar, SWT.RADIO);
		FTPItem.setText("FTP模式");
		Image FTPicon = new Image(mainCom.getDisplay(),
				"icons/toolbar/FTPMODE.ico");
		FTPItem.setImage(FTPicon);
		FTPItem.setSelection(true);
		FTPItem.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				if (FTPItem.getSelection()) {
					gotoMode(FTPAdapter.FTPMODE);
				} else {
					gotoMode(FTPAdapter.WPMODE);
				}
			}
		});
		new ToolItem(toolBar, SWT.SEPARATOR);
		final ToolItem ChooseItem = new ToolItem(toolBar, SWT.CHECK);
		ChooseItem.setText("备份至网盘");
		Image SAVEicon = new Image(mainCom.getDisplay(),
				"icons/toolbar/save.ico");
		ChooseItem.setImage(SAVEicon);
		ChooseItem.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				if (ChooseItem.getSelection()) {
					saveToWP = 1;
				} else {
					saveToWP = 0;
				}
			}
		});
		new ToolItem(toolBar, SWT.SEPARATOR);
		ToolItem backItem = new ToolItem(toolBar, SWT.PUSH);
		// backItem.setText("BACK");
		Image icon = new Image(mainCom.getDisplay(), "icons/toolbar/back.bmp");
		backItem.setImage(icon);
		backItem.addListener(SWT.Selection, backAction);
		ToolItem refreshItem = new ToolItem(toolBar, SWT.PUSH);
		Image refreshIcon = new Image(mainCom.getDisplay(),
				"icons/toolbar/refresh.bmp");
		refreshItem.setImage(refreshIcon);
		refreshItem.addListener(SWT.Selection, refreshAction);
		/*
		 * FTP连接栏
		 */
		ToolItem ftpTextItem = new ToolItem(toolBar, SWT.SEPARATOR);
		ftpText = new Combo(toolBar, SWT.BORDER | SWT.SINGLE);
		ftpText.setSize(200, 15);
		initFtpText();
		ftpText.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				System.out.println("widgetSelected");
			}
		});

		ftpText.addFocusListener(new FocusAdapter() {
			public void focusGained(FocusEvent e) {
				ftpText.removeAll();
				initFtpText();
			}
		});
		/*
		 * 添加回车监听
		 */
		ftpText.addKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent e) {
				if (e.character == SWT.CR) {
					String str = ftpText.getText();
					gotoFTP(str);
				}
			}
		});
		ftpTextItem.setWidth(ftpText.getSize().x);
		ftpTextItem.setControl(ftpText);
		ftpText.setText("");
		/*
		 * 添加连接按钮
		 */
		ToolItem connectButton = new ToolItem(toolBar, SWT.PUSH);
		connectButton.setText("连接");
		connectButton.addListener(SWT.Selection, connectAction);
		// new ToolItem(toolBar, SWT.SEPARATOR);
		/*
		 * 地址栏
		 */
		ToolItem pathTextItem = new ToolItem(toolBar, SWT.SEPARATOR);
		pathText = new Text(toolBar, SWT.BORDER | SWT.SINGLE);
		pathText.setMessage("文件路径");
		pathText.setSize(600, 15);
		pathTextItem.setWidth(pathText.getSize().x);
		pathTextItem.setControl(pathText);
		pathText.setText("");
		/*
		 * 添加回车监听
		 */
		pathText.addKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent e) {

				if (e.character == SWT.CR) {
					String str = pathText.getText();
					gotoPath(str);
				}
			}
		});
		/*
		 * set main form
		 */
		// SashForm sash_form = new SashForm(mainCom, SWT.HORIZONTAL |
		// SWT.NULL);
		// sash_form.setLayoutData(BorderLayout.CENTER);
		/*
		 * 设置主界面的左边部分
		 */
		// final Composite leftComposite = new Composite(sash_form, SWT.NONE);
		// leftComposite.setLayout(new BorderLayout(0, 0));
		/*
		 * 设置主界面的右边部分
		 */
		final Composite rightComposite = new Composite(mainCom, SWT.NONE);
		rightComposite.setLayoutData(BorderLayout.CENTER);
		rightComposite.setLayout(new BorderLayout(0, 0));
		/*
		 * set file tree
		 */
		// tv = new TreeViewer(leftComposite);
		//
		// tv.setContentProvider(new FileTreeContentProvider());
		// tv.setLabelProvider(new FileTreeLabelProvider());
		// tv.addFilter(new AllowOnlyFoldersFilter());
		// //tv.setInput();
		// tree = tv.getTree();
		// tree.setLayoutData(BorderLayout.CENTER);
		// /*
		// * set tv listener
		// */
		// tv.addSelectionChangedListener(new ISelectionChangedListener()
		// {
		// public void selectionChanged(SelectionChangedEvent event)
		// {
		// System.out.println("selection changed!!!!!!!!!!!!!!!!!!");
		// IStructuredSelection selection =
		// (IStructuredSelection)event.getSelection();
		// Object selectedFile = selection.getFirstElement();
		// //tv.expandToLevel(selectedFile, 1);
		// //fileTable.setInput(selectedFile);
		// System.out.println((String)selectedFile);
		// if(selectedFile != null)
		// {
		// gotoPath((String)selectedFile);
		// }
		// //else
		// //{
		// //pathText.setText( StringUtil.deNull( getCurrentRoot().getPath() )
		// );
		// //}
		// //pathText.setSelection( pathText.getText().length() );
		// //fileNameText.setText( "" );
		// //addFileTypeToCombo( ( File ) selected_file );
		//
		// //doFilter();
		// /*
		// File folder = ( File ) selected_file;
		// int fileNumber = FileUtil.getFileNumberInFolder( folder );
		// int folderNumber = FileUtil.getFolderNumberInFolder( folder );
		// String size = FileUtil.getFolderSize( folder );
		// setStatus( folderNumber + " folders\t" + fileNumber + " files\t" +
		// size );
		// */
		// }
		// } );
		//
		// tv.refresh();
		/*
		 * 设置右边部分底下的文件过滤器
		 */
		final Composite fileterComposite = new Composite(rightComposite,
				SWT.BORDER);
		fileterComposite.setLayout(new RowLayout());
		fileterComposite.setLayoutData(BorderLayout.SOUTH);

		final Label nameLb = new Label(fileterComposite, SWT.NONE);
		nameLb.setText("Name");

		// fileNameText = new Text(fileterComposite, SWT.BORDER);
		/*
		 * 文件过滤功能 fileNameText.addKeyListener(new KeyAdapter() { public void
		 * keyReleased(KeyEvent e) { if("".equals(fileNameText.getText()) ||
		 * fileNameText.getText() == null) { doFilter(); } else if(e.character
		 * == SWT.CR) { // Enter doFilter(); } } }); final RowData
		 * rd_fileNameText = new RowData(); rd_fileNameText.height = 15;
		 * fileNameText.setLayoutData(rd_fileNameText);
		 * fileNameText.setToolTipText("Press Enter to filter files by name");
		 */

		// final Label typeLb = new Label(fileterComposite, SWT.NONE);
		// typeLb.setText("Type");

		// fileTypeCmb = new Combo(fileterComposite, SWT.READ_ONLY);
		/*
		 * 过滤功能 fileTypeCmb.addSelectionListener(fileter_action);
		 * addFileTypeToCombo(new File(diskCmb.getText()));
		 * fileTypeCmb.setToolTipText("Filter files by type");
		 * 
		 * final RowData rd_fileTypeCmb = new RowData(); rd_fileTypeCmb.width =
		 * 40; rd_fileTypeCmb.height = 15;
		 * fileTypeCmb.setLayoutData(rd_fileTypeCmb); /* 添加连接按钮
		 */
		/*
		 * set fileTable and progress Table
		 */
		SashForm tableForm = new SashForm(rightComposite, SWT.HORIZONTAL
				| SWT.NULL);
		/*
		 * 设置右边部分的文件展示列表
		 */
		fileTable = new TableViewer(tableForm, SWT.BORDER | SWT.FULL_SELECTION
				| SWT.MULTI);
		SashForm progressForm = new SashForm(tableForm, SWT.VERTICAL | SWT.NULL);

		progressTable = new TableViewer(progressForm, SWT.BORDER
				| SWT.FULL_SELECTION | SWT.MULTI);
		progressTableDone = new TableViewer(progressForm, SWT.BORDER
				| SWT.FULL_SELECTION | SWT.MULTI);
		/*
		 * 显示文件
		 */
		fileTable.setContentProvider(new FileTableContentProvider());
		progressTable.setContentProvider(new ProgressTableContentProvider(pm));
		progressTableDone
				.setContentProvider(new ProgressTableDoneContentProvider(pm));
		progressTable.setLabelProvider(new ProgressTableLabelProvider());
		progressTableDone
				.setLabelProvider(new ProgressTableDoneLabelProvider());
		fileTable.setLabelProvider(new FileTableLabelProvider(this));
		fileTable.setSorter(new FileSorter());

		fileTable.addDoubleClickListener(openAction);
		progressTable.addDoubleClickListener(progressAction);

		// fileTable.addSelectionChangedListener(openAction);
		// fileTable.addSelectionChangedListener(delete_action);

		table = fileTable.getTable();
		table.setLayout(new BorderLayout(0, 0));
		table.setLayoutData(BorderLayout.CENTER);
		table.setHeaderVisible(true);
		ptable = progressTable.getTable();
		ptable.setLayout(new BorderLayout(0, 0));
		ptable.setLayoutData(BorderLayout.CENTER);
		ptable.setHeaderVisible(true);
		ptableDone = progressTableDone.getTable();
		ptableDone.setLayout(new BorderLayout(0, 0));
		ptableDone.setLayoutData(BorderLayout.CENTER);
		ptableDone.setHeaderVisible(true);

		final List<String> lables = new ArrayList<String>();
		lables.add("文件名");
		lables.add("文件大小");
		lables.add("文件类型");
		lables.add("修改时间");
		ArrayList<LGKLabel> TagKeys = FTPAdapter.getInstance().showAllKey();
		for (int i = 0; i < TagKeys.size(); ++i) {
			lables.add(TagKeys.get(i).getKey());
		}
		List<Integer> columnWidth = new ArrayList<Integer>();
		columnWidth.add(new Integer(150));
		columnWidth.add(new Integer(70));
		columnWidth.add(new Integer(70));
		columnWidth.add(new Integer(150));
		for (int i = 0; i < TagKeys.size(); ++i)
			columnWidth.add(new Integer(70));
		TableColumn[] columns = new TableColumn[lables.size()];
		for (int i = 0; i < columns.length; i++) {
			TableColumn column = columns[i];
			String columnName = lables.get(i);
			column = new TableColumn(table,
					"name".equalsIgnoreCase(columnName) ? SWT.LEFT : SWT.RIGHT);
			// column = new TableColumn(table, SWT.LEFT);
			column.setWidth(columnWidth.get(i));
			column.setText(lables.get(i));
			// lable排序
			final int index = i;
			column.addSelectionListener(new SelectionAdapter() {
				public void widgetSelected(SelectionEvent e) {
					Map<String, String> sortTypeMap = EasyFileConstant
							.getSortTypeMap();
					String sortTypeKey = lables.get(index);
					String sortTypeValue = sortTypeMap.get(sortTypeKey);
					doSort(sortTypeValue);
				}
			});
		}
		List<String> plables = new ArrayList<String>();
		plables.add("文件名");
		plables.add("进度");
		plables.add("状态");
		TableColumn[] pcolumns = new TableColumn[plables.size()];
		TableColumn[] pcolumnsDone = new TableColumn[plables.size()];
		for (int i = 0; i < pcolumns.length; i++) {
			TableColumn pcolumn = pcolumns[i];
			TableColumn pcolumnDone = pcolumnsDone[i];
			// String pcolumnName = plables.get(i);
			pcolumn = new TableColumn(ptable, SWT.CENTER);
			pcolumn.setWidth(90);
			pcolumn.setText(plables.get(i));
			pcolumnDone = new TableColumn(ptableDone, SWT.CENTER);
			pcolumnDone.setWidth(90);
			pcolumnDone.setText(plables.get(i));
		}
		fileTable.addSelectionChangedListener(new ISelectionChangedListener() {
			public void selectionChanged(SelectionChangedEvent event) {
				IStructuredSelection selection = (IStructuredSelection) event
						.getSelection();
				if (selection.size() == 0) {
					// File folder = (File) ((IStructuredSelection)
					// tv.getSelection()).getFirstElement();
					return;
				} else if (selection.size() == 1) {
					setStatus(((FTPFile) selection.getFirstElement()).getName());
					return;
				} else {
					String strNumber = "Number of files selected is "
							+ selection.size();
					// String strSize = "Size of files selected is " +
					// EasyFileUtil.getFilesSize(selection.toArray());
					setStatus(strNumber);// + "\t" + strSize);
					return;
				}
			}
		});
		/*
		 * Set pop menu according to the credential of user
		 */
		fileTable.getTable().addListener(SWT.MenuDetect, new Listener() {
			public void handleEvent(Event event) {
				Table t = (Table) event.widget;
				Rectangle r = t.getClientArea();
				Point p = t.getDisplay().map(null, t, event.x, event.y);
				MenuManager popMenuManager = new MenuManager();
				boolean isHeader = ((p.y - r.y) <= t.getHeaderHeight());
				if (isHeader) {
					int result = judgeTarget(p.x - r.x);
					if (result > 0)
						popMenuManager = headerMenuManager(result);
				} else {
					IStructuredSelection selection = getTableSelection();
					System.out.println("selection number = " + selection.size());
					if (selection.size() == 1)
						popMenuManager = createFilePopMenuManager();
					else
						popMenuManager = createNormalPopMenuManager();
				}
				t.setMenu(popMenuManager.createContextMenu(t));
			}
		});
		progressTable.getTable().addListener(SWT.MenuDetect, new Listener() {
			public void handleEvent(Event event) {
				Table t = (Table) event.widget;

				// IStructuredSelection selection = (IStructuredSelection)
				// progressTable.getSelection();
				MenuManager popMenuManager = null;
				System.out
						.println(progressTable.getTable().getSelectionCount());
				if (progressTable.getTable().getSelectionCount() == 1) {
					popMenuManager = createProgressPopMenuManager(progressTable
							.getTable().getSelectionIndex(), 0);
					t.setMenu(popMenuManager.createContextMenu(t));
				} else {
					t.setMenu(null);
				}
			}
		});
		progressTableDone.getTable().addListener(SWT.MenuDetect,
				new Listener() {
					public void handleEvent(Event event) {
						Table t = (Table) event.widget;
						IStructuredSelection selection = (IStructuredSelection) progressTableDone
								.getSelection();
						MenuManager popMenuManager = null;
						System.out.println(selection.size());
						if (progressTableDone.getTable().getSelectionCount() == 1) {
							popMenuManager = createProgressPopMenuManager(
									progressTableDone.getTable()
											.getSelectionIndex(), 1);
							t.setMenu(popMenuManager.createContextMenu(t));
						} else {
							t.setMenu(null);
						}
					}
				});
		fileTable.setInput(new String(""));
		progressTable.setInput(new String(""));
		progressTableDone.setInput(new String(""));
		tableForm.setWeights(new int[] { 6, 3 });
		// sash_form.setWeights(new int[]{1, 5});

		return mainCom;
	}

	/**
	 * 转化模式
	 * 
	 * @param ftpmode
	 *            WPMODE或FTPMODE
	 */
	protected void gotoMode(int ftpmode) {
		// System.out.println("now current mode:" + ftpmode);
		modeChange = 1;
		FTPAdapter.getInstance().changeMode(ftpmode);
		mode = FTPAdapter.getInstance().getCurMode();
		// System.out.println("now really mode: " + mode);
		WPItem.setSelection(mode == FTPAdapter.WPMODE);
		FTPItem.setSelection(mode == FTPAdapter.FTPMODE);
		refresh();
		modeChange = 0;
	}

	/**
	 * 判断用户右键点击的标签，决定弹出哪个右键菜单
	 * 
	 * @param answer
	 *            标签序号
	 */
	private int judgeTarget(int x) {
		int answer = 0;
		TableColumn[] tcs = table.getColumns();
		int[] width = new int[5];
		width[0] = 0;
		for (int i = 1; i < 5; i++) {
			width[i] = width[i - 1] + tcs[i - 1].getWidth();
			if (x < width[i]) {
				answer = i;
				break;
			}
		}
		return answer;
	}

	/**
	 * 进入到某一个文件夹
	 * 
	 * @param path
	 *            文件夹的绝对路径
	 */
	public void gotoPath(String path) {
		boolean change = !path.equals(FTPAdapter.getInstance()
				.getCurrentFTPPath());
		boolean gotoRes = FTPAdapter.getInstance().changeWorkingFTPDirectory(
				path);
		// 设置状态栏
		if (!gotoRes)
			setStatus("进入目录失败");
		path = FTPAdapter.getInstance().getCurrentFTPPath();
		refresh();
		if (!path.equals("/") && change) {
			// tv.setExpandedState(path, true);
			// tv.setSelection(new StructuredSelection(path), true);
		}
		pathText.setText(FTPAdapter.getInstance().getCurrentFTPPath());
		System.out.println(pathText.getText());
	}

	/**
	 * 连接到一个ftp
	 * 
	 * @param ftpAddress
	 *            FTP地址
	 */
	public void gotoFTP(String ftpAddress) {
		// 连接ftpAddress
		int connectResult = FTPAdapter.getInstance().connection(ftpAddress);
		// System.out.println(connectResult);
		if (connectResult == -1) {
			setFtpText(FTPAdapter.getInstance().getCurServer());
			return;
		}
		// 载入证书过程
		int loadRes = 0;
		if (connectResult == 1)
			loadRes = FTPAdapter.getInstance().loadCredential("");
		if (connectResult == 0) {
			setStatus("选择证书以登陆");
			FileDialog credentialDialog = new FileDialog(getShell(), SWT.OPEN);
			credentialDialog.setFilterPath("");
			credentialDialog.setText("选择证书");
			String credentialPath = credentialDialog.open();
			System.out.println("credentialPath:" + credentialPath);
			if (credentialPath == null) {
				loadRes = FTPAdapter.getInstance().loadCredential("[][///");
			} else {
				loadRes = FTPAdapter.getInstance().loadCredential(
						credentialPath);
			}
		}
		// 若loadRes为0，则输入用户名密码
		if (loadRes == 1) {
			setStatus("进入FTP   当前FTP为" + ftpAddress);
			addFTPHistory(ftpAddress);
			mode = FTPAdapter.FTPMODE;
			FTPItem.setSelection(true);
			WPItem.setSelection(false);
			// tv.setInput("");
			// tv.setExpandedState("/", true);
			// tv.setSelection(new StructuredSelection("/"), true);
			gotoPath("/");
		} else {
			setStatus("输入用户名密码以登陆");
			ConnectDialog loginDialog = new ConnectDialog(this);
			loginDialog.setBlockOnOpen(true);
			loginDialog.open();
		}
		setFtpText(FTPAdapter.getInstance().getCurServer());
		/*
		 * 获取limit并显示
		 */
		limit = FTPAdapter.getInstance().getPermission() - 1;
		System.out.println("limit = " + limit);
		setStatus("");
	}

	/**
	 * 在ftp历史中加入address这个地址
	 * 
	 * @param address
	 *            需要新加入的ftp地址
	 */
	public void addFTPHistory(String address) {
		ftpHistory.remove(address);
		ftpHistory.add(address);
		if (ftpHistory.size() > 10)
			ftpHistory.remove(0);
		File f = new File("./history/ftpHistory.txt");
		if (!f.exists()) {
			System.out.println("new " + (new File("./history")).mkdirs());
			try {
				f.createNewFile();
			} catch (IOException e) {
				System.out.println(e);
			}
		}
		try {
			FileOutputStream out = new FileOutputStream(
					"./history/ftpHistory.txt");
			PrintStream p = new PrintStream(out);
			for (String fs : ftpHistory) {
				p.println(fs);
			}
			p.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 刷新主界面
	 */
	public void refresh() {
		if (mode == FTPAdapter.WPMODE && modeChange == 0)
			return;
		makeFileTable();
		// if (mode == FTPAdapter.FTPMODE)
		// {
		// tv.setInput("");
		// System.out.println("tv.setInput end");
		// tv.setExpandedState(FTPAdapter.getInstance().getCurrentFTPPath(),
		// true);
		// System.out.println("tv.setExpandedState end");
		// //tv.setSelection(new
		// StructuredSelection(FTPAdapter.getInstance().getCurrentFTPPath()),
		// true);
		// }
		fileTable.setInput(FTPAdapter.getInstance().getCurrentFTPPath());
		System.out.println("fileTable.setInput end");
	}

	/**
	 * 得到当前的表格选中项
	 */
	public IStructuredSelection getTableSelection() {
		return (IStructuredSelection) (fileTable.getSelection());
	}

	/**
	 * 得到输入栏中的文件地址
	 * 
	 * @return
	 */
	public String getAddressText() {
		System.out.println(pathText.getText());
		return pathText.getText();
	}

	/**
	 * 得到当前的ftp地址
	 * 
	 * @return
	 */
	public String getFTPText() {
		return ftpText.getText();
	}

	/**
	 * 得到saveToWP
	 * 
	 * @return
	 */
	public int getSaveToWP() {
		return saveToWP;
	}

	/**
	 * 得到mode
	 * 
	 * @return
	 */
	public int getMode() {
		return mode;
	}

	/**
	 * 设置FTP栏的地址
	 * 
	 * @param ftp
	 */
	public void setFtpText(String ftp) {
		ftpText.setText(ftp);
	}

	// /**
	// * tv set Input
	// * @param input
	// */
	// public void treeSetInput(String input)
	// {
	// System.out.println("set input here");
	// tv.setInput(input);
	// tv.setExpandedState("/", true);
	// //tv.setSelection(new StructuredSelection("/"), true);
	// }
	/**
	 * update progressTable
	 */
	public void updateProgressTable() {
		Table t = progressTable.getTable();
		int s = t.getSelectionIndex();
		progressTable.refresh();
		t.setSelection(s);
		Table t2 = progressTableDone.getTable();
		int s2 = t2.getSelectionIndex();
		progressTableDone.refresh();
		t2.setSelection(s2);

	}

	public TableViewer getTableViewer() {
		return fileTable;
	}

	/**
	 * 
	 * @return
	 */
	public TableViewer getProgressTable() {
		return progressTable;
	}

	/**
	 * do this when start a new download
	 * 
	 * @param l
	 * @param r
	 */
	public void newDownload(List<String> r, List<String> l, int cm) {
		System.out.println("nd");
		FileStatus fs = new FileStatus();
		fs.localNameList = l;
		fs.remoteNameList = r;
		fs.progress = 0;
		fs.status = 2;
		fs.curMode = cm;
		pm.fsList.add(fs);
		pm.saveToFile();
	}

	/**
	 * do this when start a new upload
	 * 
	 * @param l
	 * @param r
	 */
	public void newUpload(List<String> l, List<String> r, int wp, int cm) {
		System.out.println("nd");
		FileStatus fs = new FileStatus();
		fs.localNameList = l;
		fs.remoteNameList = r;
		fs.progress = 0;
		fs.status = -2;
		fs.wp = wp;
		fs.curMode = cm;
		pm.fsList.add(fs);
		pm.saveToFile();
	}

	/**
	 * Main
	 * 
	 * @param args
	 */
	public static void main(String args[]) {
		// System.out.println(new String("/1/1").split("/").length);
		MainWindow mainWindow = new MainWindow();
		mainWindow.setBlockOnOpen(true);
		mainWindow.open();
	}
}
