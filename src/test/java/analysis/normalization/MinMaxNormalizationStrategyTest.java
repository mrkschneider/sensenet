package analysis.normalization;

import static org.junit.Assert.*;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

import com.tcb.sensenet.internal.analysis.normalization.MinMaxNormalizationStrategy;
import com.tcb.sensenet.internal.analysis.normalization.NoNormalizationStrategy;
import com.tcb.sensenet.internal.analysis.normalization.NormalizationStrategy;

public class MinMaxNormalizationStrategyTest {

	private double[] ds;

	@Before
	public void setUp() throws Exception {
		this.ds = new double[]{0.2,0.6,0.8,4.0,3.2};
	}

	@Test
	public void testNormalize() {
		double[] refs = new double[]{0.0, 0.11, 0.16, 1.0, 0.79};
		
		NormalizationStrategy strategy = new MinMaxNormalizationStrategy();
		strategy.normalize(ds);

		for(int i=0;i<refs.length;i++){
			assertEquals(refs[i],ds[i],0.01);
		}
	}

}
