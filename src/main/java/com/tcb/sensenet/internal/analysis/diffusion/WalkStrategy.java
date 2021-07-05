package com.tcb.sensenet.internal.analysis.diffusion;

import java.util.List;

import org.cytoscape.model.CyNode;

import com.tcb.cytoscape.cyLib.cytoApiWrappers.CyNetworkAdapter;

public interface WalkStrategy {
	
	public static interface Run {
		// returns null if walk is finished
		public CyNode next(CyNetworkAdapter network, CyNode node);
		public List<CyNode> getVisited();
		public Integer getSteps();
		public Double getWeight(CyNetworkAdapter network, CyNode node);
		public CyNode getSource();
		
	}
	
	
	
	
	public Run createRun();	
}
