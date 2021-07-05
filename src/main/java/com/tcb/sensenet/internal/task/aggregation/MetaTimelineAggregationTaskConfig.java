package com.tcb.sensenet.internal.task.aggregation;

import com.tcb.sensenet.internal.aggregation.aggregators.edges.timeline.MetaTimelineAggregator;
import com.tcb.sensenet.internal.aggregation.aggregators.edges.timeline.config.MetaTimelineAggregatorConfig;
import com.tcb.sensenet.internal.aggregation.aggregators.table.RowWriter;
import com.tcb.sensenet.internal.aggregation.methods.timeline.FrameWeightMethod;
import com.tcb.sensenet.internal.log.TaskLogType;
import com.tcb.cytoscape.cyLib.log.NotReported;
import com.tcb.cytoscape.cyLib.log.ParameterReporter;

public class MetaTimelineAggregationTaskConfig implements ParameterReporter {
	@NotReported
	private RowWriter tableWriter;
	private FrameWeightMethod weightMethod;
	private MetaTimelineAggregatorConfig aggregatorConfig;
	
	@NotReported
	private TaskLogType taskLogType;
	
	public MetaTimelineAggregationTaskConfig(
			MetaTimelineAggregatorConfig aggregatorConfig,
			FrameWeightMethod weightMethod, 
			RowWriter tableWriter,
			TaskLogType taskLogType){
		this.aggregatorConfig = aggregatorConfig;
		this.weightMethod = weightMethod;
		this.tableWriter = tableWriter;
		this.taskLogType = taskLogType;
	}
	
	public FrameWeightMethod getFrameWeightMethod(){
		return weightMethod;
	}
	
	public MetaTimelineAggregator getAggregator(){
		return aggregatorConfig.createAggregator();
	}
	
	public RowWriter getRowWriter(){
		return tableWriter;
	}
	
	public TaskLogType getTaskLogType(){
		return taskLogType;
	}
}
