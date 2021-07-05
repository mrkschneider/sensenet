package path.analysis.centrality.weight.accumulation;

import static org.junit.Assert.*;

import java.util.List;

import org.cytoscape.model.CyEdge;
import org.junit.Before;
import org.junit.Test;

import com.tcb.sensenet.internal.app.AppColumns;
import com.tcb.sensenet.internal.path.analysis.centrality.weight.accumulation.ColumnAverageWeightAccumulationStrategy;
import com.tcb.sensenet.internal.path.analysis.centrality.weight.accumulation.ColumnSumWeightAccumulationStrategy;
import com.tcb.sensenet.internal.path.analysis.centrality.weight.accumulation.WeightAccumulationStrategy;

import network.WeightedTestNetwork;

public class ColumnAverageWeightAccumulationStrategyTest extends AbstractWeightAccumulationStrategyTest {

	@Test
	public void testWeight() {
		WeightedTestNetwork refNetwork = getRefNetwork();
		WeightAccumulationStrategy strategy = new ColumnAverageWeightAccumulationStrategy(
				AppColumns.WEIGHT.toString(),refNetwork.getNetwork());
		List<CyEdge> edges = refNetwork.getSortedEdges();
		Double test = strategy.weight(edges);
		assertEquals(1.56,test,0.01);
	}

}
