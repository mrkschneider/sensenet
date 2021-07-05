package com.tcb.sensenet.internal.task.correlation;

import com.google.auto.value.AutoValue;
import com.tcb.sensenet.internal.aggregation.methods.timeline.FrameWeightMethod;
import com.tcb.sensenet.internal.log.TaskLogType;
import com.tcb.sensenet.internal.selection.SingleEdgeSelection;
import com.tcb.cytoscape.cyLib.log.ParameterReporter;

@AutoValue
public abstract class TimelineCorrelationTaskConfig implements ParameterReporter {
	
	public static TimelineCorrelationTaskConfig create(
			SingleEdgeSelection edgeSelection,
			FrameWeightMethod weightMethod){
		return new AutoValue_TimelineCorrelationTaskConfig(edgeSelection, weightMethod);
	}
	
	public abstract SingleEdgeSelection getEdgeSelection();
	public abstract FrameWeightMethod getWeightMethod();
	
	public TaskLogType getTaskLogType(){
		return TaskLogType.CORRELATION;
	}
	
}
