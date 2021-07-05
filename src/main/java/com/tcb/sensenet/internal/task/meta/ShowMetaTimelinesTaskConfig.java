package com.tcb.sensenet.internal.task.meta;


import com.google.auto.value.AutoValue;
import com.tcb.sensenet.internal.aggregation.methods.timeline.FrameWeightMethod;
import com.tcb.sensenet.internal.log.TaskLogType;
import com.tcb.cytoscape.cyLib.log.ParameterReporter;

@AutoValue
public abstract class ShowMetaTimelinesTaskConfig implements ParameterReporter {

	public static ShowMetaTimelinesTaskConfig create(FrameWeightMethod weightMethod){
		return new AutoValue_ShowMetaTimelinesTaskConfig(weightMethod);
	}
	
	public abstract FrameWeightMethod getWeightMethod();
	
	public TaskLogType getTaskLogType(){
		return TaskLogType.TIMELINE;
	}
	
}
