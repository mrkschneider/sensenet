package path.analysis.centrality;

import java.util.Arrays;
import java.util.List;

import org.cytoscape.model.NetworkTestSupport;
import org.cytoscape.model.SavePolicy;

import com.tcb.sensenet.internal.analysis.normalization.NoNormalizationStrategy;
import com.tcb.sensenet.internal.app.AppColumns;
import com.tcb.sensenet.internal.network.PrivateSubnetworkFactory;
import com.tcb.sensenet.internal.path.analysis.centrality.NodeCentralityAnalyzer;
import com.tcb.sensenet.internal.path.analysis.centrality.WeightedNodeBetweennessCentralityAnalyzer;
import com.tcb.sensenet.internal.path.analysis.centrality.WeightedPathLengthCentralityAnalyzer;
import com.tcb.sensenet.internal.path.analysis.centrality.distance.InverseWeightDistanceStrategy;
import com.tcb.sensenet.internal.path.analysis.centrality.distance.PassthroughEdgeDistanceStrategy;
import com.tcb.sensenet.internal.path.analysis.centrality.weight.accumulation.ColumnSumWeightAccumulationStrategy;
import com.tcb.sensenet.internal.path.analysis.centrality.weight.accumulation.UniformWeightAccumulationStrategy;
import com.tcb.sensenet.internal.path.analysis.centrality.weight.negative.IgnoreNegativeValuesStrategy;
import com.tcb.cytoscape.cyLib.cytoApiWrappers.CyNetworkAdapter;
import com.tcb.cytoscape.cyLib.cytoApiWrappers.CyRootNetworkAdapter;

import network.WeightedTestNetworkFactory;

public class WeightedPathLengthCentralityAnalyzerTest extends AbstractWeightedNodeCentralityAnalyzerTest {

	private Integer[][] matrix = {
			{0,0,1,0,0,1,1,0,0,1},
			{0,0,0,0,1,0,0,0,0,1},
			{0,0,0,0,0,0,1,1,0,0},
			{0,0,0,0,1,1,0,1,0,1},
			{0,0,0,0,0,0,1,1,1,0},
			{0,0,0,0,0,0,0,0,0,1},
			{0,0,0,0,0,0,0,1,0,0},
			{0,0,0,0,0,0,0,0,0,1},
			{0,0,0,0,0,0,0,0,0,1},
			{0,0,0,0,0,0,0,0,0,0}};
	private Double[] weights = {
			1.0,1.0,2.0,0.2,6.0,2.0,1.0,0.5,0.1,0.01,
			3.0,1.0,2.0,3.0,4.0,1.0,0.7,2.0,3.1
	};
	
	private class TestPrivateSubnetworkFactory implements PrivateSubnetworkFactory {
		
		@Override
		public CyNetworkAdapter create(CyNetworkAdapter network) {
			CyRootNetworkAdapter rootNetwork = getRootNetwork(network);
			CyNetworkAdapter subNetwork = 
					rootNetwork.addSubNetwork(network.getNodeList(), network.getEdgeList());
			return subNetwork;
		}
		
		private CyRootNetworkAdapter getRootNetwork(CyNetworkAdapter network) {
			CyRootNetworkAdapter rootNetwork = new CyRootNetworkAdapter(
					fac.getTestSupport().getRootNetworkFactory()
						.getRootNetwork(network.getAdaptedNetwork()));
			return rootNetwork;
		}

		@Override
		public void destroy(CyNetworkAdapter network) {
			CyRootNetworkAdapter rootNetwork = getRootNetwork(network);
			rootNetwork.removeSubNetwork(network);
		}
		
	}
		
	@Override
	protected Integer[][] getAdjacencyMatrix() {
		return matrix;
	}

	@Override
	protected Double[] getWeights() {
		return weights;
	}

	@Override
	protected List<Double> getRefUnweightedCentralities() {
		// Reference checked with networkx 2.4 (average_shortest_path_length)
		List<Double> ref = Arrays.asList(0.072,0.067,0.067,0.044,0.1,
											0.011,0.044,0.072,0.067,0.183);
		return ref;
	}

	@Override
	protected List<Double> getRefWeightedCentralities() {
		// Reference checked with networkx 2.4 (average_shortest_path_length)
		List<Double> ref = Arrays.asList(0.47,0.259,0.143,0.55,0.16,0.15,
											0.04,0.0095,0.498,1.26);
		return ref;
	}

	@Override
	protected NodeCentralityAnalyzer getUnweightedAnalyzer() {
		WeightedPathLengthCentralityAnalyzer analysis = new WeightedPathLengthCentralityAnalyzer(
				new TestPrivateSubnetworkFactory(), new UniformWeightAccumulationStrategy(),
				new PassthroughEdgeDistanceStrategy(),
				new NoNormalizationStrategy(),
				new IgnoreNegativeValuesStrategy());
		return analysis;
	}

	@Override
	protected NodeCentralityAnalyzer getWeightedAnalyzer() {
		WeightedPathLengthCentralityAnalyzer analysis = new WeightedPathLengthCentralityAnalyzer(
				new TestPrivateSubnetworkFactory(),
				new ColumnSumWeightAccumulationStrategy(
						AppColumns.WEIGHT.toString(),
						getRefNetwork().getNetwork()),
				new PassthroughEdgeDistanceStrategy(),
				new NoNormalizationStrategy(),
				new IgnoreNegativeValuesStrategy());
		return analysis;
	}

}
