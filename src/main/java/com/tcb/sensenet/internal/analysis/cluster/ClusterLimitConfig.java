package com.tcb.sensenet.internal.analysis.cluster;

import com.tcb.cluster.limit.ClusterLimit;

public interface ClusterLimitConfig {
	public ClusterLimit getClusterLimit();
	public ClusterLimitMethod getMethod();
}
