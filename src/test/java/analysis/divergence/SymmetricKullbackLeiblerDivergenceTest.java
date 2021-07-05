package analysis.divergence;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.tcb.sensenet.internal.analysis.correlation.Frequencies;
import com.tcb.sensenet.internal.analysis.correlation.IntFrequencies;
import com.tcb.sensenet.internal.analysis.divergence.DivergenceStrategy;
import com.tcb.sensenet.internal.analysis.divergence.KullbackLeiblerDivergence;
import com.tcb.sensenet.internal.analysis.divergence.SymmetricKullbackLeiblerDivergence;

public class SymmetricKullbackLeiblerDivergenceTest {

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
		DivergenceStrategy kl = new SymmetricKullbackLeiblerDivergence();
		// Checked ref values with scipy.stats.entropy (v. 0.19.0)
		double ref = 0.1217 + 0.1151;
		assertEquals(ref/Math.log(2),kl.calculate(freq1, freq2),0.001);
		assertEquals(ref/Math.log(2),kl.calculate(freq2, freq1),0.001);
	}
	
	@Test
	public void testCalculateDisjunct(){
		DivergenceStrategy kl = new SymmetricKullbackLeiblerDivergence();
		Frequencies<Integer> freq1 = IntFrequencies.create(new int[]{0,0,0,0});
		Frequencies<Integer> freq2 = IntFrequencies.create(new int[]{1,1,0,0});
		// Checked ref values with scipy.stats.entropy (v. 0.19.0)
		assertEquals(Double.POSITIVE_INFINITY,kl.calculate(freq1, freq2),0.001);
		assertEquals(Double.POSITIVE_INFINITY,kl.calculate(freq2, freq1),0.001);
	}

}
