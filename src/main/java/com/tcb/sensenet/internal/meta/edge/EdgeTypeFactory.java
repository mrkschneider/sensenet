package com.tcb.sensenet.internal.meta.edge;

import org.cytoscape.model.CyEdge;
import org.cytoscape.model.CyNode;

import com.tcb.sensenet.internal.app.AppColumns;
import com.tcb.cytoscape.cyLib.cytoApiWrappers.CyRootNetworkAdapter;

public class EdgeTypeFactory {
	private CyRootNetworkAdapter rootNetwork;
	
	public EdgeTypeFactory(CyRootNetworkAdapter rootNetwork){
		this.rootNetwork = rootNetwork;
	}
	
	public EdgeType getEdgeType(CyEdge edge){
		CyNode source = edge.getSource();
		CyNode target = edge.getTarget();
		
		if(isMetanode(source) && isMetanode(target)){
			return EdgeType.MetaToMeta;
		}
		else if (isMetanode(source)){
			return EdgeType.MetaToAtom;
		}
		else if (isMetanode(target)){
			return EdgeType.AtomToMeta;
		}
		else {
			return EdgeType.AtomToAtom;
		}
	}
		
	private boolean isMetanode(CyNode node){
		return rootNetwork.getHiddenRow(node).get(AppColumns.IS_METANODE, Boolean.class);
	}

}	
	