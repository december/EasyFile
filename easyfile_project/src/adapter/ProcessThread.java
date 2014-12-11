package adapter;

import java.io.File;
import java.util.*;

import org.apache.commons.net.ftp.FTPFile;

import basic.*;

class ProcessThread extends Thread {
	private final int DOWNLOAD = 0, UPLOAD = 1, LENGTH = 100000;
	/**
	 * ftp服务器，服务器路径列表，本地路径列表，已完成的大小，总共大小，是否要求中断
	 */
	ConnectionS connection;
	ArrayList<String> serverFileName, localFileName;
	long accomplish, total;
	boolean cut;
	private String user, pass, path, server;
	private ProcessManager manager;
	private int type, num, withWP;

	/**
	 * 新建任务线程
	 * 
	 * @param manager
	 *            任务管理器的引用
	 * @param user
	 *            用户名字
	 * @param pass
	 *            用户密码
	 * @param num
	 *            所用的线程序号
	 * @param serverFileName
	 *            服务器文件路径
	 * @param localFileName
	 *            本地文件路径
	 * @param type
	 *            上传或者下载
	 * @param server
	 *            服务器名字
	 * @param path
	 *            服务器文件父目录
	 * @param withWP
	 *            是否和网盘同步
	 */
	ProcessThread(ProcessManager manager, String user, String pass, int num,
			List<String> serverFileName, List<String> localFileName, int type,
			String server, String path, int withWP) {
		this.manager = manager;
		this.connection = new ConnectionS();
		this.server = server;
		this.user = user;
		this.pass = pass;
		this.path = path;
		this.serverFileName = (ArrayList<String>) serverFileName;
		this.localFileName = (ArrayList<String>) localFileName;
		this.type = type;
		this.num = num;
		this.accomplish = 0;
		this.total = 1;
		this.cut = false;
		this.withWP = withWP;
	}

	/**
	 * 线程的执行
	 */
	@Override
	public void run() {
		boolean flag = true;
		this.connection.connection(server);
		BaiduConnection bc = new BaiduConnection();
		bc.connection(server);
		this.connection.setWPconn(bc);
		this.connection.login(user, pass);
		this.connection.changeWorkingDirectory(path);
		this.accomplish = 0;
		this.total = 0;
		System.out.println("in process thread : cal size");
		for (int i = 0; i < serverFileName.size(); ++i)
			calSize(this.serverFileName.get(i), this.localFileName.get(i));
		System.out.println("in process thread : start progress");
		for (int i = 0; i < serverFileName.size(); ++i)
			flag &= startProgress(this.serverFileName.get(i),
					this.localFileName.get(i));
		if (!flag) {
			System.out.println("FAILURE FROM SERVER. PROCESS ABORTED.");
		} else {
			System.out.println("OPERATION SUCCESSFUL");
		}
		this.connection.logout();
		this.connection.close();
		this.manager.free(num);
	}

	/**
	 * 开始复制的过程
	 * 
	 * @param serverFileName
	 *            服务器文件路径
	 * @param localFileName
	 *            本地文件路径
	 * @return 返回是否成功
	 */
	private boolean startProgress(String serverFileName, String localFileName) {
		boolean flag = false;
		FTPFile f1 = this.connection.getFile(serverFileName);
		File f2 = new File(localFileName);
		if (type == DOWNLOAD) {
			System.out.println("in process thread : download");
			if (this.connection.fileIsExist(serverFileName) && f1.isFile()) {
				System.out.println("in process thread : download, is file");
				flag = tryCopyFile(serverFileName, localFileName, DOWNLOAD);
			} else if (this.connection.fileIsExist(serverFileName)
					&& f1.isDirectory()) {
				System.out.println("in process thread : download, is dir");
				flag = copyDirectory(serverFileName, localFileName, DOWNLOAD);
			} else
				flag = false;
		} else {
			System.out.println("in process thread : upload");
			if (f2.exists() && f2.isFile()) {
				System.out.println("in process thread : upload, is file");
				flag = tryCopyFile(serverFileName, localFileName, UPLOAD);
				System.out.println("in process thread : start wp");
				if (this.withWP == 1)
					this.connection.updateWPFile(localFileName, "/" + server
							+ "/" + serverFileName);
			} else if (f2.exists() && f2.isDirectory()) {
				System.out.println("in process thread : upload, is dir");
				flag = copyDirectory(serverFileName, localFileName, UPLOAD);
			} else
				flag = false;
		}
		return flag;
	}

	/**
	 * 计算文件列表的总大小
	 * 
	 * @param serverFileName
	 *            服务器文件路径
	 * @param localFileName
	 *            本地文件路径
	 */
	private void calSize(String serverFileName, String localFileName) {
		FTPFile f1 = this.connection.getFile(serverFileName);
		File f2 = new File(localFileName);
		if (type == DOWNLOAD) {
			if (this.connection.fileIsExist(serverFileName) && f1.isFile()) {
				this.total += this.connection.getFile(serverFileName).getSize();
				if (f2.exists())
					this.accomplish += f2.length();
			} else if (this.connection.fileIsExist(serverFileName)
					&& f1.isDirectory()) {
				this.total += checkSize(serverFileName, DOWNLOAD);
				this.accomplish += checkSize(localFileName, UPLOAD);
			}
		} else {
			if (f2.exists() && f2.isFile()) {
				this.total += f2.length();
				if (this.connection.fileIsExist(serverFileName))
					this.accomplish += f1.getSize();
			} else if (f2.exists() && f2.isDirectory()) {
				this.accomplish += checkSize(serverFileName, DOWNLOAD);
				this.total += checkSize(localFileName, UPLOAD);
			}
		}
	}

	/**
	 * 计算任务的文件总大小
	 * 
	 * @param fileName
	 *            文件路径
	 * @param type
	 *            上传还是下载
	 * @return 文件总大小
	 */
	private long checkSize(String fileName, int type) {
		if (this.cut)
			return 0;
		long ans = 0;
		if (type == DOWNLOAD) {
			String curPath = this.connection.getPath();
			if (this.connection.changeWorkingDirectory(fileName))
				for (FTPFile file : this.connection.showFileList())
					if (file.isFile())
						ans += file.getSize();
					else
						ans += checkSize(fileName + '/' + file.getName(), type);
			this.connection.changeWorkingDirectory(curPath);
		} else {
			File f1 = new File(fileName);
			if (f1.exists())
				for (File file : f1.listFiles())
					if (file.isFile())
						ans += file.length();
					else
						ans += checkSize(
								fileName + File.separator + file.getName(),
								type);
		}
		return ans;
	}

	/**
	 * 复制文件夹
	 * 
	 * @param serverFileName
	 *            服务器路径
	 * @param localFileName
	 *            本地路径
	 * @param type
	 *            上传还是下载
	 * @return 返回是否成功
	 */
	private boolean copyDirectory(String serverFileName, String localFileName,
			int type) {
		if (this.cut)
			return true;
		System.out.println("in copy dir : after check cut");
		boolean flag = true;
		String curPath = this.connection.getPath();
		this.connection.changeWorkingDirectory(serverFileName);
		if (this.withWP == 1)
			this.connection.getWPconn().changeWorkingDirectory(serverFileName);
		System.out.println("in copy dir : begin copy");
		if (type == DOWNLOAD) {
			File tmp = new File(localFileName);
			if (!tmp.exists() || tmp.isFile())
				tmp.mkdirs();
			for (FTPFile file : this.connection.showFileList())
				if (file.isFile())
					flag &= tryCopyFile(serverFileName + '/' + file.getName(),
							localFileName + File.separator + file.getName(),
							DOWNLOAD);
				else
					flag &= this.copyDirectory(
							serverFileName + '/' + file.getName(),
							localFileName + File.separator + file.getName(),
							type);
		} else {
			if (!this.connection.fileIsExist(serverFileName)
					|| this.connection.getFile(serverFileName).isFile()) {
				this.connection.makeDirectory(serverFileName);
				// if (this.withWP == 1)
				// this.connection.getWPconn().makeDirectory(serverFileName);
			}
			for (File file : new File(localFileName).listFiles())
				if (file.isFile()) {
					flag &= tryCopyFile(serverFileName + '/' + file.getName(),
							localFileName + File.separator + file.getName(),
							UPLOAD);
					if (this.withWP == 1)
						this.connection.updateWPFile(localFileName
								+ File.separator + file.getName(), "/" + server
								+ "/" + serverFileName + '/' + file.getName());
				} else
					flag &= this.copyDirectory(
							serverFileName + '/' + file.getName(),
							localFileName + File.separator + file.getName(),
							type);
		}
		this.connection.changeWorkingDirectory(curPath);
		if (this.withWP == 1)
			this.connection.getWPconn().changeWorkingDirectory(curPath);
		System.out.println("in copy dir : end copy");
		return flag;
	}

	/**
	 * 复制文件
	 * 
	 * @param serverFileName
	 *            服务器文件路径
	 * @param localFileName
	 *            本地文件路径
	 * @param type
	 *            上传还是下载
	 * @return 返回是否成功
	 */
	private boolean tryCopyFile(String serverFileName, String localFileName,
			int type) {
		FTPFile f1 = this.connection.getFile(serverFileName);
		File f2 = new File(localFileName);
		if (type == DOWNLOAD) {
			long offset = 0;
			if (f2.exists())
				offset = f2.length();
			else
				offset = 0;
			while (!this.cut) {
				int flag = this.connection.restartDownLoadFile(offset, LENGTH,
						serverFileName, localFileName);
				if (flag == -1)
					return false;
				System.out.println("copyfile : " + flag);
				this.accomplish += flag;
				offset += flag;
				if (flag < LENGTH)
					break;
			}
		} else {
			long offset = 0;
			if (this.connection.fileIsExist(serverFileName))
				offset = f1.getSize();
			else
				offset = 0;
			while (!this.cut) {
				int flag = this.connection.restartUpdateFile(offset, LENGTH,
						localFileName, serverFileName);
				if (flag == -1)
					return false;
				this.accomplish += flag;
				offset += flag;
				if (flag < LENGTH)
					break;
			}
		}
		return true;
	}

}
