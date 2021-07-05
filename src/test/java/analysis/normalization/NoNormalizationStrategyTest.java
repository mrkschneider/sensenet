package analysis.normalization;

import static org.junit.Assert.*;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

import com.tcb.sensenet.internal.analysis.normalization.NoNormalizationStrategy;
import com.tcb.sensenet.internal.analysis.normalization.NormalizationStrategy;

public class NoNormalizationStrategyTest {

	private double[] ds;
	private double[] refDs;

	@Before
	public void setUp() throws Exception {
		this.ds = new double[]{0.2,0.6,0.8,4.0,3.2};
		this.refDs = Arrays.copyOf(ds, ds.length);
	}

	@Test
	public void testNormalize() {
		NormalizationStrategy strategy = new NoNormalizationStrategy();
		strategy.normalize(ds);
		
		for(int i=0;i<refDs.length;i++){
			assertEquals(refDs[i],ds[i],0.01);
		}
	}

}
