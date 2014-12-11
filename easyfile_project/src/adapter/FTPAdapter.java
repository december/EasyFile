package adapter;

import java.io.*;

import basic.*;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.net.ftp.FTPFile;

public class FTPAdapter {
	/**
	 * WPMODE为网盘模式，FTPMODE为ftp模式
	 */
	public final static int WPMODE = 1, FTPMODE = 0;
	private final boolean USEENCODER = true;
	private ConnectionS mainConnect = new ConnectionS();
	private BaiduConnection baiduConnection = new BaiduConnection();
	private File curPos, credential;
	private ProcessManager processManager;
	@SuppressWarnings("unused")
	private String server, userName, password;
	/**
	 * 默认证书位置
	 */
	public static final String defaultCredential = "DefaultCredential"
			+ File.separator + "LastUser.cre";
	private String permission;
	private boolean onOurFTP = false, haveLoggedIn = false;
	private LabelManager labelManager;
	private ConnectionS newmainConnect;
	private String newuserName;
	private String newpassword;
	private String newpermission;
	private String newserver;
	private int curMode;

	private static FTPAdapter adapter = new FTPAdapter();

	/**
	 * FTPAdapter 为单件
	 * 
	 * @return 返回该单件
	 */
	public static FTPAdapter getInstance() {
		return adapter;
	}

	/**
	 * 单件的构造函数
	 */
	private FTPAdapter() {
		curPos = File.listRoots()[0];
	}

	/**
	 * 获得用户的权限等级
	 * 
	 * @return -1为未知错误, 0为非已知ftp服务器, 1为最低级别, 2为中等级别, 3为最高级别
	 */
	public int getPermission() {
		if (!this.onOurFTP)
			return 0;
		if (this.permission.equals("1"))
			return 1;
		else if (this.permission.equals("2"))
			return 2;
		else if (this.permission.equals("3"))
			return 3;
		else
			return -1;
	}

	/**
	 * 尝试连接ftp服务器
	 * 
	 * @param server
	 *            服务器名字
	 * @return 0为未找到默认整数, 1为成功连接
	 */
	public int connection(String server) {
		this.onOurFTP = false;
		this.newserver = server;
		this.newmainConnect = new ConnectionS();
		this.newmainConnect.connection(server);
		credential = new File(FTPAdapter.defaultCredential);
		if (!credential.exists())
			return 0;
		System.out.println(1);
		return 1;
	}

	/**
	 * 导入证书。输入可以是空串，表示导入默认证书。
	 * 
	 * @param path
	 *            证书路径
	 * @return 0为导入失败需要用户登录，1为成功，自动登录
	 */
	public int loadCredential(String path) {
		if (!path.equals(""))
			this.credential = new File(path);
		if (!this.credential.exists())
			return 0;
		int result = 0;
		String read = "";
		try {
			InputStreamReader in = new InputStreamReader(new FileInputStream(
					credential));
			while (true) {
				int k = in.read();
				if (k == -1)
					break;
				read = read + (char) k;
			}
			in.close();
			String content = this.USEENCODER ? (new String(
					Coder.decryptBASE64(read))) : read;
			// System.out.println(newserver);
			String list[] = content.split("\n");
			this.newuserName = list[0];
			this.newpassword = list[1];
			this.newpermission = list[2];
			for (int i = 3; i < list.length; ++i)
				if (list[i].equals(newserver)) {
					this.logIn(newuserName, newpassword);
					this.permission = this.newpermission;
					result = 1;
					onOurFTP = true;
					OutputStreamWriter out = new OutputStreamWriter(
							new FileOutputStream(FTPAdapter.defaultCredential));
					out.write(read);
					out.close();
					break;
				}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 用户登录
	 * 
	 * @param userName
	 *            用户名字
	 * @param password
	 *            用户密码
	 * @return 返回是否登录成功
	 */
	public boolean logIn(String userName, String password) {
		boolean ret = this.newmainConnect.login(userName, password);
		if (!ret)
			return false;
		if (this.haveLoggedIn)
			this.mainConnect.logout();
		if (this.isConnection())
			this.mainConnect.close();
		this.mainConnect = this.newmainConnect;
		this.server = this.newserver;
		haveLoggedIn = true;
		System.out.println("in login");
		this.userName = userName;
		this.password = password;
		this.baiduConnection.connection(server);
		this.mainConnect.setWPconn(baiduConnection);
		curMode = FTPMODE;
		this.processManager = new ProcessManager(userName, password, server);
		System.out.println("out of login");
		labelManager = new LabelManager(this.mainConnect);
		return ret;
	}

	/**
	 * 用户登出
	 * 
	 * @return 返回是否登出成功
	 */
	public boolean logOut() {
		haveLoggedIn = false;
		boolean flag = true;
		flag &= this.mainConnect.logout();
		flag &= this.processManager.logout();
		return flag;
	}

	/**
	 * 得到当前ftp路径。
	 * 
	 * @return 返回当前ftp路径
	 */
	public String getCurrentFTPPath() {
		if (this.curMode == WPMODE)
			return this.mainConnect.getWPconn().getPath();
		return this.mainConnect.getPath();
	}

	/**
	 * 得到当前本地路径
	 * 
	 * @return 返回本地路径
	 */
	public String getCurrentLocalPath() {
		return this.curPos.getAbsolutePath();
	}

	/**
	 * 前往ftp的父目录
	 * 
	 * @return 返回是否成功
	 */
	public boolean changetoParentFTPDirectory() {
		if (this.curMode == WPMODE)
			return this.mainConnect.getWPconn().changeWorkingDirectory(
					this.getParentDirectory(this.mainConnect.getWPconn()
							.getPath()));
		return this.mainConnect.changetoParentDirectory();
	}

	/**
	 * 前往本地的父目录
	 * 
	 * @return 返回是否成功
	 */
	public boolean changetoParentLocalDirectory() {
		if (this.curPos.getParentFile() == null)
			return false;
		this.curPos = this.curPos.getParentFile();
		return true;
	}

	/**
	 * 前往特定的ftp目录
	 * 
	 * @param path
	 *            所求的ftp目录
	 * @return 返回是否成功
	 */
	public boolean changeWorkingFTPDirectory(String path) {
		if (this.curMode == WPMODE)
			return this.mainConnect.getWPconn().changeWorkingDirectory(
					removeTitle(path));
		return this.mainConnect.changeWorkingDirectory(removeTitle(path));
	}

	/**
	 * 把ftp路径的不必要的前缀去掉
	 * 
	 * @param path
	 *            原路径
	 * @return 返回去掉之后的处理
	 */
	private String removeTitle(String path) {
		int begin = 0;
		if (path.charAt(0) == 'f')
			begin = 6;
		for (int i = begin; i < path.length(); ++i)
			if (path.charAt(i) == '/')
				return path.substring(i).intern();
		return path.intern();
	}

	/**
	 * 前往特定的本地目录
	 * 
	 * @param path
	 *            所求的目录
	 * @return 返回是否成功
	 */
	public boolean changeWorkingLocalDirectory(String path) {
		File newPos = new File(path);
		if (newPos.exists()) {
			this.curPos = newPos;
			return true;
		}
		return false;
	}

	/**
	 * 显示当前ftp目录下的文件列表
	 * 
	 * @return 返回文件列表
	 */
	public List<FTPFile> showServerFileList() {
		if (this.curMode == WPMODE)
			return this.getWPFileList(this.mainConnect.getWPconn().getPath());
		List<FTPFile> ret = this.mainConnect.showFileList();
		for (FTPFile file : ret)
			if (file.getName().equals("config.easyfile")) {
				ret.remove(file);
				break;
			}
		for (FTPFile file : ret)
			if (file.getName().equals("labels.easyfile")) {
				ret.remove(file);
				break;
			}
		return ret;
	}

	/**
	 * 显示当前本地目录下的文件列表
	 * 
	 * @return 返回文件列表
	 */
	public File[] showLocalFileList() {
		return this.curPos.listFiles();
	}

	/**
	 * 新建或继续下载任务
	 * 
	 * @param serverFileName
	 *            服务器路径
	 * @param localFileName
	 *            本地路径
	 * @param curMode
	 *            当前是网盘模式还是ftp模式
	 */
	public void newDownloadProcess(final List<String> serverFileName,
			final List<String> localFileName, int curMode) {
		if (curMode == WPMODE) {
			this.downloadFileFromWP("/" + server + "/" + serverFileName.get(0),
					localFileName.get(0));
			return;
		}
		final String path = mainConnect.getPath();
		new Thread() {

			@Override
			public void run() {
				synchronized (processManager) {
					while (!processManager.vacant())
						try {
							wait();
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					processManager.addProcess(serverFileName, localFileName, 0,
							path, 0);
				}
			}

		}.start();
	}

	/**
	 * 新建或继续上传任务
	 * 
	 * @param serverFileName
	 *            服务器路径
	 * @param localFileName
	 *            本地路径
	 * @param withWP
	 *            是否和网盘同步
	 */
	public void newUploadProcess(final List<String> serverFileName,
			final List<String> localFileName, final int withWP) {
		final String path = mainConnect.getPath();
		new Thread() {

			@Override
			public void run() {
				synchronized (processManager) {
					while (!processManager.vacant())
						try {
							processManager.wait();
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					processManager.addProcess(serverFileName, localFileName, 1,
							path, withWP);
				}
			}

		}.start();
	}

	/**
	 * 对ftp服务器的文件进行重命名
	 * 
	 * @param fileName
	 *            文件原名
	 * @param newFileName
	 *            文件新名字
	 */
	public boolean renameServerFile(String fileName, String newFileName) {
		boolean ret = this.mainConnect.rename(this.mainConnect.getPath() + "/"
				+ fileName, this.mainConnect.getPath() + "/" + newFileName, server);
		if (!ret)
			return false;
		this.labelManager.renameFile(this.mainConnect.getPath() + "/"
				+ fileName, this.mainConnect.getPath() + "/" + newFileName);
		return ret;
	}

	/**
	 * 把一个空文件夹在ftp服务器目录中删掉
	 * 
	 * @param path
	 *            文件夹的目录
	 */
	public boolean removeServerDirectory(String path) {
		if (this.mainConnect.fileIsExist(path))
			return false;
		this.labelManager.deleteLabel(path);
		return this.mainConnect.removeDirectory(path);
	}

	/**
	 * 在ftp所求目录中新建一个空文件夹
	 * 
	 * @param path
	 *            新建文件夹的目录
	 */
	public boolean makeServerDirectory(String path) {
		return this.mainConnect.makeDirectory(path);
	}

	/**
	 * 询问ftp服务器上某文件是否存在
	 * 
	 * @param filename
	 *            该文件的路径
	 * @return 返回是否存在
	 */
	public boolean fileExistsFTP(String filename) {
		return this.mainConnect.fileIsExist(filename);
	}

	/**
	 * 询问本地某文件是否存在
	 * 
	 * @param filename
	 *            该文件的路径
	 * @return 返回是否存在
	 */
	public boolean fileExistsLocal(String filename) {
		return new File(filename).exists();
	}

	/**
	 * 删除ftp服务器的某文件
	 * 
	 * @param fileName
	 *            该文件的路径
	 * @return 返回是否成功
	 */
	public boolean deleteServerFile(String fileName) {
		System.out.println("in delete server file");
		if (!this.mainConnect.fileIsExist(fileName))
			return false;
		System.out.println("in delete server file after check exist");
		if (this.mainConnect.getFile(fileName).isFile()) {
			boolean ret = this.mainConnect.deleteServerFile(fileName);
			this.labelManager.deleteLabel(fileName);
			System.out.println("in FTPAdapter : " + ret);
			return ret;
		} else {
			String curPath = this.mainConnect.getPath();
			this.mainConnect.changeWorkingDirectory(fileName);
			boolean flag = true;
			for (FTPFile file : this.mainConnect.showFileList()) {
				if (file.isFile()) {
					flag &= this.mainConnect.deleteServerFile(fileName + '/'
							+ file.getName());
					this.labelManager.deleteLabel(fileName + '/'
							+ file.getName());
				} else
					flag &= deleteServerFile(fileName + '/' + file.getName());
			}
			this.mainConnect.removeDirectory(fileName);
			this.labelManager.deleteLabel(fileName);
			this.mainConnect.changeWorkingDirectory(curPath);
			return flag;
		}
	}

	/**
	 * 暂停或停止某任务——用于上传或下载任务
	 * 
	 * @param serverFileName
	 *            任务的服务器路径
	 * @param localFileName
	 *            任务的本地路径
	 */
	public void cutProcess(final List<String> serverFileName,
			final List<String> localFileName) {
		this.processManager.cutProcess(serverFileName, localFileName);
	}

	/**
	 * 得到任务完成百分比
	 * 
	 * @param serverFileName
	 *            任务的服务器路径
	 * @param localFileName
	 *            任务的本地路径
	 * @param mode
	 *            当前是网盘模式还是ftp模式
	 * @return 返回百分比，从0到1
	 */
	public double getAccomplishRate(final List<String> serverFileName,
			final List<String> localFileName, int mode) {
		long p = 0, q = 0;
		if (mode != WPMODE)
			return this.processManager.getAccomplishRate(serverFileName,
					localFileName);
		for (int i = 0; i < serverFileName.size(); ++i) {
			File tmp = new File(localFileName.get(i));
			if (tmp != null)
				q += tmp.length();
			p += this.mainConnect.getWPconn()
					.getFile("/" + server + "/" + serverFileName.get(i))
					.getSize();
		}
		if (p > q)
			return (double) q / p;
		else
			return (double) p / q;
	}

	/**
	 * 判断当前是否正在连接
	 * 
	 * @return 返回当前是否正在连接
	 */
	public boolean isConnection() {
		return this.mainConnect.isConnection();
	}

	/**
	 * 判断当前是否已登录
	 * 
	 * @return 返回是否登录
	 */
	public boolean haveLoggedIn() {
		return haveLoggedIn;
	}

	/**
	 * 对文件增加标签
	 * 
	 * @param fileName
	 *            文件路径
	 * @param newtags
	 *            标签列表
	 */
	public void addTag(String fileName, ArrayList<LGKLabel> newtags) {
		if (fileName.length() > 1 && fileName.charAt(1) == '/')
			fileName = fileName.substring(1);
		this.labelManager.addLabel(fileName, newtags);
	}

	/**
	 * 删除标签
	 * 
	 * @param fileName
	 *            文件路径
	 * @param deltag
	 *            待删除的标签
	 */
	public void delTag(String fileName, LGKLabel deltag) {
		if (fileName.length() > 1 && fileName.charAt(1) == '/')
			fileName = fileName.substring(1);
		this.labelManager.deleteLabel(fileName, deltag);
	}

	/**
	 * 显示文件的标签列表
	 * 
	 * @param fileName
	 *            所求文件
	 * @return 标签列表
	 */
	public ArrayList<LGKLabel> showTag(String fileName) {
		if (fileName.length() > 1 && fileName.charAt(1) == '/')
			fileName = fileName.substring(1);
		ArrayList<LGKLabel> tmp = this.labelManager.getFileLabel()
				.get(fileName);
		if (tmp == null)
			tmp = new ArrayList<LGKLabel>();
		for (LGKLabel label : this.showAllKey()) {
			boolean flag = true;
			for (LGKLabel l : tmp)
				if (l.getKey().equals(label.getKey())) {
					flag = false;
					break;
				}
			if (flag)
				tmp.add(label);
		}
		ArrayList<LGKLabel> ret = new ArrayList<LGKLabel>();
		for (LGKLabel l : tmp) {
			boolean flag = true;
			for (LGKLabel label : this.showAllKey())
				if (l.getKey().equals(label.getKey())) {
					flag = false;
					break;
				}
			if (!flag)
				ret.add(l);
		}
		return ret;
	}

	/**
	 * 得到特定路径的父目录路径
	 * 
	 * @param dir
	 *            特定路径
	 * @return 父目录路径
	 */
	public String getParentDirectory(String dir) {
		if (this.curMode == WPMODE)
			return this.getWPParentDirectory(dir);
		if (dir.length() > 1 && dir.charAt(1) == '/')
			dir = dir.substring(1);
		if (dir.equals("/"))
			return dir;
		for (int i = dir.length() - 1; i >= 0; --i)
			if (dir.charAt(i) == '/')
				return dir.substring(0, i);
		return dir;
	}

	/**
	 * 显示特定路径的文件列表
	 * 
	 * @param dir
	 *            特定路径
	 * @return 文件列表
	 */
	public List<FTPFile> getFileList(String dir) {
		if (this.curMode == WPMODE)
			return this.getWPFileList("/" + server + "/" + dir);
		if (dir.length() > 1 && dir.charAt(1) == '/')
			dir = dir.substring(1);
		assert (this.mainConnect.fileIsExist(dir));
		assert (this.mainConnect.getFile(dir).isDirectory());
		String curPath = mainConnect.getPath();
		mainConnect.changeWorkingDirectory(dir);
		List<FTPFile> ret = this.mainConnect.showFileList();
		mainConnect.changeWorkingDirectory(curPath);
		return ret;
	}

	/**
	 * 得到某路径的文件
	 * 
	 * @param file
	 *            特定路径
	 * @return 文件，空为无该文件
	 */
	public FTPFile getFile(String file) {
		if (this.curMode == WPMODE)
			return this.getWPFile("/" + server + "/" + file);
		if (file.length() > 1 && file.charAt(1) == '/')
			file = file.substring(1);
		assert (this.mainConnect.fileIsExist(file));
		assert (this.mainConnect.getFile(file).isFile());
		String curPath = mainConnect.getPath();
		mainConnect.changeWorkingDirectory(this.getParentDirectory(file));
		FTPFile ret = this.mainConnect.getFile(file);
		mainConnect.changeWorkingDirectory(curPath);
		return ret;
	}

	/**
	 * 得到当前服务器名字
	 * 
	 * @return 服务器名字
	 */
	public String getCurServer() {
		return this.server;
	}

	/**
	 * 显示所有种类的标签
	 * 
	 * @return 所有种类的标签
	 */
	public ArrayList<LGKLabel> showAllKey() {
		if (!this.haveLoggedIn)
			return new ArrayList<LGKLabel>();
		if (this.labelManager.isLabelOrNot())
			return this.labelManager.getLabelList();
		return new ArrayList<LGKLabel>();
	}

	/**
	 * 增加一种新的标签
	 * 
	 * @param newLabel
	 *            新标签
	 */
	public void addKey(LGKLabel newLabel) {
		this.labelManager.addNewKindLabel(newLabel);
	}

	/**
	 * 删除一种标签
	 * 
	 * @param oldLabel
	 *            待删标签
	 */
	public void delKey(LGKLabel oldLabel) {
		this.labelManager.deleteLabel(oldLabel);
	}

	/**
	 * 修改一种标签的属性
	 * 
	 * @param newLabel
	 *            待改标签
	 */
	public void changeLabelByKey(LGKLabel newLabel) {
		this.labelManager.notifyLabel(newLabel);
	}

	/**
	 * 修改文件的各种标签
	 * 
	 * @param fileName
	 *            文件的路径
	 * @param newLabelList
	 *            新标签列表
	 */
	public void changeLabelOfFile(String fileName,
			ArrayList<LGKLabel> newLabelList) {
		this.labelManager.addLabel(fileName, newLabelList);
	}

	/**
	 * 从网盘上下载文件
	 * 
	 * @param serverFileName
	 *            服务器路径
	 * @param localFileName
	 *            本地路径
	 */
	public void downloadFileFromWP(final String serverFileName,
			final String localFileName) {
		new Thread() {
			@Override
			public void run() {
				mainConnect.getWPconn().downLoadFile(serverFileName,
						localFileName);
			}
		}.start();
	}

	/**
	 * 得到网盘特定目录的父目录
	 * 
	 * @param dir
	 *            特定路径
	 * @return 返回父目录
	 */
	public String getWPParentDirectory(String dir) {
		if (dir.length() > 1 && dir.charAt(1) == '/')
			dir = dir.substring(1);
		if (dir.equals("/"))
			return dir;
		for (int i = dir.length() - 1; i >= 0; --i)
			if (dir.charAt(i) == '/')
				return dir.substring(0, i);
		return dir;
	}

	/**
	 * 得到特定网盘路径下的文件
	 * 
	 * @param dir
	 *            特定路径
	 * @return 返回文件列表
	 */
	public List<FTPFile> getWPFileList(String dir) {
		if (dir.length() > 1 && dir.charAt(1) == '/')
			dir = dir.substring(1);
		String curPath = mainConnect.getWPconn().getPath();
		mainConnect.getWPconn().changeWorkingDirectory(dir);
		List<WPFile> ret = this.mainConnect.getWPconn().showFileList();
		mainConnect.getWPconn().changeWorkingDirectory(curPath);
		List<FTPFile> ans = new ArrayList<FTPFile>();
		for (WPFile file : ret)
			ans.add(file);
		return ans;
	}

	/**
	 * 得到网盘的文件
	 * 
	 * @param file
	 *            文件路径
	 * @return 文件，空为无该文件
	 */
	public FTPFile getWPFile(String file) {
		if (file.length() > 1 && file.charAt(1) == '/')
			file = file.substring(1);
		String curPath = mainConnect.getWPconn().getPath();
		mainConnect.getWPconn().changeWorkingDirectory(
				this.getWPParentDirectory(file));
		FTPFile ret = this.mainConnect.getWPconn().getFile(file);
		mainConnect.getWPconn().changeWorkingDirectory(curPath);
		return ret;
	}

	/**
	 * 更改当前模式
	 * 
	 * @param mode
	 *            新的模式
	 */
	public void changeMode(int mode) {
		this.curMode = mode;
	}

	/**
	 * 得到当前模式
	 * 
	 * @return 当前模式
	 */
	public int getCurMode() {
		return curMode;
	}
}
