package path.analysis.centrality.weight.negative;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.tcb.sensenet.internal.path.analysis.centrality.weight.negative.AbsoluteValueNegativeValuesStrategy;
import com.tcb.sensenet.internal.path.analysis.centrality.weight.negative.IgnoreNegativeValuesStrategy;

public class IgnoreNegativeValuesStrategyTest {

	private IgnoreNegativeValuesStrategy strategy;

	@Before
	public void setUp() throws Exception {
		this.strategy = new IgnoreNegativeValuesStrategy();
	}

	@Test
	public void testTransform() {
		Double x = 5.0;
		assertEquals(x, strategy.transform(x));
		
		Double y = -x;
		assertEquals((Double)0.0d, strategy.transform(y));
	}

}
