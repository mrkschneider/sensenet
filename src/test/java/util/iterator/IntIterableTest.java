package util.iterator;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import org.junit.Before;
import org.junit.Test;

import com.tcb.sensenet.internal.util.ArrayShuffler;
import com.tcb.sensenet.internal.util.TestUtil;
import com.tcb.sensenet.internal.util.iterator.DoubleIterable;
import com.tcb.sensenet.internal.util.iterator.DoubleIterator;
import com.tcb.sensenet.internal.util.iterator.IntIterable;
import com.tcb.sensenet.internal.util.iterator.IntIterator;


public class IntIterableTest {

	private int[] xs = {1,2,3,4};
	private List<Integer> xsL = Arrays.stream(xs)
			.boxed()
			.collect(Collectors.toList());
	
	@Before
	public void setUp() throws Exception {
	
	}

	@Test
	public void testOfArray() {
		IntIterable itr = IntIterable.of(xs);
		assertEquals(4, itr.size());
		TestUtil.assertListEquals(xsL,itToList(itr.ints()));
	}
	
	@Test
	public void testOfList() {
		IntIterable itr = IntIterable.of(xsL);
		assertEquals(4, itr.size());
		TestUtil.assertListEquals(xsL,itToList(itr.ints()));
	}
	
	@Test
	public void testToArray() {
		TestUtil.assertArrayEquals(xs, IntIterable.toArray(IntIterable.of(xs)));
	}
	
	@Test
	public void testToList() {
		TestUtil.assertListEquals(xsL, IntIterable.toList(IntIterable.of(xs)));
	}
		
	private static List<Integer> itToList(IntIterator it) {
		List<Integer> result = new ArrayList<>();
		while(it.hasNext()) result.add(it.nextInt());
		return result;
	}
			
}
