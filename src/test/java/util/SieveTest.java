package util;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.tcb.sensenet.internal.util.Sieve;

public class SieveTest {

	private List<String> lst;
	private Sieve<String> sieve;

	@Before
	public void setUp() throws Exception {
		this.lst = Arrays.asList("a","b","c","d","e","f","g","h");
		this.sieve = new Sieve<>(lst,2);
	}

	@Test
	public void testGetSievedIndices() {
		List<Integer> ref = Arrays.asList(0,2,4,6);
		assertEquals(ref, sieve.getSievedIndices());
	}

	@Test
	public void testGetSieved() {
		List<String> ref = Arrays.asList("a","c","e","g");
		assertEquals(ref, sieve.getSieved());
	}

	@Test
	public void testGetSievedSize() {
		assertEquals((Integer)4, sieve.getSievedSize());
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testSieveFailsForZeroStep() {
		new Sieve<>(lst,0);
	}
	
	@Test
	public void testLargeStep() {
		Sieve<String> s = new Sieve<>(lst,10);
		assertEquals(Arrays.asList("a"), s.getSieved());
		assertEquals(Arrays.asList(0), s.getSievedIndices());
	}
	
	@Test
	public void testEmptyList() {
		Sieve<String> s = new Sieve<>(new ArrayList<String>(),1);
		assertEquals(new ArrayList<String>(), s.getSieved());
		assertEquals(new ArrayList<Integer>(), s.getSievedIndices());
	}
	

}
