package com.tcb.sensenet.internal.task.divergence;

import com.tcb.sensenet.internal.aggregation.aggregators.edges.timeline.config.MetaTimelineAggregatorConfig;
import com.tcb.sensenet.internal.aggregation.aggregators.table.RowWriter;
import com.tcb.sensenet.internal.aggregation.methods.timeline.FrameWeightMethod;
import com.tcb.sensenet.internal.log.TaskLogType;
import com.tcb.sensenet.internal.task.aggregation.MetaTimelineAggregationTaskConfig;

public class DivergenceTaskConfig extends MetaTimelineAggregationTaskConfig {

	public DivergenceTaskConfig(MetaTimelineAggregatorConfig aggregatorConfig, FrameWeightMethod weightMethod,
			RowWriter tableWriter, TaskLogType taskLogType) {
		super(aggregatorConfig, weightMethod, tableWriter, taskLogType);
	}

}
