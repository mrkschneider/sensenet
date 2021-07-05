package com.tcb.sensenet.internal.path;

import java.util.Map;

import org.cytoscape.model.CyNode;

import com.tcb.sensenet.internal.util.ObjMap;
import com.tcb.cytoscape.cyLib.cytoApiWrappers.CyNetworkAdapter;

public interface AveragePathLengthAnalyzer {
	public ObjMap analyze(CyNetworkAdapter network);
	public void cancel();
}
