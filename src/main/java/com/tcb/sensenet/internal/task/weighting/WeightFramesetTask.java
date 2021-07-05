package com.tcb.sensenet.internal.task.weighting;

import java.util.List;

import org.cytoscape.work.AbstractTask;
import org.cytoscape.work.SynchronousTaskManager;
import org.cytoscape.work.TaskIterator;
import org.cytoscape.work.TaskMonitor;

import com.tcb.sensenet.internal.aggregation.aggregators.edges.timeline.TimelineWeightAnalysis;
import com.tcb.sensenet.internal.aggregation.aggregators.edges.timeline.config.MetaTimelineAggregatorConfig;
import com.tcb.sensenet.internal.aggregation.aggregators.edges.timeline.config.SubTimelineWeightAggregatorConfig;
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


public class WeightFramesetTask extends AbstractTask {

	private SynchronousTaskManager taskManager;
	private MetaNetworkManager metaNetworkManager;
	private MetaTimelineFactoryManager metaTimelineFactoryManager;
	private TaskLogManager logManager;
	private List<Integer> selectedFrames;

	public WeightFramesetTask(List<Integer> selectedFrames,
			MetaNetworkManager metaNetworkManager,
			MetaTimelineFactoryManager timepointAggregatorManager,
			TaskLogManager logManager,
			SynchronousTaskManager taskManager){
		this.selectedFrames = selectedFrames;
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
		
		MetaTimelineAggregatorConfig aggregatorConfig = 
				new SubTimelineWeightAggregatorConfig(selectedFrames);
		RowWriter writer = new WeightWriter(
				AppColumns.WEIGHT, AppColumns.STANDARD_DEVIATION);
		
		MetaTimelineAggregationTaskConfig config = 
				new MetaTimelineAggregationTaskConfig(
						aggregatorConfig,
						method,
						writer,
						TaskLogType.WEIGHT);
		
		ObjMap m = new ObjMap();
		
		TaskIterator it = new TaskIterator();
		it.append(
				new MetaTimelineAggregateTask(m,
						metaNetworkManager, metaTimelineFactoryManager, logManager,
						config));
		taskManager.execute(it);
	}
	
	public static void checkFramesAreValid(MetaNetwork metaNetwork, List<Integer> selectedFrames){
		Integer timelineLength = metaNetwork.getHiddenDataRow().get(AppColumns.TIMELINE_LENGTH, Integer.class);
		for(Integer i:selectedFrames){
			if(i < 0 || i >= timelineLength){
				throw new IllegalArgumentException("Invalid frame");
			}
		}
	}
	
			
}
