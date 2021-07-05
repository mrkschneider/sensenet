package com.tcb.sensenet.internal.task.path.search;

import org.cytoscape.model.CyNode;

import com.google.auto.value.AutoValue;
import com.tcb.sensenet.internal.path.PathSearcher;
import com.tcb.cytoscape.cyLib.cytoApiWrappers.CyNetworkAdapter;

@AutoValue
public abstract class SearchPathsTaskConfig {
	
	public static SearchPathsTaskConfig create(
			CyNode source,
			CyNode target,
			CyNetworkAdapter network,
			PathSearcher pathSearcher) {
		return new AutoValue_SearchPathsTaskConfig(source,target,network,pathSearcher);
	}
	
	public abstract CyNode getSource();
	public abstract CyNode getTarget();
	public abstract CyNetworkAdapter getNetwork();
	public abstract PathSearcher getPathSearcher();
}
