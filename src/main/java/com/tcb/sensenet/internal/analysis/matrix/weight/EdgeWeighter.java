package com.tcb.sensenet.internal.analysis.matrix.weight;

import org.cytoscape.model.CyEdge;

public interface EdgeWeighter {
	public Double weight(CyEdge edge);
}
