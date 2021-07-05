package com.tcb.sensenet.internal.task.select;

import java.util.List;
import java.util.Set;

import com.tcb.cluster.limit.ClusterLimit;
import com.tcb.sensenet.internal.aggregation.methods.timeline.FrameWeightMethod;
import com.tcb.sensenet.internal.analysis.cluster.ClusterLimitConfig;
import com.tcb.sensenet.internal.analysis.cluster.ClusterMethod;
import com.tcb.sensenet.internal.analysis.cluster.ClustererConfig;
import com.tcb.sensenet.internal.analysis.cluster.ClustererFactory;
import com.tcb.sensenet.internal.log.TaskLogType;
import com.tcb.cytoscape.cyLib.log.ParameterReporter;


public class SelectResiduesTaskConfig implements ParameterReporter {
	private Set<Integer> resIndices;
	private Set<Integer> labelResIndices;
	private Set<String> resInserts;
	private Set<String> labelResInserts;

	public SelectResiduesTaskConfig(
			Set<Integer> resIndices,
			Set<Integer> labelResIndices,
			Set<String> resInserts,
			Set<String> labelResInserts
			){
		this.resIndices = resIndices;
		this.labelResIndices = labelResIndices;
		this.resInserts = resInserts;
		this.labelResInserts = labelResInserts;
	}
	
	public Set<Integer> getResIndices(){
		return resIndices;
	}
	
	public Set<Integer> getLabelResIndices(){
		return labelResIndices;
	}
	
	public Set<String> getResInserts(){
		return resInserts;
	}
	
	public Set<String> getLabelResInserts(){
		return labelResInserts;
	}
	
	
}
