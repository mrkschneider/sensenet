package com.tcb.sensenet.internal.aggregation.aggregators.nodes;

import org.cytoscape.model.CyNode;

import com.tcb.cytoscape.cyLib.cytoApiWrappers.CyNetworkAdapter;

public interface NodeAggregator<T> {
	public T aggregate(CyNode node, CyNetworkAdapter network);
}
