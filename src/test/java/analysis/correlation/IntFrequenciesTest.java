package analysis.correlation;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import com.tcb.sensenet.internal.analysis.correlation.DiscreteProbability;
import com.tcb.sensenet.internal.analysis.correlation.Frequencies;
import com.tcb.sensenet.internal.analysis.correlation.IntFrequencies;

public class IntFrequenciesTest {

	private int[] a;
	private int[] b;
	private Frequencies<Integer> fA;
	private Frequencies<Integer> fB;
	private int[] c;
	private Frequencies<Integer> fC;

	@Before
	public void setUp() throws Exception {
		this.a = new int[]{0,0,1,1,2,2};
		this.b = new int[]{0,3,1,3,2,3};
		this.c = new int[]{0,1,2,0,5,6};
		this.fA = IntFrequencies.create(a);
		this.fB = IntFrequencies.create(b);
		this.fC = IntFrequencies.create(c);
	}

	@Test
	public void testGetEvents() {
		Set<Integer> refA = new HashSet<>(Arrays.asList(0,1,2));
		Set<Integer> refB = new HashSet<>(Arrays.asList(0,1,2,3));
		Set<Integer> refC = new HashSet<>(Arrays.asList(0,1,2,5,6));
		
		assertEquals(refA,fA.getEvents());
		assertEquals(refB,fB.getEvents());
		assertEquals(refC,fC.getEvents());
	}
	
	@Test
	public void testGetFrequency() {
		assertEquals(2,fA.getFrequency(1),0.001);
		assertEquals(1,fB.getFrequency(1),0.001);
		assertEquals(3,fB.getFrequency(3),0.001);
		assertEquals(0,fC.getFrequency(3),0.001);
		assertEquals(1,fC.getFrequency(5),0.001);
	}
	
	@Test
	public void testMerge() {
		Frequencies<Integer> fD = IntFrequencies.merge(Arrays.asList(fA,fB,fC));
		
		assertEquals(5,fD.getFrequency(0),0.001);
		assertEquals(4,fD.getFrequency(1),0.001);
		assertEquals(4,fD.getFrequency(2),0.001);
		assertEquals(3,fD.getFrequency(3),0.001);
		assertEquals(0,fD.getFrequency(4),0.001);
		assertEquals(1,fD.getFrequency(5),0.001);
		assertEquals(1,fD.getFrequency(6),0.001);
	}
	
	@Test
	public void testAverage() {
		Frequencies<Integer> fD = IntFrequencies.average(Arrays.asList(fA,fB,fC));
		
		assertEquals(5./3,fD.getFrequency(0),0.001);
		assertEquals(4./3,fD.getFrequency(1),0.001);
		assertEquals(4./3,fD.getFrequency(2),0.001);
		assertEquals(3./3,fD.getFrequency(3),0.001);
		assertEquals(0./3,fD.getFrequency(4),0.001);
		assertEquals(1./3,fD.getFrequency(5),0.001);
		assertEquals(1./3,fD.getFrequency(6),0.001);
	}

	@Test
	public void testGetProbability() {
		assertEquals(0.33,fA.getProbability(1),0.01);
		assertEquals(0.16,fB.getProbability(1),0.01);
		assertEquals(0.5,fB.getProbability(3),0.01);
		assertEquals(0,fC.getProbability(3),0.01);
	}
	
	@Test
	public void testGetLength() {
		assertEquals(6,fA.getLength());
		assertEquals(6,fB.getLength());
		assertEquals(6,fC.getLength());
	}
	
	@Test
	public void testAdd() {
		assertEquals(2, fA.getFrequency(1),0.01);
		assertEquals(6, fA.getLength());
		fA.add(1);
		assertEquals(3, fA.getFrequency(1),0.01);
		assertEquals(7, fA.getLength());
		fA.add(1);
		assertEquals(4, fA.getFrequency(1),0.01);
		assertEquals(8, fA.getLength());
	}
	
	
}
