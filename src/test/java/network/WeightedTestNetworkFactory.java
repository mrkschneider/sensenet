package network;

import org.cytoscape.model.NetworkTestSupport;

import com.tcb.cytoscape.cyLib.cytoApiWrappers.CyNetworkAdapter;

public class WeightedTestNetworkFactory {
	
	private AdjacencyMatrixNetworkFactory fac = new AdjacencyMatrixNetworkFactory();
	
	public WeightedTestNetwork create(Integer[][] adjacencyMatrix, Double[] weights){
		CyNetworkAdapter network = fac.create(adjacencyMatrix);
		return new WeightedTestNetwork(network,weights);
	}
	
	public NetworkTestSupport getTestSupport() {
		return fac.getTestSupport();
	}
}
