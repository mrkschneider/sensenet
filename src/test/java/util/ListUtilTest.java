package util;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

import org.junit.Before;
import org.junit.Test;

import com.tcb.sensenet.internal.util.ListUtil;

public class ListUtilTest {

	private List<Integer> lst;

	@Before
	public void setUp() throws Exception {
		this.lst = Arrays.asList(1,2,3,4,5,6,7,8,9,10);
	}

	@Test
	public void testGetEveryNthForN1() {
		assertEquals(lst, ListUtil.getEveryNth(lst, 1));
	}
	
	@Test
	public void testGetEveryNthForN2() {
		List<Integer> ref = Arrays.asList(1,3,5,7,9);
		assertEquals(ref, ListUtil.getEveryNth(lst, 2));
	}
	
	@Test
	public void testGetEveryNthForN3() {
		List<Integer> ref = Arrays.asList(1,4,7,10);
		assertEquals(ref, ListUtil.getEveryNth(lst, 3));
	}
	
	@Test
	public void testGetEveryNthForN10() {
		List<Integer> ref = Arrays.asList(1);
		assertEquals(ref, ListUtil.getEveryNth(lst, 10));
	}
	
	@Test
	public void testGetMaxKey() {
		List<String> lst = Arrays.asList("1", "10", "2");
		Function<String,Integer> f = Integer::parseInt;
		
		assertEquals(1,ListUtil.maxKey(lst, f));
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testGetMaxKeyEmpty() {
		ListUtil.maxKey(new ArrayList<String>(), Integer::parseInt);
	}
	
	@Test
	public void testGetMinKey() {
		List<String> lst = Arrays.asList("1", "10", "2");
		Function<String,Integer> f = Integer::parseInt;
		
		assertEquals(0,ListUtil.minKey(lst, f));
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testGetMinKeyEmpty() {
		ListUtil.minKey(new ArrayList<String>(), Integer::parseInt);
	}

}
