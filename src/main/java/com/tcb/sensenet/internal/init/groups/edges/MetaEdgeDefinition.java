package com.tcb.sensenet.internal.init.groups.edges;

import org.cytoscape.model.CyEdge;

import com.tcb.sensenet.internal.util.predicates.DiPredicate;
import com.tcb.cytoscape.cyLib.cytoApiWrappers.CyRootNetworkAdapter;

public interface MetaEdgeDefinition extends DiPredicate<CyEdge,CyRootNetworkAdapter>{
	public void setEdgeValid(CyEdge edge, CyRootNetworkAdapter rootNetwork);
}
