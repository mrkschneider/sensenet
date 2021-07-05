package path;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import org.cytoscape.model.CyNode;
import org.junit.Before;
import org.junit.Test;

import com.tcb.sensenet.internal.analysis.normalization.NoNormalizationStrategy;
import com.tcb.sensenet.internal.app.AppColumns;
import com.tcb.sensenet.internal.path.DistanceSearcher;
import com.tcb.sensenet.internal.path.Path;
import com.tcb.sensenet.internal.path.ShortestPathSearcher;
import com.tcb.sensenet.internal.path.SimpleDistanceSearcher;
import com.tcb.sensenet.internal.path.WeightedAveragePathLengthAnalyzer;
import com.tcb.sensenet.internal.path.analysis.centrality.distance.PassthroughEdgeDistanceStrategy;
import com.tcb.sensenet.internal.path.analysis.centrality.weight.accumulation.ColumnSumWeightAccumulationStrategy;
import com.tcb.sensenet.internal.path.analysis.centrality.weight.accumulation.UniformWeightAccumulationStrategy;
import com.tcb.sensenet.internal.path.analysis.centrality.weight.negative.IgnoreNegativeValuesStrategy;
import com.tcb.sensenet.internal.util.ObjMap;
import com.tcb.cytoscape.cyLib.cytoApiWrappers.CyNetworkAdapter;

import network.AdjacencyMatrixNetworkFactory;
import network.WeightedTestNetwork;
import network.WeightedTestNetworkFactory;

public class WeightedAveragePathLengthAnalyzerTest {

	private Integer[][] matrix = 
		    {{0,0,1,0,0,1,1,0,0,1},
			{0,0,0,0,1,0,0,0,0,1},
			{1,0,0,0,0,0,1,1,0,0},
			{0,0,0,0,1,1,0,1,0,1},
			{0,1,0,1,0,0,1,1,1,0},
			{1,0,0,1,0,0,0,0,0,1},
			{1,0,1,0,1,0,0,1,0,0},
			{0,0,1,1,1,0,1,0,0,1},
			{0,0,0,0,1,0,0,0,0,1},
			{1,1,0,1,0,1,0,1,1,0}};
	private Double[] weights = {
			1.0,1.0,2.0,0.2,6.0,2.0,1.0,0.5,0.1,0.01,
			3.0,1.0,2.0,3.0,4.0,1.0,0.7,2.0,3.1
	};
	
	private CyNetworkAdapter network;
	private List<CyNode> nodes;
	private WeightedAveragePathLengthAnalyzer analyzer;
	private WeightedTestNetwork weightedNetwork;
	private WeightedAveragePathLengthAnalyzer weightedAnalyzer;
			
	
	@Before
	public void setUp() throws Exception {
		this.weightedNetwork = new WeightedTestNetworkFactory().create(matrix,weights);
		this.network = weightedNetwork.getNetwork();
		this.nodes = weightedNetwork.getSortedNodes();
		this.analyzer = 
				new WeightedAveragePathLengthAnalyzer(
						new UniformWeightAccumulationStrategy(),
						new PassthroughEdgeDistanceStrategy(),
						new IgnoreNegativeValuesStrategy());
		this.weightedAnalyzer =
				new WeightedAveragePathLengthAnalyzer(
						new ColumnSumWeightAccumulationStrategy(
								AppColumns.WEIGHT.toString(),
								network),
						new PassthroughEdgeDistanceStrategy(),
						new IgnoreNegativeValuesStrategy());
	}

	@Test
	public void testGetAnalysis() {
		ObjMap m = analyzer.analyze(network);
		double avgPathLength = m.get("averagePathLength",double.class);
		// Reference checked with networkx 2.4 (average_shortest_path_length)
		assertEquals(1.622, avgPathLength, 0.001);
	}
	
	@Test
	public void testGetAnalysisWeighted() {
		ObjMap m = weightedAnalyzer.analyze(weightedNetwork.getNetwork());
		double avgPathLength = m.get("averagePathLength",double.class);
		// Reference checked with networkx 2.4 (average_shortest_path_length)
		assertEquals(2.242, avgPathLength, 0.001);
	}
	
	@Test
	public void testGetAnalysisSubnet1() {
		
		network.removeNodes(Arrays.asList(nodes.get(2)));
		
		ObjMap m = analyzer.analyze(network);
		
		double avgPathLength = m.get("averagePathLength",double.class);
		// Reference checked with networkx 2.4 (average_shortest_path_length)
		assertEquals(1.555, avgPathLength, 0.001);
	}
	
	@Test
	public void testGetAnalysisSubnet1Weighted() {
		
		network.removeNodes(Arrays.asList(nodes.get(2)));
		
		ObjMap m = weightedAnalyzer.analyze(network);
		
		double avgPathLength = m.get("averagePathLength",double.class);
		// Reference checked with networkx 2.4 (average_shortest_path_length)
		assertEquals(2.385, avgPathLength, 0.001);
	}
	
	@Test
	public void testGetAnalysisSubnet2() {
		
		network.removeNodes(Arrays.asList(nodes.get(6)));
		
		ObjMap m = analyzer.analyze(network);
		
		double avgPathLength = m.get("averagePathLength",double.class);
		// Reference checked with networkx 2.4 (average_shortest_path_length)
		assertEquals(1.666, avgPathLength, 0.001);
	}
	
	@Test
	public void testGetAnalysisSubnet2Weighted() {
		
		network.removeNodes(Arrays.asList(nodes.get(6)));
		
		ObjMap m = weightedAnalyzer.analyze(network);
		
		double avgPathLength = m.get("averagePathLength",double.class);
		// Reference checked with networkx 2.4 (average_shortest_path_length)
		assertEquals(2.202, avgPathLength, 0.001);
	}
	
}
