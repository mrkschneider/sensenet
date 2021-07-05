package path.analysis.centrality;

public class MultiEdgeWeightedNodeBetweennessCentralityAnalyzerTest extends WeightedNodeBetweennessCentralityAnalyzerTest {

	/* Hacky way to test behaviour for networks with multiple edges. The idea is that 
	 * when the summed column weights equal the original weights, the centralities should be the same
	 */
	
	private Integer[][] matrix = {
			{0,0,2,0,0,1,1,0,0,1},
			{0,0,0,0,4,0,0,0,0,1},
			{0,0,0,0,0,0,1,1,0,0},
			{0,0,0,0,1,1,0,1,0,1},
			{0,0,0,0,0,0,1,1,1,0},
			{0,0,0,0,0,0,0,0,0,1},
			{0,0,0,0,0,0,0,1,0,0},
			{0,0,0,0,0,0,0,0,0,1},
			{0,0,0,0,0,0,0,0,0,3},
			{0,0,0,0,0,0,0,0,0,0}};
	private Double[] weights = {
			0.6,0.4,
			1.0,
			2.0,
			0.2,
			2.0,1.0,1.0,2.0,
			2.0,
			1.0,
			0.5,
			0.1,
			0.01,
			3.0,
			1.0,
			2.0,
			3.0,
			4.0,
			1.0,
			0.7,
			2.0,
			1.5,1.4,0.2
	};
	
	@Override
	protected Integer[][] getAdjacencyMatrix() {
		return matrix;
	}

	@Override
	protected Double[] getWeights() {
		return weights;
	}
	
}
