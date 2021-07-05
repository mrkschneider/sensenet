package com.tcb.sensenet.internal.task.weighting;

import org.cytoscape.work.AbstractTask;
import org.cytoscape.work.TaskMonitor;

import com.tcb.sensenet.internal.aggregation.aggregators.edges.TimelineWeightMethod;
import com.tcb.sensenet.internal.aggregation.methods.timeline.FrameWeightMethod;
import com.tcb.sensenet.internal.app.AppColumns;
import com.tcb.sensenet.internal.meta.network.MetaNetwork;
import com.tcb.sensenet.internal.meta.network.MetaNetworkManager;

public class InitClusterWeightingTask extends AbstractTask {

	private FrameWeightMethod weightMethod;
	private Integer clusterIndex;
	private MetaNetworkManager metaNetworkManager;


	public InitClusterWeightingTask(
			MetaNetworkManager metaNetworkManager,
			FrameWeightMethod aggregationMethod,
			Integer clusterIndex){
		this.metaNetworkManager = metaNetworkManager;
		this.weightMethod = aggregationMethod;
		this.clusterIndex = clusterIndex;
	}
	
	
	@Override
	public void run(TaskMonitor taskMonitor) throws Exception {
		MetaNetwork metaNetwork = metaNetworkManager.getCurrentMetaNetwork();
		metaNetwork.getHiddenDataRow().set(AppColumns.METATIMELINE_TYPE, weightMethod.name());
		metaNetwork.getHiddenDataRow().set(AppColumns.AGGREGATION_MODE,
				TimelineWeightMethod.CLUSTERS.name());
		metaNetwork.getHiddenDataRow().set(AppColumns.SELECTED_CLUSTER_INDEX, clusterIndex);
	}
}
