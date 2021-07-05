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
import com.tcb.sensenet.internal.util.iterator.DoubleIterator;


public class DoubleIteratorTest {

	private double[] xs = {1.,2.,3.,4.};
	private List<Double> xsL = Arrays.stream(xs)
			.boxed()
			.collect(Collectors.toList());
	
	@Before
	public void setUp() throws Exception {
	
	}

	@Test
	public void testOfArray() {
		DoubleIterator it = DoubleIterator.of(xs);
		TestUtil.assertListEquals(xsL,itToList(it));
	}
	
	@Test
	public void testOfList() {
		DoubleIterator it = DoubleIterator.of(xsL);
		TestUtil.assertListEquals(xsL, itToList(it));
	}
	
	
	private static List<Double> itToList(DoubleIterator it) {
		List<Double> result = new ArrayList<>();
		while(it.hasNext()) result.add(it.nextDouble());
		return result;
	}
			
}
