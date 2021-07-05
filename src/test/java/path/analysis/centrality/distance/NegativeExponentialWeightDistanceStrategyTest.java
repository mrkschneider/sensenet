package path.analysis.centrality.distance;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.tcb.sensenet.internal.path.analysis.centrality.distance.EdgeDistanceStrategy;
import com.tcb.sensenet.internal.path.analysis.centrality.distance.InverseWeightDistanceStrategy;
import com.tcb.sensenet.internal.path.analysis.centrality.distance.NegativeExponentialWeightDistanceStrategy;

public class NegativeExponentialWeightDistanceStrategyTest {

	private double weight;
	private EdgeDistanceStrategy strategy;

	@Before
	public void setUp() throws Exception {
		this.weight = 5.0;
		this.strategy = new NegativeExponentialWeightDistanceStrategy();
	}

	@Test
	public void testGetDistance() {
		Double test  = strategy.getDistance(weight);
		
		assertEquals(Math.exp(-weight),test,0.01);
	}

}
