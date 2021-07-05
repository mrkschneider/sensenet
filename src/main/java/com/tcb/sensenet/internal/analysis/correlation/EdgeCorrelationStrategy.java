package com.tcb.sensenet.internal.analysis.correlation;

import org.cytoscape.model.CyEdge;

public interface EdgeCorrelationStrategy {
	public Double getCorrelation(CyEdge a, CyEdge b);
}
