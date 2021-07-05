package com.tcb.sensenet.internal.path.analysis.centrality;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.cytoscape.model.CyNode;

import com.tcb.sensenet.internal.util.ObjMap;
import com.tcb.cytoscape.cyLib.cytoApiWrappers.CyNetworkAdapter;

public interface NodeCentralityAnalyzer {
	public Map<CyNode,ObjMap> analyze(CyNetworkAdapter network);
	public void cancel();
}
