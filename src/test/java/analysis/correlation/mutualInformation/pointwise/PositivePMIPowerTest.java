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
import com.tcb.sensenet.internal.analysis.correlation.mutualInformation.pointwise.PositivePMIPower;
import com.tcb.sensenet.internal.util.MathLog;
import com.tcb.sensenet.internal.analysis.correlation.mutualInformation.pointwise.PMI;

public class PositivePMIPowerTest extends PMITest {
	
	@Before
	public void setUp() throws Exception {
		super.setUp();
	}

	@Test
	public void testCalculate2() {
		PointwiseMutualInformationStrategy inf = new PositivePMIPower(2.);
		
		// TODO: Check references
		assertEquals(0.072*Math.log(2),inf.calculate(0, 0, freqA,freqB,freqAB),0.01);
		assertEquals(1.18*Math.log(2),inf.calculate(0, 1, freqA,freqB,freqAB),0.01);
		assertEquals(0.65*Math.log(2),inf.calculate(1, 0, freqA,freqB,freqAB),0.01);
		assertEquals(0.02*Math.log(2),inf.calculate(1, 1, freqA,freqB,freqAB),0.01);
	}
	
	@Test
	public void testCalculate3() {
		PointwiseMutualInformationStrategy inf = new PositivePMIPower(3.);
		
		// TODO: Check references
		assertEquals(0.007*Math.log(2),inf.calculate(0, 0, freqA,freqB,freqAB),0.01);
		assertEquals(0.82*Math.log(2),inf.calculate(0, 1, freqA,freqB,freqAB),0.01);
		assertEquals(0.10*Math.log(2),inf.calculate(1, 0, freqA,freqB,freqAB),0.01);
		assertEquals(0.001*Math.log(2),inf.calculate(1, 1, freqA,freqB,freqAB),0.01);
	}

	
}
