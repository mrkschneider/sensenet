package com.tcb.sensenet.internal.analysis.cluster;

import com.tcb.cluster.limit.ClusterLimit;
import com.tcb.cluster.limit.TargetNumberLimit;

public class TargetClusterLimitConfig implements ClusterLimitConfig {

	private int targetNumber;
	private final ClusterLimitMethod method = ClusterLimitMethod.TARGET_NUMBER;
	

	public TargetClusterLimitConfig(int targetNumber){
		this.targetNumber = targetNumber;
	}
	
	@Override
	public ClusterLimit getClusterLimit() {
		return new TargetNumberLimit(targetNumber);
	}

	@Override
	public ClusterLimitMethod getMethod() {
		return method;
	}

}
