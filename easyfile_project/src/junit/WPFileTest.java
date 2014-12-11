/**
 * 测试WPFile类
 */
package junit;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import basic.WPFile;

/**
 * 测试WPFile类
 * 
 * @author luyf
 */
public class WPFileTest {

	WPFile wp = new WPFile();

	@Before
	public void setUp() throws Exception {

	}

	@After
	public void tearDown() throws Exception {

	}

	@Test
	public void testIsDirectory() {
		wp.setDirectory(true);
		Assert.assertTrue(wp.isDirectory());
	}

	@Test
	public void testIsFile() {
		wp.setFile(true);
		Assert.assertTrue(wp.isFile());
	}

	@Test
	public void testSetSize() {
		wp.setSize(1000);
		Assert.assertTrue(wp.getSize() == 1000);
	}

	@Test
	public void testSetFile() {
		wp.setFile(false);
		Assert.assertTrue(!wp.isFile());
	}

	@Test
	public void testSetDirectory() {
		wp.setDirectory(false);
		Assert.assertTrue(!wp.isDirectory());
	}

	@Test
	public void testSetNameString() {
		wp.setName("xj");
		Assert.assertTrue(wp.getName().equals("xj"));
	}

}
