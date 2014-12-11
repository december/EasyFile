package basic;

import org.apache.commons.net.ftp.*;

/**
 * 网盘文件类，继承自FTPFile类，作为adpater连接网盘文件系统与前端。
 * @author lai
 *
 */
public class WPFile extends FTPFile{
	private boolean isFile;
	private boolean isDirectory;
	private String name;
	private long size;
	public WPFile(){}
	
	public boolean isFile() {
		return isFile;
	}
	
	public void setFile(boolean isFile) {
		this.isFile = isFile;
	}
	
	public boolean isDirectory() {
		return isDirectory;
	}
	
	public void setDirectory(boolean isDirectory) {
		this.isDirectory = isDirectory;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public long getSize() {
		return size;
	}
	
	public void setSize(long size) {
		this.size = size;
	}
}
