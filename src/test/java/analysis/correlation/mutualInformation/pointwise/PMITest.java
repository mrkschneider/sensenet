package analysis.correlation.mutualInformation.pointwise;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.tcb.sensenet.internal.analysis.correlation.mutualInformation.pointwise.PointwiseMutualInformationStrategy;
import com.tcb.sensenet.internal.util.MathLog;
import com.tcb.sensenet.internal.analysis.correlation.Frequencies;
import com.tcb.sensenet.internal.analysis.correlation.IntFrequencies;
import com.tcb.sensenet.internal.analysis.correlation.JointIntFrequencies;
import com.tcb.sensenet.internal.analysis.correlation.mutualInformation.pointwise.PMI;

public class PMITest {

	protected int[] dataA;
	protected int[] dataB;
	protected Frequencies<Integer> freqA;
	protected Frequencies<Integer> freqB;
	protected JointIntFrequencies freqAB;

	@Before
	public void setUp() throws Exception {
		// Test data according to https://en.wikipedia.org/wiki/Pointwise_mutual_information
		this.dataA = new int[]{0,0,0,0,0,0,0,0,1,1,1,1,0,0,0,0,0,0,0,0};
		this.dataB = new int[]{1,1,1,1,1,1,1,1,0,0,1,0,1,1,1,1,1,0,0,1};
		this.freqA = IntFrequencies.create(dataA);
		this.freqB = IntFrequencies.create(dataB);
		this.freqAB = JointIntFrequencies.create(dataA, dataB);
	}

	@Test
	public void testCalculate() {
		// Ref results according to https://en.wikipedia.org/wiki/Pointwise_mutual_information
		PointwiseMutualInformationStrategy inf = new PMI();
		assertEquals(-1,inf.calculate(0, 0, freqA, freqB, freqAB),0.01);
		assertEquals(0.22,inf.calculate(0, 1, freqA, freqB, freqAB),0.01);
		assertEquals(1.58,inf.calculate(1, 0, freqA, freqB, freqAB),0.01);
		assertEquals(-1.58,inf.calculate(1, 1, freqA, freqB, freqAB),0.01);
	}
	
	protected double log(double x) {
		return MathLog.log2(x);
	}
		
}
