package com.tcb.sensenet.internal.path;

import java.util.List;

import org.cytoscape.model.CyNode;

import com.tcb.sensenet.internal.util.CancelledException;

public interface PathSearcher {
	public List<Path> getPaths(CyNode source, CyNode target) throws CancelledException;
	public void cancel();
}
