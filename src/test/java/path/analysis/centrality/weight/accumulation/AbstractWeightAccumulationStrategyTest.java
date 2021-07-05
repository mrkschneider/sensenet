package path.analysis.centrality.weight.accumulation;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.tcb.sensenet.internal.app.AppColumns;
import com.tcb.sensenet.internal.path.analysis.centrality.weight.accumulation.ColumnSumWeightAccumulationStrategy;
import com.tcb.sensenet.internal.path.analysis.centrality.weight.accumulation.WeightAccumulationStrategy;

import network.WeightedTestNetwork;
import network.WeightedTestNetworkFactory;

public abstract class AbstractWeightAccumulationStrategyTest {
		
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
			1.0,1.0,2.0,0.2,6.0,Double.NaN,1.0,0.5,0.1,0.01,
			3.0,1.0,null,3.0,4.0,1.0,0.7,2.0,3.1
	};
	
	private WeightedTestNetwork refNetwork;

	protected WeightedTestNetwork getRefNetwork(){
		return refNetwork;
	}
	
	@Before
	public void setUp() throws Exception {
		this.refNetwork = new WeightedTestNetworkFactory().create(matrix, weights);	
	}

}
