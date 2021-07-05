package com.tcb.sensenet.internal.task.cluster;

import com.tcb.cluster.limit.ClusterLimit;
import com.tcb.sensenet.internal.aggregation.methods.timeline.FrameWeightMethod;
import com.tcb.sensenet.internal.analysis.cluster.ClusterLimitConfig;
import com.tcb.sensenet.internal.analysis.cluster.ClusterMethod;
import com.tcb.sensenet.internal.analysis.cluster.ClustererConfig;
import com.tcb.sensenet.internal.analysis.cluster.ClustererFactory;


public class ClusteringConfigImpl implements ClusteringConfig {
	private ClusterLimitConfig limitConfig;
	private Integer sieve;
	private FrameWeightMethod weightMethod;
	private ClustererConfig clustererConfig;

	public ClusteringConfigImpl(
					ClustererConfig clustererConfig,
					ClusterLimitConfig limitConfig,
					Integer sieve,
					FrameWeightMethod weightMethod){
		this.clustererConfig = clustererConfig;
		this.limitConfig = limitConfig;
		this.sieve = sieve;
		this.weightMethod = weightMethod;
	}
	
	@Override
	public ClustererFactory getClustererFactory() {
		return clustererConfig.getClustererFactory();
	}

	@Override
	public ClusterLimit getClusterLimit() {
		return limitConfig.getClusterLimit();
	}

	@Override
	public Integer getSieve() {
		return sieve;
	}

	@Override
	public ClusterMethod getClusterMethod() {
		return clustererConfig.getClusterMethod();
	}

	@Override
	public FrameWeightMethod getWeightMethod() {
		return weightMethod;
	}
}
