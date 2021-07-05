package analysis.correlation.mutualInformation;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import com.tcb.sensenet.internal.analysis.correlation.mutualInformation.MutualInformation;
import com.tcb.sensenet.internal.util.iterator.IntIterable;

public class MutualInformationTest {

	private IntIterable timelineA = IntIterable.of(
			new int[] {0,-1,1,0,-1,1,0,0,0,1,0,-1,-1,1,1,0,0,1});
	private IntIterable timelineB = IntIterable.of(
			new int[]{1,1,-1,-1,0,0,-1,0,0,0,-1,1,0,-1,1,0,0,0});
	
	private double ref = 0.112 / Math.log(2); // Source: Bioconductor 3.0: bioDist mutualInfo function
	private MutualInformation mutInf;
	
	@Before
	public void setUp() throws Exception {
		this.mutInf = new MutualInformation();
	}

	@Test
	public void testCalculate() {
		double test = mutInf.calculate(timelineA, timelineB);
		
		assertEquals(ref,test,0.001);
	}

}
