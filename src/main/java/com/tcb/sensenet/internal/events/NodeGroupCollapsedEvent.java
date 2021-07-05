package com.tcb.sensenet.internal.events;

import org.cytoscape.event.CyEvent;
import org.cytoscape.model.CyNode;

import com.tcb.cytoscape.cyLib.cytoApiWrappers.CyNetworkAdapter;

public class NodeGroupCollapsedEvent implements CyEvent<CyNode>{
	private CyNetworkAdapter network;
	private CyNode group;
	private boolean collapsed;

	public NodeGroupCollapsedEvent(CyNode group, CyNetworkAdapter network, boolean collapsed){
		this.group = group;
		this.network = network;
		this.collapsed = collapsed;
	}
	
	public CyNetworkAdapter getNetwork(){
		return network;
	}
	
	public CyNode getSource(){
		return group;
	}
	
	public boolean collapsed(){
		return collapsed;
	}

	@Override
	public Class<?> getListenerClass() {
		return NodeGroupCollapsedListener.class;
	}
}
