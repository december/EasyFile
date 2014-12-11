/**
 * 测试Coder类
 */
package junit;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;
import basic.Coder;

/**
 * 测试Coder类
 * 
 * @author luyf
 */
public class CoderTest {

	String key = "Test";
	byte[] test = { 'a', 'b', 'c', 'd', 'e' };

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testDecryptBASE64() {
		try {
			assertArrayEquals(Coder.decryptBASE64(key), (new BASE64Decoder()).decodeBuffer(key));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testEncryptBASE64() {
		try {
			String standard = (new BASE64Encoder()).encodeBuffer(test);
			String output = Coder.encryptBASE64(test);
			assertTrue(standard.equals(output));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
