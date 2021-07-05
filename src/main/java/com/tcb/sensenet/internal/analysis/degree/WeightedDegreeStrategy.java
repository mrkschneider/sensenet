package com.tcb.sensenet.internal.analysis.degree;

import org.cytoscape.model.CyNode;

import com.tcb.cytoscape.cyLib.cytoApiWrappers.CyNetworkAdapter;

public interface WeightedDegreeStrategy {
	public Double getDegree(CyNode node, CyNetworkAdapter network);
}
