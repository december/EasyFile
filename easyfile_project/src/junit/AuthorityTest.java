/**
 * 测试Authority类
 */
package junit;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import basic.Authority;

/**
 * 测试Authority类
 * 
 * @author luyf
 */
public class AuthorityTest {

	Authority test;

	@Before
	public void setUp() throws Exception {
		test = new Authority("first", "second", "last");
	}

	@After
	public void tearDown() throws Exception {

	}

	@Test
	public void testGetAuthority() {
		boolean[] r = new boolean[7];

		r[0] = test.getAuthority("download", "last");
		Assert.assertTrue(r[0] == true);
		r[1] = test.getAuthority("upload", "first");
		Assert.assertTrue(r[1] == true);
		r[2] = test.getAuthority("upload", "last");
		Assert.assertTrue(r[2] == false);
		r[3] = test.getAuthority("change", "second");
		Assert.assertTrue(r[3] == false);
		r[4] = test.getAuthority("delete", "first");
		Assert.assertTrue(r[4] == true);
		r[5] = test.getAuthority("change", "first");
		Assert.assertTrue(r[5]);
		r[6] = test.getAuthority("fuck", "first");
		Assert.assertFalse(r[6]);
	}

}
