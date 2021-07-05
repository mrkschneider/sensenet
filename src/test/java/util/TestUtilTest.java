package util;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import com.tcb.sensenet.internal.util.SizeMismatchError;
import com.tcb.sensenet.internal.util.TestUtil;

public class TestUtilTest {
	private List<Integer> a = Arrays.asList(1,2,3);
	private List<Integer> a2 = Arrays.asList(1,2,3);
	private List<Integer> aLonger = Arrays.asList(1,2,3,4);
	private List<Integer> aShorter = Arrays.asList(1,2);
	private List<Integer> empty = Arrays.asList();
	private List<Integer> b = Arrays.asList(1,4,3);
	
	@Test
	public void testListEqualsEqual() {
		TestUtil.assertListEquals(a, a);
		TestUtil.assertListEquals(a, a2);
	}
	
	@Test(expected=AssertionError.class)
	public void testListEqualsNotEqual() {
		TestUtil.assertListEquals(a, b);
	}
	
	@Test(expected=SizeMismatchError.class)
	public void testListEqualsLonger() {
		TestUtil.assertListEquals(a, aLonger);
	}
	
	@Test(expected=SizeMismatchError.class)
	public void testListEqualsEmpty() {
		TestUtil.assertListEquals(a, empty);
	}
	
	@Test(expected=SizeMismatchError.class)
	public void testListEqualsShorter() {
		TestUtil.assertListEquals(a, aShorter);
	}
	
	
	
}
