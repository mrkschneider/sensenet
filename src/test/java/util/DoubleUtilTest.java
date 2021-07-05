package util;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.tcb.sensenet.internal.util.DoubleUtil;

public class DoubleUtilTest {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testNormalInput() {
		Double x = 2.0;
		Double y = 2.0;
		assertEquals(y, DoubleUtil.replaceNaN(x, 5.0), 0.01);
	}

	@Test
	public void testNaNInput() {
		Double x = Double.NaN;
		Double y = 5.0;
		assertEquals(y, DoubleUtil.replaceNaN(x, y), 0.01);
	}
}
