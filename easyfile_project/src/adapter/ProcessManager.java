package adapter;

import java.util.List;

class ProcessManager {
	/**
	 * 用户名字，用户密码
	 */
	String userName, password;
	private final int CONNUM = 5;
	private String server;
	private int busyNum = 0, vacantNum = 0;
	private int busyCon[] = new int[CONNUM], vacantCon[] = new int[CONNUM];
	private ProcessThread threads[] = new ProcessThread[CONNUM];

	/**
	 * 任务管理器
	 * 
	 * @param userName
	 *            用户名字
	 * @param password
	 *            用户密码
	 * @param server
	 *            服务器名字
	 */
	ProcessManager(String userName, String password, String server) {
		this.server = server;
		this.vacantNum = CONNUM;
		this.userName = userName;
		this.password = password;
		for (int i = 0; i < this.vacantNum; ++i) {
			this.vacantCon[i] = i;
		}
	}

	/**
	 * 用户登出
	 * 
	 * @return 返回是否登出成功
	 */
	public boolean logout() {
		boolean flag = true;
		for (int i = 0; i < this.busyNum; ++i) {
			flag &= threads[i].connection.logout();
			threads[i].connection.close();
		}
		return flag;
	}

	/**
	 * 释放已完成的任务
	 * 
	 * @param num
	 *            任务序号
	 */
	public void free(int num) {
		synchronized (this) {
			for (int i = 0; i < busyNum; ++i)
				if (busyCon[i] == num) {
					for (int j = i; j < busyNum - 1; ++j)
						busyCon[j] = busyCon[j + 1];
					vacantCon[vacantNum++] = num;
					--busyNum;
					break;
				}
			notifyAll();
		}
	}

	/**
	 * 检查是否有可用资源
	 * 
	 * @return 返回是否有可用线程
	 */
	public synchronized boolean vacant() {
		return vacantNum > 0;
	}

	/**
	 * 新增任务
	 * 
	 * @param serverFileName
	 *            服务器文件路径
	 * @param localFileName
	 *            本地文件路径
	 * @param type
	 *            上传或下载的标号
	 * @param path
	 *            服务器文件路径父目录
	 * @param withWP
	 *            是否和网盘同步
	 */
	public void addProcess(List<String> serverFileName,
			List<String> localFileName, int type, String path, final int withWP) {
		int num = vacantCon[--vacantNum];
		busyCon[busyNum++] = num;
		threads[num] = new ProcessThread(this, userName, password, num,
				serverFileName, localFileName, type, server, path, withWP);
		threads[num].start();
	}

	/**
	 * 中断任务
	 * 
	 * @param serverFileName
	 *            服务器文件路径
	 * @param localFileName
	 *            本地文件路径
	 */
	public void cutProcess(List<String> serverFileName,
			List<String> localFileName) {
		for (int i = 0; i < busyNum; ++i)
			if (threads[busyCon[i]].serverFileName.get(0).equals(
					serverFileName.get(0))
					&& threads[busyCon[i]].localFileName.get(0).equals(
							localFileName.get(0))) {
				threads[busyCon[i]].cut = true;
				break;
			}
	}

	/**
	 * 得到任务完成百分比
	 * 
	 * @param serverFileName
	 *            服务器文件路径
	 * @param localFileName
	 *            本地文件路径
	 * @return 任务完成百分比
	 */
	public double getAccomplishRate(List<String> serverFileName,
			List<String> localFileName) {
		for (int i = 0; i < busyNum; ++i)
			if (threads[busyCon[i]].serverFileName.get(0).equals(
					serverFileName.get(0))
					&& threads[busyCon[i]].localFileName.get(0).equals(
							localFileName.get(0)))
				return (double) threads[busyCon[i]].accomplish
						/ threads[busyCon[i]].total;
		// else System.out.println(threads[busyCon[i]].serverFileName.get(0) +
		// "is not equal to " + serverFileName.get(0));
		return 1;
	}

}
