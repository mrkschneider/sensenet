package meta.util;

import org.cytoscape.model.CyEdge;
import org.cytoscape.model.CyNetwork;
import org.cytoscape.model.CyNode;
import org.cytoscape.model.CyRow;

public class EdgeForTest {
	
	public static CyEdge create(CyNetwork network, CyNode source, CyNode target, String weight) throws Exception {
				
		if(network.containsEdge(source, target)){
			throw new Exception("Edge already exists.");
		}
		CyEdge edge = network.addEdge(source, target, true);
						
		CyRow edgeRow = network.getRow(edge);
		CyRow srcRow = network.getRow(source);
		CyRow targetRow = network.getRow(target);
		String node1Name = srcRow.get("shared name", String.class);
		String node2Name = targetRow.get("shared name", String.class);
		String node1Group = srcRow.get("nodeGroup", String.class);
		String node2Group = targetRow.get("nodeGroup", String.class);
		
		edgeRow.set("node1Group", node1Group);
		edgeRow.set("node2Group", node2Group);
		edgeRow.set("weight", weight);
		edgeRow.set("shared name", node1Name + node2Name);
		return edge;
				
	}
	
	
}
