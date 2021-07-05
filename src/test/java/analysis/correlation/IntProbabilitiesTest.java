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
import com.tcb.sensenet.internal.analysis.correlation.IntProbabilities;
import com.tcb.sensenet.internal.analysis.correlation.Probabilities;

public class IntProbabilitiesTest {

	private int[] a;
	private int[] b;
	private Probabilities<Integer> pA;
	private Probabilities<Integer> pB;
	private int[] c;
	private Probabilities<Integer> pC;

	@Before
	public void setUp() throws Exception {
		this.a = new int[]{0,0,1,1,2,2};
		this.b = new int[]{0,3,1,3,2,3};
		this.c = new int[]{0,1,2,0,5,6};
		this.pA = IntProbabilities.create(a);
		this.pB = IntProbabilities.create(b);
		this.pC = IntProbabilities.create(c);
	}

	@Test
	public void testGetEvents() {
		Set<Integer> refA = new HashSet<>(Arrays.asList(0,1,2));
		Set<Integer> refB = new HashSet<>(Arrays.asList(0,1,2,3));
		Set<Integer> refC = new HashSet<>(Arrays.asList(0,1,2,5,6));
		
		assertEquals(refA,pA.getEvents());
		assertEquals(refB,pB.getEvents());
		assertEquals(refC,pC.getEvents());
	}
		
	@Test
	public void testAverage() {
		Probabilities<Integer> fD = IntProbabilities.average(Arrays.asList(pA,pB,pC));
		
		assertEquals(0.277,fD.getProbability(0),0.001);
		assertEquals(0.222,fD.getProbability(1),0.001);
		assertEquals(0.222,fD.getProbability(2),0.001);
		assertEquals(0.166,fD.getProbability(3),0.001);
		assertEquals(0.,fD.getProbability(4),0.001);
		assertEquals(0.055,fD.getProbability(5),0.001);
		assertEquals(0.055,fD.getProbability(6),0.001);
	}

	@Test
	public void testGetProbability() {
		assertEquals(0.33,pA.getProbability(1),0.01);
		assertEquals(0.16,pB.getProbability(1),0.01);
		assertEquals(0.5,pB.getProbability(3),0.01);
		assertEquals(0,pC.getProbability(3),0.01);
	}
		
}
