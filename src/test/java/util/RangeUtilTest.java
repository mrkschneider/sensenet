package util;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.tcb.sensenet.internal.util.RangeUtil;

public class RangeUtilTest {

	@Before
	public void setUp() throws Exception {
				
	}

	@Test
	public void testIntRangeIntIntInt() {
		List<Integer> ref = Arrays.asList(2,4,6);
		assertEquals(ref, RangeUtil.intRange(2, 8, 2));	
	}

	@Test
	public void testIntRangeIntInt() {
		List<Integer> ref = Arrays.asList(2,3,4);
		assertEquals(ref, RangeUtil.intRange(2, 5));
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testIntRangeFailsForNegativeStep() {
		RangeUtil.intRange(2, 5, -1);
	}

	@Test
	public void testDoubleRangeIntIntInt() {
		List<Double> ref = Arrays.asList(2.,4.,6.);
		assertEquals(ref, RangeUtil.doubleRange(2, 8, 2));	
	}

	@Test
	public void testDoubleRangeIntInt() {
		List<Double> ref = Arrays.asList(2.,3.,4.);
		assertEquals(ref, RangeUtil.doubleRange(2, 5));
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testDoubleRangeFailsForNegativeStep() {
		RangeUtil.doubleRange(2, 5, -1);
	}

}
