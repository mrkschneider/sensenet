package com.tcb.sensenet.internal.task.weighting;

import org.cytoscape.work.AbstractTask;
import org.cytoscape.work.SynchronousTaskManager;
import org.cytoscape.work.TaskIterator;
import org.cytoscape.work.TaskMonitor;

import com.tcb.sensenet.internal.aggregation.aggregators.edges.timeline.TimelineWeightAnalysis;
import com.tcb.sensenet.internal.aggregation.aggregators.edges.timeline.config.MetaTimelineAggregatorConfig;
import com.tcb.sensenet.internal.aggregation.aggregators.edges.timeline.config.TimelineWeightAggregatorConfig;
import com.tcb.sensenet.internal.aggregation.aggregators.table.RowWriter;
import com.tcb.sensenet.internal.aggregation.aggregators.table.WeightWriter;
import com.tcb.sensenet.internal.aggregation.methods.timeline.FrameWeightMethod;
import com.tcb.sensenet.internal.app.AppColumns;
import com.tcb.sensenet.internal.log.TaskLogManager;
import com.tcb.sensenet.internal.log.TaskLogType;
import com.tcb.sensenet.internal.meta.network.MetaNetwork;
import com.tcb.sensenet.internal.meta.network.MetaNetworkManager;
import com.tcb.sensenet.internal.meta.timeline.factories.MetaTimelineFactoryManager;
import com.tcb.sensenet.internal.task.aggregation.MetaTimelineAggregateTask;
import com.tcb.sensenet.internal.task.aggregation.MetaTimelineAggregationTaskConfig;
import com.tcb.sensenet.internal.util.ObjMap;


public class WeightAverageFrameTask extends AbstractTask {

	private SynchronousTaskManager taskManager;
	private MetaNetworkManager metaNetworkManager;
	private MetaTimelineFactoryManager metaTimelineFactoryManager;
	private TaskLogManager logManager;

	public WeightAverageFrameTask(
			MetaNetworkManager metaNetworkManager,
			MetaTimelineFactoryManager timepointAggregatorManager,
			TaskLogManager logManager,
			SynchronousTaskManager taskManager){
		this.taskManager = taskManager;
		this.metaTimelineFactoryManager = timepointAggregatorManager;
		this.metaNetworkManager = metaNetworkManager;
		this.logManager = logManager;
	}
		
	@Override
	public void run(TaskMonitor taskMonitor) throws Exception {
		MetaNetwork metaNetwork = metaNetworkManager.getCurrentMetaNetwork();
		
		FrameWeightMethod method = FrameWeightMethod.valueOf(
				metaNetwork.getHiddenDataRow().get(AppColumns.METATIMELINE_TYPE, String.class));

		MetaTimelineAggregatorConfig aggregatorConfig = new TimelineWeightAggregatorConfig();
		RowWriter writer = new WeightWriter(
				AppColumns.WEIGHT, AppColumns.STANDARD_DEVIATION);
		
		MetaTimelineAggregationTaskConfig config = 
				new MetaTimelineAggregationTaskConfig(
						aggregatorConfig,
						method,
						writer,
						TaskLogType.WEIGHT);
		
		
		TaskIterator it = new TaskIterator();
		ObjMap m = new ObjMap();
		it.append(
				new MetaTimelineAggregateTask(m,metaNetworkManager, metaTimelineFactoryManager, logManager, config));
		taskManager.execute(it);
	}
	
}
