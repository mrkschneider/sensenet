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


public class DoubleIterableTest {

	private double[] xs = {1.,2.,3.,4.};
	private List<Double> xsL = Arrays.stream(xs)
			.boxed()
			.collect(Collectors.toList());
	
	@Before
	public void setUp() throws Exception {
	
	}

	@Test
	public void testOfArray() {
		DoubleIterable itr = DoubleIterable.of(xs);
		assertEquals(4, itr.size());
		TestUtil.assertListEquals(xsL,itToList(itr.doubles()));
	}
	
	@Test
	public void testOfList() {
		DoubleIterable itr = DoubleIterable.of(xsL);
		assertEquals(4, itr.size());
		TestUtil.assertListEquals(xsL,itToList(itr.doubles()));
	}
	
	@Test
	public void testToArray() {
		TestUtil.assertArrayEquals(xs, DoubleIterable.toArray(DoubleIterable.of(xs)));
	}
	
	@Test
	public void testToList() {
		TestUtil.assertListEquals(xsL, DoubleIterable.toList(DoubleIterable.of(xs)));
	}
		
	private static List<Double> itToList(DoubleIterator it) {
		List<Double> result = new ArrayList<>();
		while(it.hasNext()) result.add(it.nextDouble());
		return result;
	}
			
}
