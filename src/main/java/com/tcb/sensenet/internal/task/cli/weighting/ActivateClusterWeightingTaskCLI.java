package com.tcb.sensenet.internal.task.cli.weighting;

import org.cytoscape.work.TaskIterator;
import org.cytoscape.work.Tunable;

import com.tcb.cluster.Cluster;
import com.tcb.sensenet.internal.aggregation.methods.timeline.FrameWeightMethod;
import com.tcb.sensenet.internal.app.AppGlobals;
import com.tcb.sensenet.internal.meta.network.MetaNetwork;
import com.tcb.sensenet.internal.task.cli.AbstractWrappedTask;
import com.tcb.sensenet.internal.task.weighting.factories.ActivateClusterWeightingTaskFactory;
import com.tcb.cytoscape.cyLib.util.NullUtil;

public class ActivateClusterWeightingTaskCLI extends AbstractWrappedTask {
	
	@Tunable(description="Frame weight")
	public String weightMethod;
	
	@Tunable(description="Cluster index")
	public Integer clusterIndex;

	public ActivateClusterWeightingTaskCLI(AppGlobals appGlobals){
		super(appGlobals);
	}
	
	@Override
	public TaskIterator createWrappedTasks() {
		NullUtil.requireNonNull(clusterIndex, "Cluster index");
		NullUtil.requireNonNull(weightMethod, "Frame weight");
		
		MetaNetwork metaNetwork = appGlobals.state.metaNetworkManager.getCurrentMetaNetwork();
		Cluster selection = appGlobals.state.clusteringStoreManager
				.get(metaNetwork).get(clusterIndex);
		FrameWeightMethod weightMethodV = FrameWeightMethod.valueOfCLI(weightMethod);
		
		return new ActivateClusterWeightingTaskFactory(appGlobals,weightMethodV, clusterIndex, selection)
		.createTaskIterator();
	}
		

}
