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
import com.tcb.sensenet.internal.analysis.divergence.PopulationShiftDivergence;

public class ShiftDivergenceTest {

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
		DivergenceStrategy s = new PopulationShiftDivergence();
		// ref values checked with TODO
		assertEquals(0.2,s.calculate(freq1, freq2),1e-5);
		assertEquals(0.2,s.calculate(freq2, freq1),1e-5);
	}
	
	@Test
	public void testCalculate2() {
		DivergenceStrategy s = new PopulationShiftDivergence();
		assertEquals(1.,s.calculate(IntProbabilities.create(new int[] {1}),
				IntProbabilities.create(new int[] {0})),1e-5);
	}
	
	@Test
	public void testCalculateDisjunct(){
		DivergenceStrategy s = new PopulationShiftDivergence();
		Frequencies<Integer> freq1 = IntFrequencies.create(new int[]{0,0,0,0});
		Frequencies<Integer> freq2 = IntFrequencies.create(new int[]{1,1,0,0});
		// ref values checked with TODO
		assertEquals(0.5,s.calculate(freq1, freq2),0.001);
		assertEquals(0.5,s.calculate(freq2, freq1),0.001);
	}

}
