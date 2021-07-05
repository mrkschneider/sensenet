package path.analysis.centrality.weight.accumulation;

import static org.junit.Assert.*;

import java.util.List;

import org.cytoscape.model.CyEdge;
import org.junit.Before;
import org.junit.Test;

import com.tcb.sensenet.internal.app.AppColumns;
import com.tcb.sensenet.internal.path.analysis.centrality.weight.accumulation.ColumnSumWeightAccumulationStrategy;
import com.tcb.sensenet.internal.path.analysis.centrality.weight.accumulation.EdgeCountAccumulationStrategy;
import com.tcb.sensenet.internal.path.analysis.centrality.weight.accumulation.WeightAccumulationStrategy;

import network.WeightedTestNetwork;

public class EdgeCountAccumulationStrategyTest extends AbstractWeightAccumulationStrategyTest {

	@Test
	public void testWeight() {
		WeightedTestNetwork refNetwork = getRefNetwork();
		WeightAccumulationStrategy strategy = new EdgeCountAccumulationStrategy();
		List<CyEdge> edges = refNetwork.getSortedEdges();
		Double test = strategy.weight(edges);
		assertEquals(19.,test,0.01);
	}

}
