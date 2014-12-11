/**
 * 测试BaiduConnection类
 */
package junit;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.List;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import basic.BaiduConnection;
import basic.WPFile;

/**
 * 测试BaiduConnection类
 * 
 * @author luyf
 */
public class BaiduConnectionTest {

	BaiduConnection baidu = new BaiduConnection();
	String testFileName = "fucktff.txt";

	@Before
	public void setUp() throws Exception {
		baidu.connection("166.111.206.79");
		System.out.println(baidu.getPath());
		File temp = new File(testFileName);
		try {
			if (!temp.createNewFile())
				return;
			Writer writer = new OutputStreamWriter(new FileOutputStream(temp));
			writer.write("fuuuuuuuuuuuuuuuuuuck");
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		temp.deleteOnExit();
	}

	@After
	public void tearDown() throws Exception {

	}

	@Test
	public void testUpdateFile() {
		baidu.updateFile(testFileName, "/" + testFileName);
	}

	@Test
	public void testDownLoadFile() {
		baidu.updateFile(testFileName, "/" + testFileName);
		baidu.downLoadFile("/" + testFileName, "test.txt");
	}

	@Test
	public void testDeleteServerFile() {
		baidu.updateFile(testFileName, "/" + testFileName);
		baidu.deleteServerFile("//" + testFileName);
	}

	@Test
	public void testRemoveDirectory() {
		baidu.updateFile("config.txt", "/config.txt");
		baidu.removeDirectory("/config.txt");
	}

	@Test
	public void testChangeWorkingDirectory() {
		String path = "/166.111.206.79/";
		baidu.changeWorkingDirectory(path);
		Assert.assertTrue(baidu.getPath().equals(path));
	}
	
	@Test
	public void testCreateConfig() {
		boolean result = baidu.createConfig();
		Assert.assertTrue(result);
	}

	@Test
	public void testShowFileList() {
		List<WPFile> wp = baidu.showFileList();
		System.out.println(wp.get(0).getName());
		System.out.println(wp.get(1).getSize());
		System.out.println(wp.get(2).isDirectory());
		System.out.println(wp.get(3).isFile());
	}

	@Test
	public void testGetFile() {
		WPFile wp = baidu.getFile("/config.txt");
		System.out.println(wp.getSize());
		Assert.assertTrue(!wp.isDirectory());
	}

	@Test
	public void testMakeDirectory() {
		boolean result = baidu.makeDirectory("/NewFolder");
		Assert.assertTrue(result);
	}
}
