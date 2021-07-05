package analysis.divergence;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.tcb.sensenet.internal.analysis.correlation.Frequencies;
import com.tcb.sensenet.internal.analysis.correlation.IntFrequencies;
import com.tcb.sensenet.internal.analysis.correlation.IntProbabilities;
import com.tcb.sensenet.internal.analysis.divergence.DivergenceStrategy;
import com.tcb.sensenet.internal.analysis.divergence.JensenShannonDivergence;
import com.tcb.sensenet.internal.analysis.divergence.KullbackLeiblerDivergence;

public class JensenShannonDivergenceTest {

	private int[] data1;
	private int[] data2;
	private Frequencies<Integer> freq1;
	private Frequencies<Integer> freq2;

	@Before
	public void setUp() throws Exception {
		this.data1 = new int[]{0,0,0,1,1,2,2,2,2,3};
		this.data2 = new int[]{0,0,0,0,1,1,2,2,3,3};
		this.freq1 = IntFrequencies.create(data1);
		this.freq2 = IntFrequencies.create(data2);
	}

	@Test
	public void testCalculate() {
		DivergenceStrategy kl = new JensenShannonDivergence();
		// ref values checked with scipy scipy.spatial.distance.jensenshannon (squared) (v. 1.2.1)
		assertEquals(0.02907/Math.log(2),kl.calculate(freq1, freq2),1e-5);
		assertEquals(0.02907/Math.log(2),kl.calculate(freq2, freq1),1e-5);
	}
	
	@Test
	public void testCalculate2() {
		DivergenceStrategy kl = new JensenShannonDivergence();
		assertEquals(1.,kl.calculate(IntProbabilities.create(new int[] {1}),
				IntProbabilities.create(new int[] {0})),1e-5);
	}
	
	@Test
	public void testCalculateDisjunct(){
		DivergenceStrategy kl = new JensenShannonDivergence();
		Frequencies<Integer> freq1 = IntFrequencies.create(new int[]{0,0,0,0});
		Frequencies<Integer> freq2 = IntFrequencies.create(new int[]{1,1,0,0});
		// ref values checked with scipy scipy.spatial.distance.jensenshannon (squared) (v. 1.2.1)
		assertEquals(0.2158/Math.log(2),kl.calculate(freq1, freq2),0.001);
		assertEquals(0.2158/Math.log(2),kl.calculate(freq2, freq1),0.001);
	}

}
