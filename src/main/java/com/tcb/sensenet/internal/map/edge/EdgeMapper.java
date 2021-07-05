package com.tcb.sensenet.internal.map.edge;

import java.util.Optional;

import org.cytoscape.model.CyEdge;

import com.tcb.sensenet.internal.meta.network.MetaNetwork;

public interface EdgeMapper {
	public Optional<CyEdge> getMapped(CyEdge a, MetaNetwork metaNetwork);
}
