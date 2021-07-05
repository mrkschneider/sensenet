package com.tcb.sensenet.internal.path;

import java.util.List;

import org.cytoscape.model.CyNode;

import com.tcb.sensenet.internal.util.CancelledException;
import com.tcb.cytoscape.cyLib.cytoApiWrappers.CyNetworkAdapter;

public interface DistanceSearcher {
	public double distance(CyNetworkAdapter network, CyNode source, CyNode target) throws CancelledException;
	public void cancel();
}
