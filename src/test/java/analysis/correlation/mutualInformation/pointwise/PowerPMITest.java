package analysis.correlation.mutualInformation.pointwise;

import static org.junit.Assert.*;

import org.apache.commons.lang3.tuple.Pair;
import org.junit.Before;
import org.junit.Test;

import com.tcb.sensenet.internal.analysis.correlation.DiscreteProbability;
import com.tcb.sensenet.internal.analysis.correlation.Frequencies;
import com.tcb.sensenet.internal.analysis.correlation.JointIntFrequencies;
import com.tcb.sensenet.internal.analysis.correlation.mutualInformation.pointwise.ExpectedPMI;
import com.tcb.sensenet.internal.analysis.correlation.mutualInformation.pointwise.PMIPower;
import com.tcb.sensenet.internal.analysis.correlation.mutualInformation.pointwise.PointwiseMutualInformationStrategy;
import com.tcb.sensenet.internal.analysis.correlation.mutualInformation.pointwise.PMI;

public class PowerPMITest extends PMITest {
	
	@Before
	public void setUp() throws Exception {
		super.setUp();
	}

	@Test
	public void testCalculate2() {
		PointwiseMutualInformationStrategy inf = new PMIPower(2.);
		
		// TODO: Check references
		assertEquals(-4.32,inf.calculate(0, 0, freqA,freqB,freqAB),0.01);
		assertEquals(-0.29,inf.calculate(0, 1, freqA,freqB,freqAB),0.01);
		assertEquals(-1.15,inf.calculate(1, 0, freqA,freqB,freqAB),0.01);
		assertEquals(-5.9,inf.calculate(1, 1, freqA,freqB,freqAB),0.01);
	}
	
	@Test
	public void testCalculate3() {
		PointwiseMutualInformationStrategy inf = new PMIPower(3.);
		
		// TODO: Check references
		assertEquals(-7.64,inf.calculate(0, 0, freqA,freqB,freqAB),0.01);
		assertEquals(-0.81,inf.calculate(0, 1, freqA,freqB,freqAB),0.01);
		assertEquals(-3.88,inf.calculate(1, 0, freqA,freqB,freqAB),0.01);
		assertEquals(-10.22,inf.calculate(1, 1, freqA,freqB,freqAB),0.01);
	}

	
}
