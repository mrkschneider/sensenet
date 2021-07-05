package network;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.cytoscape.model.CyNode;
import org.cytoscape.model.NetworkTestSupport;

import com.tcb.cytoscape.cyLib.cytoApiWrappers.CyNetworkAdapter;

public class AdjacencyMatrixNetworkFactory {
	private final NetworkTestSupport fac = new NetworkTestSupport();
	
	public CyNetworkAdapter create(Integer[][] adjacencyMatrix){
		CyNetworkAdapter network = new CyNetworkAdapter(fac.getNetwork());
		for(int i=0;i<adjacencyMatrix.length;i++){
			network.addNode();
		}
		
		List<CyNode> nodes = network.getNodeList().stream()
				.sorted(Comparator.comparing(n -> n.getSUID()))
				.collect(Collectors.toList());
		
		for(int i=0;i<adjacencyMatrix.length;i++){
			for(int j=i;j<adjacencyMatrix[0].length;j++){
				int number = adjacencyMatrix[i][j];
				CyNode source = nodes.get(i);
				CyNode target = nodes.get(j);
				for(int k=0;k<number;k++){
					network.addEdge(source, target, false);
				}		
			}
		}
		return network;
	}
	
	public NetworkTestSupport getTestSupport(){
		return fac;
	}
}
