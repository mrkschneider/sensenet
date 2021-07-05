package com.tcb.sensenet.internal.app;

import java.io.Serializable;
import java.lang.reflect.Field;

import com.tcb.sensenet.internal.analysis.cluster.ClusteringStoreManager;
import com.tcb.sensenet.internal.analysis.cluster.TreeClusteringStoreManager;
import com.tcb.sensenet.internal.layout.NodePositionStoreManager;
import com.tcb.sensenet.internal.log.TaskLogManager;
import com.tcb.sensenet.internal.meta.network.MetaNetworkManager;
import com.tcb.sensenet.internal.meta.settings.MetaNetworkSettingsManager;
import com.tcb.sensenet.internal.meta.timeline.TimelineManager;
import com.tcb.sensenet.internal.meta.view.MetaNetworkViewManager;
import com.tcb.sensenet.internal.util.BasicMapManager;

public class AppPersistentState implements Serializable {
	private static final long serialVersionUID = 1L;
	
	public MetaNetworkManager metaNetworkManager;
	public MetaNetworkSettingsManager networkSettingsManager;
	public NodePositionStoreManager nodePositionStoreManager;
	public TimelineManager timelineManager;
	public TaskLogManager logManager;
	public ClusteringStoreManager clusteringStoreManager;
	public TreeClusteringStoreManager treeClusteringStoreManager;
	
	public AppPersistentState(
			MetaNetworkManager metaNetworkManager,
			MetaNetworkSettingsManager networkSettingsManager,
			NodePositionStoreManager nodePositionStoreManager,
			TimelineManager timelineManager,
			TaskLogManager logManager,
			ClusteringStoreManager clusteringStoreManager,
			TreeClusteringStoreManager treeClusteringStoreManager){
		this.metaNetworkManager = metaNetworkManager;
		this.networkSettingsManager = networkSettingsManager;
		this.nodePositionStoreManager = nodePositionStoreManager;
		this.timelineManager = timelineManager;
		this.logManager = logManager;
		this.clusteringStoreManager = clusteringStoreManager;
		this.treeClusteringStoreManager = treeClusteringStoreManager;
	}
	
	public void update(AppPersistentState other) throws IllegalAccessException{		
		this.metaNetworkManager.putAll(other.metaNetworkManager.getData());
		this.networkSettingsManager.putAll(other.networkSettingsManager.getData());
		this.nodePositionStoreManager.putAll(other.nodePositionStoreManager.getData());
		this.timelineManager.putAll(other.timelineManager.getData());
		this.clusteringStoreManager.putAll(other.clusteringStoreManager.getData());
		this.treeClusteringStoreManager.putAll(other.treeClusteringStoreManager.getData());
		
		this.logManager.putAll(other.logManager.getData());
		this.logManager.setGlobalLogs(other.logManager.getGlobalLogs());
	}
}
