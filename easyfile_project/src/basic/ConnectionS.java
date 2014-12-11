package basic;

/**
 * 升级版connection，在完成ftp端操作同时完成网盘端操作
 * @author lai
 *
 */
public class ConnectionS extends Connection{
	/**
	 * 百度网盘链接
	 */
	private BaiduConnection WPconn = null;
	
	public BaiduConnection getWPconn() {
		return WPconn;
	}
	
	public void setWPconn(BaiduConnection wPconn) {
		WPconn = wPconn;
	}
	
	/**
	 * 当flag为true时，删除网盘文件
	 * @param File 文件路径
	 * @param flag 是否要删除网盘文件
	 * @return 操作是否成功
	 */
	public boolean deleteServerFile(String File, boolean flag)
	{
		if (WPconn != null)
		{
			if (flag)
			{
				WPFile wpFile = WPconn.getFile(File);
				if (wpFile.isFile() != false || wpFile.isDirectory() != false)
					WPconn.deleteServerFile(File);
			}
		}
		return super.deleteServerFile(File);
	}
	
	/**
	 * 当flag为true时，向网盘上传文件
	 * @param localFileName 本地文件名
	 * @param serverFileName 服务器端文件名
	 * @return 操作是否成功
	 */
	public boolean updateWPFile(String localFileName, String serverFileName)
	{
		if (WPconn != null)
		{
			WPconn.updateFile(localFileName, serverFileName);
			return true;
		}
		else return false;
	}
	
	/**
	 * 当flag为true时，在网盘上新建文件夹
	 * @param path 文件夹路径
	 * @param flag 是否网盘同步
	 * @return 操作是否成功
	 */
	public boolean makeDirectory(String path, boolean flag)
	{
		if (WPconn != null)
		{
			if (flag)
				WPconn.makeDirectory(path);
		}
		return super.makeDirectory(path);
	}
	
	/**
	 * 当flag为true时，从网盘删除相应文件夹
	 * @param path 文件路径
	 * @param flag 是否网盘同步
	 * @return 操作是否成功
	 */
	public boolean removeDirectory(String path, boolean flag)
	{
		if (WPconn != null)
		{
			if (flag)
				WPconn.removeDirectory(path);
		}
		return super.removeDirectory(path);
	}
	
	/**
	 * 重命名网盘文件的时候，如果此文件在网盘上，那么也重命名此文件。
	 */
	public boolean rename(String oldFileName, String newFileName, String server)
	{
		if (oldFileName.charAt(1) == '/')
			oldFileName = oldFileName.substring(1);
		if (newFileName.charAt(1) == '/')
			newFileName = newFileName.substring(1);
		try
		{
			if (WPconn != null)
			{
//				System.out.println("fuck"+oldFileName+"///////"+newFileName);
				WPFile wpFile = WPconn.getFile((oldFileName));
				if (wpFile.isFile() != false || wpFile.isDirectory() != false)
				{
	//				System.out.println("fuckfuck");
					WPconn.deleteServerFile(oldFileName);
					super.downLoadFile(oldFileName, newFileName);
					WPconn.updateFile(newFileName, "/"+server+newFileName);
				}
			}
		} catch(Exception e){
			System.out.println(e);
		}
		return super.rename(oldFileName, newFileName);
	}
}
