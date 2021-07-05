package com.tcb.sensenet.internal.task.cluster;

import com.tcb.cluster.limit.ClusterLimit;
import com.tcb.sensenet.internal.aggregation.methods.timeline.FrameWeightMethod;
import com.tcb.sensenet.internal.analysis.cluster.ClusterMethod;
import com.tcb.sensenet.internal.analysis.cluster.ClustererFactory;
import com.tcb.sensenet.internal.log.TaskLogType;
import com.tcb.cytoscape.cyLib.log.ParameterReporter;

public interface ClusteringConfig extends ParameterReporter {
	public ClustererFactory getClustererFactory();
	public ClusterLimit getClusterLimit();
	public Integer getSieve();
	public ClusterMethod getClusterMethod();
	public FrameWeightMethod getWeightMethod();
	
	public default TaskLogType getTaskLogType(){
		return TaskLogType.CLUSTER;
	}
}
