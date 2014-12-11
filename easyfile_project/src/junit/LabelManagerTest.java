/**
 * 测试LableManager类
 */
package junit;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import basic.BaiduConnection;
import basic.Connection;
import basic.LGKLabel;
import basic.LabelManager;


/**
 * 测试LableManager类
 * 
 * @author luyf
 */
@FixMethodOrder(MethodSorters.DEFAULT)
public class LabelManagerTest {

	LabelManager lm = new LabelManager();

	@Before
	public void setUp() throws Exception {
		System.out.println("Set up......");
		Connection con = new Connection();
		con.connection("166.111.206.79");
		boolean rs = con.login("anonymous", "");
		if (!rs)
			fail("Error Login!");
		lm = new LabelManager(con);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testAddLabelStringArrayListOfLGKLabel() {
		System.out.println("Test NO.1......");
		String fileName = "fucklgk.txt";
		ArrayList<LGKLabel> newlabel = new ArrayList<LGKLabel>();
		newlabel.add(new LGKLabel("name", 0, "ljb"));
		newlabel.add(new LGKLabel("time", 0, "today"));
		newlabel.add(new LGKLabel("number", 0, "1"));
		lm.addLabel(fileName, newlabel);
		HashMap<String, ArrayList<LGKLabel>> hm = lm.getFileLabel();
		System.out.println(hm.get(fileName).size());
		Assert.assertTrue(hm.get(fileName).size() == 0);
		for (LGKLabel tmp : hm.get(fileName)) {
			if (tmp.getKey().equals("name"))
				Assert.assertTrue(tmp.getValue().equals("ljb"));
			if (tmp.getKey().equals("time"))
				Assert.assertTrue(tmp.getValue().equals("today"));
			if (tmp.getKey().equals("number"))
				Assert.assertTrue(tmp.getValue().equals("1"));
		}

	}

	@Test
	public void testDeleteLabelStringLGKLabel() {
		System.out.println("Test NO.2......");
		String fileName = "fucklgk.txt";
		LGKLabel temp = new LGKLabel("time", 0, "today");
		lm.deleteLabel(fileName, temp);
		HashMap<String, ArrayList<LGKLabel>> hm = lm.getFileLabel();
		ArrayList<LGKLabel> labels = hm.get(fileName);
		//System.out.println(labels.size());
		//Assert.assertTrue(labels.size() == 0);
		for (LGKLabel tmp : labels) {
			System.out.println(tmp.getKey());
			Assert.assertTrue(!(tmp.getKey() != "time"));
		}
	}

	@Test
	public void testDeleteLabelString() {
		System.out.println("Test NO.3......");
		String fileName = "fucklgk.txt";
		lm.deleteLabel(fileName);
		HashMap<String, ArrayList<LGKLabel>> hm = lm.getFileLabel();
		Assert.assertTrue(hm.get(fileName) == null);
	}

	@Test
	public void testAddNewKindLabel() {
		System.out.println("Test NO.4......");
		LGKLabel lgk = new LGKLabel("shape", 0, "circle");
		int before = lm.getLabelList().size();
		lm.addNewKindLabel(lgk);
		int after = lm.getLabelList().size();
		Assert.assertTrue(after - before == 1);
	}

	@Test
	public void testDeleteLabelLGKLabel() {
		System.out.println("Test NO.5......");
		LGKLabel lgk = new LGKLabel("shape", 0, "circle");
		int before = lm.getLabelList().size();
		lm.deleteLabel(lgk);
		int after = lm.getLabelList().size();
		System.out.println(before + " -> " + after);
		Assert.assertTrue(before - after == 1);
	}

	@Test
	public void testAddLabelStringLGKLabel() {
		System.out.println("Test NO.6......");
		String fileName = "fucklgk.txt";
		LGKLabel lgk = new LGKLabel("sex", 0, "male");
		lm.addLabel(fileName, lgk);
		HashMap<String, ArrayList<LGKLabel>> hm = lm.getFileLabel();
		for (LGKLabel tmp : hm.get(fileName)) {
			if (tmp.getKey().equals(lgk.getKey())) {
				Assert.assertTrue(tmp.getValue().equals("male"));
				break;
			}
		}
		lgk.setValue("female");
		hm = lm.getFileLabel();
		for (LGKLabel tmp : hm.get(fileName)) {
			if (tmp.getKey().equals(lgk.getKey())) {
				Assert.assertTrue(tmp.getValue().equals("female"));
				break;
			}
		}

	}
	
	@Test
	public void testNotifyFileLabel() {
		LGKLabel temp = new LGKLabel("name", 0, "xj");
		lm.notifyFileLabel("fucklgk.txt", temp);
	}
	
	@Test
	public void testNotifyLabel() {
		LGKLabel temp = new LGKLabel("name", 0, "xj");
		lm.notifyLabel(temp);
	}
	
	@Test
	public void testRenameFile() {
		lm.renameFile("fucklgk.txt", "fucklgk.txt");
	}
	
	@Test
	public void testSetBaiduConn() {
		BaiduConnection bc = new BaiduConnection();
		lm.setBaiduConn(bc);
		lm.getBaiduConn();
	}
	
	@Test
	public void testIsLabelOrNot() {
		boolean flag = lm.isLabelOrNot();
		Assert.assertTrue(flag);
	}
	
	@Test
	public void testChangeLabel() {
		LGKLabel oldlabel = new LGKLabel("name", 0, "ljb");
		LGKLabel newlabel = new LGKLabel("name", 0, "tff");
		lm.changeLabel("fucktff.txt", oldlabel, newlabel);
	}

}
