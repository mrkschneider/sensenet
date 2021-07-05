package com.tcb.sensenet.internal.task.weighting;

import org.cytoscape.work.AbstractTask;
import org.cytoscape.work.TaskMonitor;

import com.tcb.sensenet.internal.aggregation.aggregators.edges.TimelineWeightMethod;
import com.tcb.sensenet.internal.aggregation.methods.timeline.FrameWeightMethod;
import com.tcb.sensenet.internal.app.AppColumns;
import com.tcb.sensenet.internal.meta.network.MetaNetwork;
import com.tcb.sensenet.internal.meta.network.MetaNetworkManager;
import com.tcb.sensenet.internal.meta.timeline.factories.MetaTimelineFactoryManager;

public class InitAverageFrameWeightingTask extends AbstractTask {

	private MetaTimelineFactoryManager timepointAggregatorManager;
	private FrameWeightMethod weightMethod;
	private MetaNetworkManager metaNetworkManager;

	public InitAverageFrameWeightingTask(MetaNetworkManager metaNetworkManager, 
			MetaTimelineFactoryManager timepointAggregatorManager,
			FrameWeightMethod weightMethod){
		this.metaNetworkManager = metaNetworkManager;
		this.timepointAggregatorManager = timepointAggregatorManager;
		this.weightMethod = weightMethod;
	}
	
	
	@Override
	public void run(TaskMonitor taskMonitor) throws Exception {
		MetaNetwork metaNetwork = metaNetworkManager.getCurrentMetaNetwork();
		metaNetwork.getHiddenDataRow().set(AppColumns.METATIMELINE_TYPE, weightMethod.name());
		metaNetwork.getHiddenDataRow().set(AppColumns.AGGREGATION_MODE, TimelineWeightMethod.AVERAGE_FRAME.name());
		timepointAggregatorManager.reset();
	}
}
