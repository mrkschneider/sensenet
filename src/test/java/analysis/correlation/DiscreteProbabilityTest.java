package analysis.correlation;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.tcb.sensenet.internal.analysis.correlation.DiscreteProbability;

public class DiscreteProbabilityTest {

	private int[] a;
	private int[] b;

	@Before
	public void setUp() throws Exception {
		this.a = new int[]{0,0,1,1,2,2};
		this.b = new int[]{0,3,1,3,2,3};
	}

	@Test
	public void testGetEvents() {
		int[] ref = new int[]{0,1,2,3};
		
		assertArrayEquals(ref,DiscreteProbability.getEvents(ref));
	}

	@Test
	public void testGetProbability() {
		double ref = 0.33;
		assertEquals(ref,DiscreteProbability.getProbability(1, a),0.01);
	}

	@Test
	public void testGetJointProbability() {
		double ref = 1./6.;
		
		assertEquals(ref,DiscreteProbability.getJointProbability(1, 3, a, b),0.01);
	}

}
