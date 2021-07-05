package testUtils;

// import NetworkTestSupport was broken in v3.3

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.cytoscape.model.CyEdge;
import org.cytoscape.model.CyNode;

public class DummyNetwork {
	//NetworkTestSupport nts = new NetworkTestSupport();
	//CyNetworkAdapter network = new CyNetworkAdapter(nts.getNetwork());
	private List<CyNode> nodes = new ArrayList<CyNode>();
	private CyEdge edge1_2;
	private CyEdge edge3_1;
	private List<Integer> refNodeDegreeValues = Arrays.asList(2,1,1,0);
	
	
	public DummyNetwork() {
		for(int i=0;i<4;i++){
			//nodes.add(network.addNode());
		}
		//this.edge1_2 = network.addEdge(nodes.get(0), nodes.get(1), false);
		//this.edge3_1 = network.addEdge(nodes.get(2), nodes.get(0), false);
	}
	
	//public CyNetworkAdapter getNetwork(){
		//return network;
	//}
	
	public List<CyNode> getNodes(){
		return nodes;
	}
	
	public List<Integer> getNodeDegreeValues(){
		return refNodeDegreeValues;
	}
	
	public CyEdge getEdge1_2(){
		return edge1_2;
	}
	
	public CyEdge getEdge3_1(){
		return edge3_1;
	}
	
}
