package meta.util;

import org.cytoscape.model.CyNetwork;
import org.cytoscape.model.CyNode;
import org.cytoscape.model.CyRow;

public class NodeForTest {
	public static CyNode create(CyNetwork network, String nodeName, String nodeGroup){
		CyNode node = network.addNode();
		CyRow row = network.getRow(node);
		row.set("type", "node");
		row.set("shared name", nodeName);
		row.set("nodeGroup", nodeGroup);
				
		return node;
		
	}
	
}
