package com.tcb.sensenet.internal.map.node;

import java.util.Optional;

import org.cytoscape.model.CyNode;

import com.tcb.sensenet.internal.meta.network.MetaNetwork;

public interface NodeMapper {
	public Optional<CyNode> getMapped(CyNode node, MetaNetwork metaNetwork);
}
