package com.tcb.sensenet.internal.analysis.cluster;

import com.tcb.cluster.limit.ClusterLimit;
import com.tcb.cluster.limit.EpsilonLimit;

public class EpsilonClusterLimitConfig implements ClusterLimitConfig {

	private double limit;
	private final ClusterLimitMethod method = ClusterLimitMethod.EPSILON;

	public EpsilonClusterLimitConfig(double limit){
		this.limit = limit;
	}
	
	@Override
	public ClusterLimit getClusterLimit() {
		return new EpsilonLimit(limit);
	}

	@Override
	public ClusterLimitMethod getMethod() {
		return method;
	}

}
