/**
 * 实现百度网盘底层操作的类
 */
package basic;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.baidu.inf.iis.bcs.BaiduBCS;
import com.baidu.inf.iis.bcs.auth.BCSCredentials;
import com.baidu.inf.iis.bcs.model.ObjectListing;
import com.baidu.inf.iis.bcs.model.ObjectMetadata;
import com.baidu.inf.iis.bcs.model.ObjectSummary;
import com.baidu.inf.iis.bcs.request.GetObjectRequest;
import com.baidu.inf.iis.bcs.request.ListObjectRequest;
import com.baidu.inf.iis.bcs.request.PutObjectRequest;
import com.baidu.inf.iis.bcs.response.BaiduBCSResponse;


/**
 * 实现百度网盘底层操作的类
 * 
 * @author luyf
 *
 */
public class BaiduConnection {
	// ----------------------------------------\
	private static final Log log = LogFactory.getLog(BaiduConnection.class);
	private String host = "bcs.duapp.com"; //
	private String accessKey = "2hrNKDqG0EPU6YNR4rbLGYkr"; // key
	private String secretKey = "CPpXgdvr6jvN3t1WcGUyc2WrrUTGrFBd"; // key
	private String bucket = "easyfile"; // bucket name
	private String path = "/";
	private String server = "";
	private BaiduBCS baiduBCS;
	final String configName = "config.txt";
	final String local = "127.0.0.1";

	public BaiduConnection() {
	}

	/**
	 * 向百度网盘建立连接
	 * 
	 * @param servername 服务器名称（可以是IP地址）
	 * @return 是否能成功连接到网盘对应区域时返回
	 */

	public boolean createConfig() {
		File temp = new File(configName);
		System.out.println("Creating folders...");
		try {
			temp.createNewFile();
			Writer writer = new OutputStreamWriter(new FileOutputStream(temp));
			writer.write("fucktff");
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		temp.deleteOnExit();
		return true;
	}

	public boolean connection(String serverName) {
		BCSCredentials credentials = new BCSCredentials(accessKey, secretKey);
		baiduBCS = new BaiduBCS(credentials, host);
		baiduBCS.setDefaultEncoding("GBK");
		/*if (serverName.equals(local))
			serverName += "X";
		if (!createConfig())
			return false;*/
		System.out.println("Uploading folders...");
		updateFile(configName, path + serverName + "/" + configName);
		System.out.println("Deleting folders...");
		path = path + serverName + "/";
		System.out.println("Path changed to " + path);
		server = serverName;
		return true;
	}

	/**
	 * 获取网盘当前工作路径
	 * 
	 * @return 当前路径
	 */
	public String getPath() {
		return path;
	}

	/**
	 * 从百度云下载文件
	 * 
	 * @param serverFileName 服务器上文件名称（绝对路径）
	 * @param localFileName 本地文件名称（相对路径）
	 * @return always true
	 */
	
	public boolean downLoadFile(String serverFileName, String localFileName) {
		GetObjectRequest getObjectRequest = new GetObjectRequest(bucket,
				serverFileName);
		baiduBCS.getObject(getObjectRequest, new File(localFileName));
		return true;
	}

	/**
	 * 向百度云上传文件
	 * 
	 * @param serverFileName 服务器上文件名称（绝对路径）
	 * @param localFileName 本地文件名称（相对路径）
	 * @return always true
	 */
	public boolean updateFile(String localFileName, String serverFileName) {
		File desFile = new File(localFileName);
		System.out.println(desFile.isDirectory());
		PutObjectRequest request = new PutObjectRequest(bucket, serverFileName,
				desFile);
		ObjectMetadata metadata = new ObjectMetadata();
		request.setMetadata(metadata);
		BaiduBCSResponse<ObjectMetadata> response = baiduBCS.putObject(request);
		ObjectMetadata objectMetadata = response.getResult();
		log.info("x-bs-request-id: " + response.getRequestId());
		log.info(objectMetadata);
		System.out.println("Upload finished.");
		return true;
	}

	/**
	 * 在网盘上新建一个文件夹
	 * 
	 * @param absPath 新文件夹对应的绝对路径
	 * @return 操作是否成功
	 */
	public boolean makeDirectory(String absPath) {
		absPath = trans(absPath);
		/*if (!createConfig())
			return false;*/
		updateFile(configName, absPath + configName);
		deleteServerFile(absPath + configName);
		return true;
	}

	/**
	 * 删除服务器上的文件
	 * 
	 * @param fileName 服务器上文件名称（绝对路径）
	 * @return always true
	 */
	public boolean deleteServerFile(String fileName)
	{
		baiduBCS.deleteObject(bucket, path + fileName).getResult();
		return true;
	}

	/**
	 * 删除服务器上的文件夹
	 * 
	 * @param fileName 目标文件夹对应绝对路径
	 * @return always true
	 */
	public boolean removeDirectory(String path) {
		path = trans(path);
		baiduBCS.deleteObject(bucket, path).getResult();
		return true;
	}

	/**
	 * 转换路径格式
	 * 
	 * @param str 顶端传的路径
	 * @return 符合格式要求的路径
	 */
	private String trans(String str) {
		while (str.length() > 1 && str.charAt(1) == '/')
			str = str.substring(1);

		return str;
	}

	/**
	 * 切换当前路径
	 * 
	 * @param 目标路径（绝对路径）
	 * @return always true
	 */
	public boolean changeWorkingDirectory(String path) {
		path = trans(path);
		if (!path.endsWith("/"))
			path += "/";
		this.path = path;
		return true;
	}

	/**
	 * 获取当前路径下的文件列表
	 * 
	 * @return 当前路径下的文件列表（WPFile格式）
	 */
	public List<WPFile> showFileList() {
		if (path.equals("/"))
			path = path + server + "/";
		List<WPFile> list = new ArrayList<WPFile>();
		ListObjectRequest listObjectRequest = new ListObjectRequest(bucket);
		listObjectRequest.setPrefix(path);
		System.out.println("Baidu path = " + path);
		int depth = path.split("/").length;
		BaiduBCSResponse<ObjectListing> response = baiduBCS
				.listObject(listObjectRequest);
		Set<String> folderName = new HashSet<String>();
		for (ObjectSummary os : response.getResult().getObjectSummaries()) {
			WPFile wp = new WPFile();
			String name = os.getName();
			String[] temp = name.split("/");
			if (temp.length == depth + 1) {
				wp.setName(temp[depth]);
				wp.setSize(os.getSize());
				wp.setDirectory(false);
				wp.setFile(true);
			}
			if (temp.length > depth + 1)
				folderName.add(temp[depth]);
			//if (temp[depth] == null || temp[depth].equals(""))
			//	continue;
			list.add(wp);
		}
		for (String s : folderName) {
			WPFile wp = new WPFile();
			//if (s == null || s.equals(""))
			//	continue;
			wp.setName(s);
			wp.setSize(0);
			wp.setDirectory(true);
			wp.setFile(false);
			list.add(wp);
		}
		return list;
	}

	/**
	 * 获取特定文件的信息
	 * 
	 * @param filename 目标文件名（绝对路径）
	 * @return 目标文件的信息（WPFile格式）
	 */
	public WPFile getFile(String fileName) {
		fileName = trans(fileName);
		WPFile wp = new WPFile();
		ListObjectRequest listObjectRequest = new ListObjectRequest(bucket);
		listObjectRequest.setPrefix(path+fileName);
		BaiduBCSResponse<ObjectListing> response = baiduBCS.listObject(listObjectRequest);
		if (response.getResult().getObjectSummaries().size() < 1)
		{
			System.out.println("BaiduCloud:No file found!");
			wp.setFile(false);
			wp.setDirectory(false);
			return wp;
		}
		if (response.getResult().getObjectSummaries().size() > 1) {
			System.out.println("BaiduCloud:Error in listing files!");
			wp.setFile(false);
			wp.setDirectory(false);
			return wp;
		}
		for (ObjectSummary os : response.getResult().getObjectSummaries()) {
			wp.setName(os.getName());
			wp.setSize(os.getSize());
			wp.setDirectory(os.isDir());
			wp.setFile(!os.isDir());
		}
		return wp;
	}

}
