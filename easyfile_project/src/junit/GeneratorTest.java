/**
 * 测试Generator类
 */
package junit;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import basic.Generator;

/**
 * 测试Generator类
 * 
 * @author luyf
 */
public class GeneratorTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testMain() {
		String[] test = new String[] { "xj", "tff", "ljb" };
		Generator.main(test);
	}

}
