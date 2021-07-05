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
import com.tcb.sensenet.internal.analysis.entropy.ShannonEntropy;

public class ShannonEntropyTest {

	private int[] data1;
	private int[] data2;
	private Frequencies<Integer> freq1;
	private Frequencies<Integer> freq2;

	@Before
	public void setUp() throws Exception {
		this.data1 = new int[]{0,0,0,0,0,0,0,1,1,1};
		this.data2 = new int[]{0,0,0,0,0,1,1,1,1,1};
		this.freq1 = IntFrequencies.create(data1);
		this.freq2 = IntFrequencies.create(data2);
	}

	@Test
	public void testCalculate() {
		ShannonEntropy ent = new ShannonEntropy();
		// ref values from scipy.stats.entropy version 1.4.1
		assertEquals(1,ent.calculate(freq2),1e-5);
		assertEquals(0.88129, ent.calculate(freq1), 1e-5);
	}

}
