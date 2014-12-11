/**
 * 测试LGKLable类
 */
package junit;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import basic.Connection;
import basic.TagManager;

/**
 * 测试TagManager类
 * 
 * @author luyf
 */
@FixMethodOrder(MethodSorters.DEFAULT)
public class TagManagerTest {

	TagManager tm;

	@Before
	public void setUp() throws Exception {
		System.out.println("Set up......");
		Connection con = new Connection();
		con.connection("166.111.206.79");
		boolean rs = con.login("anonymous", "");
		if (!rs)
			fail("Error Login!");
		tm = new TagManager(con);
	}

	@After
	public void tearDown() throws Exception {

	}

	@Test
	public void testaddTag() {
		System.out.println("Testing addTag()......");
		ArrayList<String> newtags = new ArrayList<String>();
		newtags.add("ljb");
		newtags.add("xujie");
		newtags.add("xujie");
		newtags.add("tff");
		tm.addTag("fucklgk.txt", newtags);
		ArrayList<String> rst = tm.showTag("fucklgk.txt");
		for (String tmp : rst) {
			System.out.println(tmp);
		}
		System.out.println(rst.size());
		Assert.assertTrue(rst.size() == 4);
		for (String tmp : rst) {
			System.out.println(tmp);
		}
	}

	@Test
	public void testdelTag() {
		System.out.println("Testing delTag()......");
		ArrayList<String> rst = tm.showTag("fucklgk.txt");
		for (String tmp : rst) {
			System.out.println(tmp);
		}
		tm.delTag("fucklgk.txt", "ljb");
		rst = tm.showTag("fucklgk.txt");
		for (String tmp : rst) {
			System.out.println(tmp);
		}
		System.out.println(rst.size());
		Assert.assertTrue(rst.size() == 3);
	}

	@Test
	public void testdelFile() {
		System.out.println("Testing delFile()......");
		tm.delFile("fucklgk.txt");
		ArrayList<String> rst = tm.showTag("fucklgk.txt");
		Assert.assertTrue(rst.size() == 0);
	}

}
