package path.analysis.centrality.normalization;

import static org.junit.Assert.*;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

import com.tcb.sensenet.internal.analysis.normalization.MinMaxNormalizationStrategy;
import com.tcb.sensenet.internal.analysis.normalization.NormalizationStrategy;
import com.tcb.sensenet.internal.path.analysis.centrality.normalization.MaxNodePairsNormalizationStrategy;

public class MaxNodePairsNormalizationStrategyTest {

	private double[] centralities;

	@Before
	public void setUp() throws Exception {
		this.centralities = new double[]{0.2,0.6,0.8,4.0,3.2};
	}

	@Test
	public void testNormalize() {
		double[] refCentralities = new double[]{0.03, 0.10, 0.13, 0.67, 0.53};
		
		NormalizationStrategy strategy = new MaxNodePairsNormalizationStrategy();
		strategy.normalize(centralities);
		
		for(int i=0;i<refCentralities.length;i++){
			assertEquals(refCentralities[i],centralities[i],0.01);
		}
	}
}
