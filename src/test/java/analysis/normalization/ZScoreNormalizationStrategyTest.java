package analysis.normalization;

import static org.junit.Assert.*;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

import com.tcb.sensenet.internal.analysis.normalization.MinMaxNormalizationStrategy;
import com.tcb.sensenet.internal.analysis.normalization.NoNormalizationStrategy;
import com.tcb.sensenet.internal.analysis.normalization.NormalizationStrategy;
import com.tcb.sensenet.internal.analysis.normalization.ZScoreNormalizationStrategy;

public class ZScoreNormalizationStrategyTest {

	private double[] ds;

	@Before
	public void setUp() throws Exception {
		this.ds = new double[]{0.2,0.6,0.8,4.0,3.2};
	}

	@Test
	public void testNormalize() {
		// Verified with numpy 1.16.3
		double[] refs = new double[]{-0.91, -0.68, -0.56, 1.3, 0.84};
		
		NormalizationStrategy strategy = new ZScoreNormalizationStrategy();
		strategy.normalize(ds);

		for(int i=0;i<refs.length;i++){
			assertEquals(refs[i],ds[i],0.01);
		}
	}

}
