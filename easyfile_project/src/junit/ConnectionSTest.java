/**
 * 测试ConnectionS类
 */
package junit;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.Assert;

import basic.BaiduConnection;
import basic.ConnectionS;

/**
 * 测试ConnectionS类
 * 
 * @author luyf
 */
public class ConnectionSTest {

	ConnectionS cs = new ConnectionS();
	BaiduConnection bc = new BaiduConnection();
	String testServer = "/fucktff.txt";
	String confName = "config.txt";
	String newName = "/temp.txt";
	String tempDir = "/temp";
	String testDir = "/fucktff";

	@Before
	public void setUp() throws Exception {
		cs.connection("166.111.206.79");
		if (!bc.connection("166.111.206.79"))
			fail("Connection failed!");
		cs.setWPconn(bc);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testRename() {
		cs.rename(testServer, testServer);
	}

	@Test
	public void testSetWPconn() {
		cs.setWPconn(null);
		assertNull(cs.getWPconn());
		cs.setWPconn(bc);
		assertNotNull(cs.getWPconn());
	}

	@Test
	public void testDeleteServerFileStringBoolean() {
		cs.deleteServerFile(newName, true);
		cs.updateFile(confName, newName);
	}

	@Test
	public void testUpdateWPFile() {
		cs.updateWPFile(confName, newName);
	}

	@Test
	public void testMakeDirectoryStringBoolean() {
		cs.makeDirectory(tempDir, true);
	}

	@Test
	public void testRemoveDirectoryStringBoolean() {
		cs.removeDirectory(newName, true);
		cs.updateFile(confName, newName);
	}

}
