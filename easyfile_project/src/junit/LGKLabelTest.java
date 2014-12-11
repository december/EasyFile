/**
 * 测试LGKLable类
 */
package junit;

import java.util.ArrayList;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import basic.LGKLabel;

/**
 * 测试LGKLable类
 * 
 * @author luyf
 */
public class LGKLabelTest {

	LGKLabel LGK;

	@Before
	public void setUp() throws Exception {
		LGK = new LGKLabel("Name", 0, "anonymous");
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testSetValue() {
		LGK.setValue("");
		LGK.setValue("tff");
		Assert.assertTrue(LGK.getValue() == "tff");
	}

	@Test
	public void testSetKey() {
		LGK.setKey("Time");
		Assert.assertTrue(LGK.getKey() == "Time");
	}

	@Test
	public void testSetType() {
		LGK.setType(1);
		Assert.assertTrue(LGK.getType() == 1);
	}

	@Test
	public void testSetValueList() {
		ArrayList<String> nameList = new ArrayList<String>();
		nameList.add("xujie");
		nameList.add("ljb");
		LGK.setValueList(nameList);
		Assert.assertTrue(LGK.getValueList().size() == 2);
	}
	
	@Test
	public void testSetDescription() {
		LGK.setDescription("hahaha");
		Assert.assertTrue(LGK.getDescription().equals("hahaha"));
	}

}
