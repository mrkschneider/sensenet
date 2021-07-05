package com.tcb.sensenet.internal.events;

import org.cytoscape.event.CyEvent;
import org.cytoscape.model.CyNode;

import com.tcb.cytoscape.cyLib.cytoApiWrappers.CyNetworkAdapter;

public class NodeGroupAboutToCollapseEvent implements CyEvent<CyNode> {
	private CyNetworkAdapter network;
	private CyNode group;
	private boolean collapsing;

	public NodeGroupAboutToCollapseEvent(CyNode group, CyNetworkAdapter network, boolean collapsing){
		this.group = group;
		this.network = network;
		this.collapsing = collapsing;
	}
	
	public CyNetworkAdapter getNetwork(){
		return network;
	}
	
	@Override
	public CyNode getSource(){
		return group;
	}
	
	public boolean collapsing(){
		return collapsing;
	}

	@Override
	public Class<?> getListenerClass() {
		return NodeGroupAboutToCollapseListener.class;
	}
}
