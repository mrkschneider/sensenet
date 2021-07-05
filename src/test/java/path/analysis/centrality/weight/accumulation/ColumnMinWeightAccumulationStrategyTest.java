package path.analysis.centrality.weight.accumulation;

import static org.junit.Assert.*;

import java.util.List;

import org.cytoscape.model.CyEdge;
import org.junit.Before;
import org.junit.Test;

import com.tcb.sensenet.internal.app.AppColumns;
import com.tcb.sensenet.internal.path.analysis.centrality.weight.accumulation.ColumnAverageWeightAccumulationStrategy;
import com.tcb.sensenet.internal.path.analysis.centrality.weight.accumulation.ColumnMaxWeightAccumulationStrategy;
import com.tcb.sensenet.internal.path.analysis.centrality.weight.accumulation.ColumnMinWeightAccumulationStrategy;
import com.tcb.sensenet.internal.path.analysis.centrality.weight.accumulation.ColumnSumWeightAccumulationStrategy;
import com.tcb.sensenet.internal.path.analysis.centrality.weight.accumulation.WeightAccumulationStrategy;

import network.WeightedTestNetwork;

public class ColumnMinWeightAccumulationStrategyTest extends AbstractWeightAccumulationStrategyTest {

	@Test
	public void testWeight() {
		WeightedTestNetwork refNetwork = getRefNetwork();
		WeightAccumulationStrategy strategy = new ColumnMinWeightAccumulationStrategy(
				AppColumns.WEIGHT.toString(),refNetwork.getNetwork());
		List<CyEdge> edges = refNetwork.getSortedEdges();
		Double test = strategy.weight(edges);
		assertEquals(0.01,test,0.01);
	}

}
