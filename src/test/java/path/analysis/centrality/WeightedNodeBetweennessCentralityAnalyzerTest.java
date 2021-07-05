package path.analysis.centrality;

import java.util.Arrays;
import java.util.List;

import com.tcb.sensenet.internal.analysis.normalization.NoNormalizationStrategy;
import com.tcb.sensenet.internal.app.AppColumns;
import com.tcb.sensenet.internal.path.analysis.centrality.NodeCentralityAnalyzer;
import com.tcb.sensenet.internal.path.analysis.centrality.WeightedNodeBetweennessCentralityAnalyzer;
import com.tcb.sensenet.internal.path.analysis.centrality.distance.InverseWeightDistanceStrategy;
import com.tcb.sensenet.internal.path.analysis.centrality.distance.PassthroughEdgeDistanceStrategy;
import com.tcb.sensenet.internal.path.analysis.centrality.weight.accumulation.ColumnSumWeightAccumulationStrategy;
import com.tcb.sensenet.internal.path.analysis.centrality.weight.accumulation.UniformWeightAccumulationStrategy;
import com.tcb.sensenet.internal.path.analysis.centrality.weight.negative.IgnoreNegativeValuesStrategy;

public class WeightedNodeBetweennessCentralityAnalyzerTest extends AbstractWeightedNodeCentralityAnalyzerTest {

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
		// ref generated from networkx 2.2
		// betweenness_centrality(g,normalized=False,endpoints=False)
		// ref verified by R 3.1.1 statnet versions: network_1.13.0 sna_2.3-2 
		// betweenness(g,gmode="graph",cmode='undirected')
		List<Double> ref = Arrays.asList(3.5,0.25,0.33,1.75,6.0,0.5,2.33,4.25,0.25,8.83);
		return ref;
	}

	@Override
	protected List<Double> getRefWeightedCentralities() {
		// ref generated from R (network_1.13.0, sna_2.3-2, tnet_3.0.14)
		// betweenness_w(net,directed=F)
		List<Double> ref = Arrays.asList(2.,0.,0.,0.,20.,0.,12.,8.,5.,5.);
		return ref;
	}

	@Override
	protected NodeCentralityAnalyzer getUnweightedAnalyzer() {
		WeightedNodeBetweennessCentralityAnalyzer analysis = new WeightedNodeBetweennessCentralityAnalyzer(
				new UniformWeightAccumulationStrategy(),
				new PassthroughEdgeDistanceStrategy(),
				new NoNormalizationStrategy(),
				new IgnoreNegativeValuesStrategy());
		return analysis;
	}

	@Override
	protected NodeCentralityAnalyzer getWeightedAnalyzer() {
		WeightedNodeBetweennessCentralityAnalyzer analysis = new WeightedNodeBetweennessCentralityAnalyzer(
				new ColumnSumWeightAccumulationStrategy(
						AppColumns.WEIGHT.toString(),
						getRefNetwork().getNetwork()),
				new InverseWeightDistanceStrategy(),
				new NoNormalizationStrategy(),
				new IgnoreNegativeValuesStrategy());
		return analysis;
	}

}
