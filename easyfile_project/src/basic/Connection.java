package basic;

import java.io.*;
import java.util.*;
import java.net.SocketException;

import org.apache.commons.net.ftp.*;

/**
 * ftp连接器，负责有关ftp的所有操作
 * @author lai
 *
 */
public class Connection {
	/**
	 * ftp链接类
	 */
	private FTPClient ftp=new FTPClient();
	
	public Connection(){}
	
	/**
	 * 建立一个ftp链接
	 * @param server 链接目标地址
	 * @return 操作是否成功
	 */
	public boolean connection(String server)
	{
		int port = 21;  
		try 
		{
			ftp.connect(server, port);
			ftp.setControlEncoding("GBK");
			ftp.setFileType(FTP.BINARY_FILE_TYPE); 
			ftp.enterLocalPassiveMode(); 
		} catch (SocketException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} 
		int reply = ftp.getReplyCode(); 
		return (FTPReply.isPositiveCompletion(reply));  	
	}
	
	/**
	 * 用户登录
	 * @param userName 用户名
	 * @param password 用户密码
	 * @return 操作是否成功
	 */
	public boolean login(String userName,String password)
	{
		boolean result = false;
		try 
		{
			result = (ftp.login(userName, password));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * 用户登出
	 * @return 操作是否成功
	 */
	public boolean logout()
	{
		boolean result = false;
		try 
		{
			result = ftp.logout();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * 得到ftp服务器当前文件路径
	 * @return 当前路径
	 */
	public String getPath() 
	{
		String path = "";
		try 
		{
			path = ftp.printWorkingDirectory();
			path = new String(path.getBytes("iso-8859-1"), "GBK");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return path;
	}
	
	/**
	 * ftp文件路径返回父目录
	 * @return 操作是否成功
	 */
	public boolean changetoParentDirectory()
	{
		boolean res = false;
		try 
		{
			res = ftp.changeToParentDirectory();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return res;
	}
	
	/**
	 * ftp跳转到指定文件目录
	 * @param path 
	 * @return 操作是否成功
	 */
	public boolean changeWorkingDirectory(String path)
	{
		boolean res = false;
		try 
		{
			res = ftp.changeWorkingDirectory(new String(path.getBytes("GBK"),"iso-8859-1"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return res;
	}
	
	/**
	 * 下载ftp指定文件
	 * @param serverFileName ftp服务器文件名 
	 * @param localFileName	本地文件名
	 * @return 操作是否成功
	 */
	public boolean downLoadFile(String serverFileName, String localFileName)
	{
		boolean result = false;
		try 
		{
			FileOutputStream output = new FileOutputStream(localFileName);
			result = ftp.retrieveFile(new String(serverFileName.getBytes("GBK"),"iso-8859-1"), output);
			output.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * 上传指定文件
	 * @param localFileName 本地文件名
	 * @param serverFileName ftp服务器文件名
	 * @return 操作是否成功
	 */
	public boolean updateFile(String localFileName, String serverFileName)
	{
		boolean result = false;
		try 
		{
			FileInputStream input = new FileInputStream(localFileName);
			result = ftp.storeFile(new String(serverFileName.getBytes("GBK"),"iso-8859-1"), input);
			input.close();
		} catch (Exception e) {
			e.printStackTrace();
		}	
		return result;
	}
	
	/**
	 * 删除服务器端指定文件
	 * @param fileName 服务器端文件名
	 * @return 操作是否成功
	 */
	public boolean deleteServerFile(String fileName)
	{
		System.out.println("fuckxjfuckxjfuckxjfuckxjfuckxjfuckxj");
		boolean result = false;
		try 
		{
			result = ftp.deleteFile(new String(fileName.getBytes("GBK"),"iso-8859-1"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * 重命名一个服务器端文件
	 * @param fileName 原文件名
	 * @param newFileName 新文件名
	 * @return 操作是否成功
	 */
	public boolean rename(String fileName, String newFileName)
	{
		boolean res = false;
		try 
		{
			res = ftp.rename(new String(fileName.getBytes("GBK"),"iso-8859-1"), new String(newFileName.getBytes("GBK"),"iso-8859-1"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return res;
	}
	
	/**
	 * 删除服务器端一个文件夹
	 * @param path	文件夹的路径
	 * @return	操作是否成功
	 */
	public boolean removeDirectory(String path)
	{
		boolean res = false;
		try 
		{
			res = ftp.removeDirectory(new String(path.getBytes("GBK"),"iso-8859-1"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return res;
	}
	
	/**
	 * 在ftp客户端新建一个文件夹
	 * @param path	文件夹路径
	 * @return	操作是否陈宫
	 */
	public boolean makeDirectory(String path)
	{
		boolean res = false;
		try 
		{
			res = ftp.makeDirectory(new String(path.getBytes("GBK"),"iso-8859-1"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return res;
	}
	
	/**
	 * 返回当前目录的文件列表
	 * @return	文件列表
	 */
	public List<FTPFile> showFileList()
	{
		List<FTPFile> list = new ArrayList<FTPFile>();
		try 
		{
			FTPFile[] files = ftp.listFiles();
			for (int i = 0; i< files.length; i++)
			{
				list.add(files[i]);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	/**
	 * 下载服务器文件到本地文件，数据位置从offset位置开始length个bytes
	 * @param offset 数据开始位置
	 * @param length 下载长度
	 * @param serverFileName 服务器文件名
	 * @param localFileName 本地文件名
	 * @return 操作是否成功
	 */
	public int restartDownLoadFile(long offset, int length, String serverFileName, String localFileName)
	{
		System.out.print("download\t"+offset+"\t"+length);
		int c = -1;
		try 
		{
			ftp.setRestartOffset(offset);
			FileOutputStream output = new FileOutputStream(localFileName, true);
			InputStream input = ftp.retrieveFileStream(new String(serverFileName.getBytes("GBK"),"iso-8859-1"));
			byte[] bytes= new byte[length];
			for (int i=0;i<length;i++){
				int ib = input.read();
				if (ib == -1) break;
				else bytes[i] = (byte)ib;
				c = i + 1;
			}
//			c = input.read(bytes);
			if (c != -1)
			{
				output.write(bytes, 0, c);
			}
			output.close();
			input.close();
			ftp.completePendingCommand();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return c;
	}
	
	/**
	 * 上传服务器文件到本地文件，数据位置从offset位置开始length个bytes
	 * @param offset 数据开始位置
	 * @param length 下载长度
	 * @param serverFileName 服务器文件名
	 * @param localFileName 本地文件名
	 * @return 操作是否成功
	 */
	public int restartUpdateFile(long offset, int length, String localFileName, String serverFileName)
	{
		int c = -1;
		try 
		{
			ftp.setRestartOffset(offset);
			RandomAccessFile input = new RandomAccessFile(localFileName,"r");
			byte[] tmp = serverFileName.getBytes("GBK");
			String tmp1 = new String(tmp,"iso-8859-1");
			OutputStream output = ftp.appendFileStream(tmp1);
			/*if (output == null)
			{
				System.out.println("in restart : " + tmp1);
				output = ftp.storeFileStream(tmp1);
			}*/
			System.out.println("inprocess:"+isConnection());
			input.seek(offset);
			byte[] bytes = new byte[length];
			c = input.read(bytes);
			System.out.println("in restart : " + offset);
			if (c != -1)
			{
				System.out.println(output);
				output.write(bytes, 0, c);
			}
			output.close();
			input.close();
			ftp.completePendingCommand();
		} catch (Exception e) {
			e.printStackTrace();
		}		
		return c;
	}
	
	/**
	 * 关闭连接
	 */
	public void close()
	{
		try 
		{
			if (isConnection())
				ftp.disconnect();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * ̬返回ftp是否连接
	 * @return ftp是否连接
	 */
	public boolean isConnection()
	{
		if (ftp==null) return false;
		int reply = ftp.getReplyCode();      
		return (FTPReply.isPositiveCompletion(reply));   			
	}
	
	/**
	 * 返回ftp服务器上是否有某个文件
	 * @param filename
	 * @return
	 */
	public boolean fileIsExist(String fileName)
	{
		return this.getFile(fileName) != null;
	}
	
	/**
	 * 返回ftp中的指定文件
	 * @param filename 文件路径名
	 * @return ftpfile文件类
	 */
	public FTPFile getFile(String fileName)
	{
//		System.out.println("getFile-----------------");
		String path = this.getPath();
		if (path.equals("/"))
			path = "";
		if (fileName.charAt(1) == '/')
			fileName = fileName.substring(1);
		FTPFile result = null;
		try 
		{
			FTPFile[] files = ftp.listFiles((new String(path.getBytes("GBK"),"iso-8859-1")));
			for (FTPFile file : files)
			{
//				System.out.println(new String(fileName.getBytes("GBK"),"iso-8859-1"));
//				System.out.println(new String((new String((path+"/"+file.getName()).getBytes("GBK"),"iso-8859-1"))));
				if (new String(fileName.getBytes("GBK"),"iso-8859-1").equals(
						new String((new String((path+"/"+file.getName()).getBytes("GBK"),"iso-8859-1")))))
					return file;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;		
	}
}
