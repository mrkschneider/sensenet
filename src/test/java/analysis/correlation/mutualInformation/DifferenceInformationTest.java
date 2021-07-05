package analysis.correlation.mutualInformation;

import static org.junit.Assert.*;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.tcb.sensenet.internal.analysis.correlation.difference.DifferenceInformation;
import com.tcb.sensenet.internal.analysis.correlation.mutualInformation.MutualInformation;
import com.tcb.sensenet.internal.analysis.correlation.mutualInformation.pointwise.ExpectedPMI;
import com.tcb.sensenet.internal.analysis.correlation.mutualInformation.pointwise.PMI;
import com.tcb.sensenet.internal.util.iterator.IntIterable;

public class DifferenceInformationTest {

	/*private int[] timelineA1 = {0,-1,1,0,-1,1,0,0,0,1,0,-1,-1,1,1,0,0,1};
	private int[] timelineA2 = {0,-1,1,0,-1,1,0,0,0,1,0,-1,-1,1,1,0,0,0};
	private int[] timelineB1 = {1,1,-1,-1,0,0,-1,0,0,0,-1,1,0,-1,1,0,0,0};
	private int[] timelineB2 = {1,1,-1,-1,0,0,-1,0,0,0,-1,1,0,-1,1,0,0,1};*/
	
	private IntIterable timelineA1 = IntIterable.of(new int[]{0,0,1,1});
	private IntIterable timelineB1 = IntIterable.of(new int[]{1,1,0,0});
	
	private IntIterable timelineA2 = IntIterable.of(new int[]{0,1,1,1});
	private IntIterable timelineB2 = IntIterable.of(new int[]{1,1,0,0});
	
	private IntIterable empty = IntIterable.of(new int[]{0,0,0,0});
	
	private DifferenceInformation info;
	
	@Before
	public void setUp() throws Exception {
		this.info = new DifferenceInformation(new ExpectedPMI());
	}

	@Test
	public void testCalculateSame() {
		Double test = info.calculate(
				timelineA1,
				timelineB1,
				timelineA1,
				timelineB1);
		assertEquals(0.0,test,0.01);
	}
	
	@Test
	public void testCalculateDifferent() {
		Double test = info.calculate(timelineA1, timelineB1, timelineA2, timelineB2);
		// Reference checked manually
		assertEquals(0.4774 / Math.log(2),test,0.0001);
	}
	
	@Test
	public void testCalculateEmpty() {
		Double test = info.calculate(timelineA1, timelineB1, empty, timelineB2);
		Double ref = new MutualInformation().calculate(timelineA1,timelineB1);

		assertEquals(ref,test,0.01);
		
		test = info.calculate(empty, empty, timelineA2, timelineB2);
		ref = 0.418 / Math.log(2);

		assertEquals(ref,test,0.01);
	}

}
