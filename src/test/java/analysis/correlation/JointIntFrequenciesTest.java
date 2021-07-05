package analysis.correlation;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang3.tuple.Pair;
import org.junit.Before;
import org.junit.Test;

import com.tcb.sensenet.internal.analysis.correlation.DiscreteProbability;
import com.tcb.sensenet.internal.analysis.correlation.Frequencies;
import com.tcb.sensenet.internal.analysis.correlation.IntFrequencies;
import com.tcb.sensenet.internal.analysis.correlation.JointIntFrequencies;

public class JointIntFrequenciesTest {

	private int[] a;
	private int[] b;
	private Frequencies<Pair<Integer,Integer>> f;

	@Before
	public void setUp() throws Exception {
		this.a = new int[]{0,0,1,1,0,2};
		this.b = new int[]{0,3,1,1,3,3};
		this.f = JointIntFrequencies.create(a,b);
	}

	@Test
	public void testGetEvents() {
		Set<Pair<Integer,Integer>> ref = new HashSet<>(Arrays.asList(
				Pair.of(0, 0),
				Pair.of(0, 3),
				Pair.of(1, 1),
				Pair.of(2, 3)
				));
				
		assertEquals(ref,f.getEvents());
	}
	
	@Test
	public void testGetFrequency() {
		assertEquals(1,f.getFrequency(Pair.of(0, 0)),0.001);
		assertEquals(2,f.getFrequency(Pair.of(0, 3)),0.001);
		assertEquals(2,f.getFrequency(Pair.of(1, 1)),0.001);
		assertEquals(1,f.getFrequency(Pair.of(2, 3)),0.001);
	}

	@Test
	public void testGetProbability() {
		assertEquals(1./6,f.getProbability(Pair.of(0, 0)),0.01);
		assertEquals(2./6,f.getProbability(Pair.of(0, 3)),0.01);
		assertEquals(2./6,f.getProbability(Pair.of(1, 1)),0.01);
		assertEquals(1./6,f.getProbability(Pair.of(2, 3)),0.01);
	}
	
	@Test
	public void testGetLength() {
		assertEquals(6,f.getLength());
	}
	
	@Test
	public void testAdd() {
		assertEquals(2, f.getFrequency(Pair.of(0, 3)),0.01);
		assertEquals(6, f.getLength());
		f.add(Pair.of(0,3));
		assertEquals(3, f.getFrequency(Pair.of(0, 3)),0.01);
		assertEquals(7, f.getLength());
		f.add(Pair.of(0,3));
		assertEquals(4, f.getFrequency(Pair.of(0, 3)),0.01);
		assertEquals(8, f.getLength());
	}

}
