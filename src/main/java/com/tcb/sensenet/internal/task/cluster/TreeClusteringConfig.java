package com.tcb.sensenet.internal.task.cluster;


import com.google.auto.value.AutoValue;
import com.tcb.cluster.limit.ClusterLimit;
import com.tcb.sensenet.internal.aggregation.methods.timeline.FrameWeightMethod;
import com.tcb.sensenet.internal.analysis.cluster.ClusterLimitConfig;
import com.tcb.sensenet.internal.analysis.cluster.ClusterMethod;
import com.tcb.sensenet.internal.analysis.cluster.ClustererConfig;
import com.tcb.sensenet.internal.analysis.cluster.ClustererFactory;
import com.tcb.sensenet.internal.analysis.cluster.TreeClusterMethod;
import com.tcb.sensenet.internal.analysis.cluster.TreeClustererConfig;
import com.tcb.sensenet.internal.analysis.cluster.TreeClustererFactory;
import com.tcb.sensenet.internal.analysis.cluster.TreeClusteringSelectionMethod;
import com.tcb.sensenet.internal.log.TaskLogType;
import com.tcb.cytoscape.cyLib.log.ParameterReporter;

public class TreeClusteringConfig implements ParameterReporter {
	
	private TreeClustererConfig clustererConfig;
	private Integer sieve;
	private FrameWeightMethod weightMethod;
	private TreeClusteringSelectionMethod clusteringSelectionMethod;
	
	public TreeClusteringConfig(
			TreeClustererConfig clustererConfig,
			Integer sieve,
			FrameWeightMethod weightMethod,
			TreeClusteringSelectionMethod clusteringSelectionMethod){
		this.clustererConfig = clustererConfig;
		this.sieve = sieve;
		this.weightMethod = weightMethod;
		this.clusteringSelectionMethod = clusteringSelectionMethod;
	}
	
	public TreeClustererFactory getClustererFactory(){
		return clustererConfig.getClustererFactory();
	};
	
	public Integer getSieve(){
		return sieve;
	};
	
	public TreeClusterMethod getClusterMethod(){
		return clustererConfig.getTreeClusterMethod();
	};
	
	public FrameWeightMethod getWeightMethod(){
		return weightMethod;
	};
	
	public TreeClusteringSelectionMethod getClusteringSelectionMethod(){
		return clusteringSelectionMethod;
	}
	
	public TaskLogType getTaskLogType(){
		return TaskLogType.CLUSTER;
	}
}
