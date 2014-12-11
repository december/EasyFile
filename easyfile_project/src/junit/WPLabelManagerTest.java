/**
 * 测试WPFile类
 */
package junit;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import basic.BaiduConnection;
import basic.WPLabelManager;

/**
 * 测试WPLabelManager类
 * 
 * @author luyf
 */
@FixMethodOrder(MethodSorters.DEFAULT)
public class WPLabelManagerTest {

	WPLabelManager wplm;
	BaiduConnection bc = new BaiduConnection();

	@Before
	public void setUp() throws Exception {
		if (!bc.connection("166.111.206.79"))
			fail("Connection failed!");
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testWPLabelManager() {
		wplm = new WPLabelManager(bc);
		assertNotNull(wplm);
	}

	@Test
	public void testInit() {
		wplm = new WPLabelManager(bc);
		assertTrue(wplm.getFileLabel().size() > 0);
		assertTrue(wplm.getLabelList().size() > 0);
	}

	@Test
	public void testUpload() {
		wplm = new WPLabelManager(bc);
		wplm.upload();
	}

}
