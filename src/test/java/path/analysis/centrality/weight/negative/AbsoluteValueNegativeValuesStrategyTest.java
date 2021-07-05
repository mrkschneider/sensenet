package path.analysis.centrality.weight.negative;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.tcb.sensenet.internal.path.analysis.centrality.weight.negative.AbsoluteValueNegativeValuesStrategy;

public class AbsoluteValueNegativeValuesStrategyTest {

	private AbsoluteValueNegativeValuesStrategy strategy;

	@Before
	public void setUp() throws Exception {
		this.strategy = new AbsoluteValueNegativeValuesStrategy();
	}

	@Test
	public void testTransform() {
		Double x = 5.0;
		assertEquals(x, strategy.transform(x));
		
		Double y = -x;
		assertEquals(x, strategy.transform(y));
	}

}
