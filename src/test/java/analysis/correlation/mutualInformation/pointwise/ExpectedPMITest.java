package analysis.correlation.mutualInformation.pointwise;

import static org.junit.Assert.*;

import org.apache.commons.lang3.tuple.Pair;
import org.junit.Before;
import org.junit.Test;

import com.tcb.sensenet.internal.analysis.correlation.DiscreteProbability;
import com.tcb.sensenet.internal.analysis.correlation.Frequencies;
import com.tcb.sensenet.internal.analysis.correlation.JointIntFrequencies;
import com.tcb.sensenet.internal.analysis.correlation.mutualInformation.pointwise.ExpectedPMI;
import com.tcb.sensenet.internal.analysis.correlation.mutualInformation.pointwise.PointwiseMutualInformationStrategy;
import com.tcb.sensenet.internal.analysis.correlation.mutualInformation.pointwise.PMI;

public class ExpectedPMITest extends PMITest {
	
	@Before
	public void setUp() throws Exception {
		super.setUp();
	}

	@Test
	public void testCalculate() {
		// Ref results according to https://en.wikipedia.org/wiki/Pointwise_mutual_information
		double p00 = freqAB.getProbability(Pair.of(0, 0));
		double p01 = freqAB.getProbability(Pair.of(0, 1));
		double p10 = freqAB.getProbability(Pair.of(1, 0));
		double p11 = freqAB.getProbability(Pair.of(1, 1));
		PointwiseMutualInformationStrategy inf = new ExpectedPMI();
		
		assertEquals(-1*p00,inf.calculate(0, 0, freqA,freqB,freqAB),0.01);
		assertEquals(0.22*p01,inf.calculate(0, 1, freqA,freqB,freqAB),0.01);
		assertEquals(1.58*p10,inf.calculate(1, 0, freqA,freqB,freqAB),0.01);
		assertEquals(-1.58*p11,inf.calculate(1, 1, freqA,freqB,freqAB),0.01);
	}

}
