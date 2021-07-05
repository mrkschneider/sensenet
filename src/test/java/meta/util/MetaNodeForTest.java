package meta.util;

import org.cytoscape.model.CyNetwork;
import org.cytoscape.model.CyNode;
import org.cytoscape.model.CyRow;

import com.tcb.sensenet.internal.meta.node.NodeType;

public class MetaNodeForTest {
	
	public static CyNode create(CyNetwork network, String nodeGroup) throws Exception {
				
		CyNode node = network.addNode();
		CyRow row = network.getRow(node);
		
		
		row.set("shared name", nodeGroup);
		row.set("type", NodeType.Metanode.toString());
		
		
				
		return node;
		
		
	}
}
