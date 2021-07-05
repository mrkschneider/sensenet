package path.analysis.centrality.distance;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.tcb.sensenet.internal.path.analysis.centrality.distance.EdgeDistanceStrategy;
import com.tcb.sensenet.internal.path.analysis.centrality.distance.InverseWeightDistanceStrategy;

public class InverseWeightDistanceStrategyTest {

	private double weight;
	private InverseWeightDistanceStrategy strategy;

	@Before
	public void setUp() throws Exception {
		this.weight = 5.0;
		this.strategy = new InverseWeightDistanceStrategy();
	}

	@Test
	public void testGetDistance() {
		Double test  = strategy.getDistance(weight);
		
		assertEquals(1./5.,test,0.01);
	}
	
	@Test
	public void testGetDistanceForZero() {
		Double test = strategy.getDistance(0.0);
		assertEquals(Double.POSITIVE_INFINITY, test, 0.01);
	}

}
