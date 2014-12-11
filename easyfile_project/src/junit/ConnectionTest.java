/**
 * 测试Connection类
 */
package junit;

import static org.junit.Assert.*;

import java.util.List;

import org.apache.commons.net.ftp.FTPFile;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.sun.jndi.url.iiopname.iiopnameURLContextFactory;

import basic.Connection;

/**
 * 测试Connection类
 * 
 * @author luyf
 */
public class ConnectionTest {

	Connection con = new Connection();
	String testLocal = "test.txt";
	String testServer = "fucklgk.txt";
	String testDir = "/emptyTest";
	String tempDir = "/Temp";
	String confName = "config.txt";

	@Before
	public void setUp() throws Exception {
		con.connection("166.111.206.79");
		boolean rs = con.login("anonymous", "");
		if (!rs)
			fail("Error Login!");
	}

	@After
	public void tearDown() throws Exception {

	}

	@Test
	public void testChangeWorkingDirectory() {
		String curpath = con.getPath();
		String test1 = "/thispathdoesnotexist";
		Assert.assertTrue(!con.changeWorkingDirectory(test1));
		System.out.println(con.getPath());
		Assert.assertTrue(curpath.equals(con.getPath()));
		String test2 = "/fucklgk";
		con.changeWorkingDirectory(test2);
		Assert.assertTrue(test2.equals(con.getPath()));
		con.changeWorkingDirectory("fuck");
		Assert.assertTrue(!(test2 + "/fuck").equals(con.getPath()));
		con.changetoParentDirectory();
		Assert.assertTrue(!test2.equals(con.getPath()));
	}

	@Test
	public void testDownLoadFile() {
		boolean result = con.downLoadFile(testServer, testLocal);
		assertTrue(result);
	}

	@Test
	public void testUpdateFile() {
		boolean result = con.updateFile(confName, "newFile.txt");
		assertTrue(result);
	}

	@Test
	public void testDeleteServerFile() {
		boolean result = con.deleteServerFile(testServer);
		assertTrue(result);
		boolean sign = con.fileIsExist(testServer);
		assertFalse(sign);
		con.updateFile(confName, testServer);
	}

	@Test
	public void testRename() {
		String newName = "new" + testServer;
		con.rename(testServer, newName);
		assertFalse(con.fileIsExist(testServer));
		assertTrue(!con.fileIsExist(newName));
		con.rename(newName, testServer);
		assertTrue(!con.fileIsExist(testServer));
		assertFalse(con.fileIsExist(newName));
	}

	@Test
	public void testRemoveDirectory() {
		con.removeDirectory(testDir);
		assertFalse(con.fileIsExist(testDir));
		con.makeDirectory(testDir);
	}

	@Test
	public void testMakeDirectory() {
		con.removeDirectory(tempDir);
		con.makeDirectory(tempDir);
		assertTrue(con.fileIsExist(tempDir));
		con.removeDirectory(tempDir);
	}

	
	 @Test public void testRestartDownLoadFile() {
		 con.restartDownLoadFile(0, 10000, testServer, "tempLocal.txt");
	 }
	 
	 @Test public void testRestartUpdateFile() {
		 con.restartUpdateFile(0, 10000, confName, "tempServer.txt");
	 }
	
	@Test
	public void testIsConnection() {
		boolean result = con.isConnection();
		Assert.assertTrue(result);
	}
	
	@Test
	public void testClose() {
		con.close();
	}

	@Test
	public void testLogout() {
		boolean result = con.logout();
		Assert.assertTrue(result);
	}
	
	@Test
	public void testShowFileList() {
		List<FTPFile> temp = con.showFileList();
		for (int i = 0;i < temp.size();i++)
			System.out.println(temp.get(i));
	}
	
}
