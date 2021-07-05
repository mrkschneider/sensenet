package com.tcb.sensenet.internal.analysis.matrix.weight;

import org.cytoscape.model.CyEdge;

public class UniformEdgeWeighter implements EdgeWeighter {

	@Override
	public Double weight(CyEdge edge) {
		return 1.0;
	}

}
