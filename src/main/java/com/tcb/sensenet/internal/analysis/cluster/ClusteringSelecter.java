package com.tcb.sensenet.internal.analysis.cluster;

import java.util.List;

import com.tcb.cluster.Cluster;

public interface ClusteringSelecter {
	public List<Cluster> select(List<List<Cluster>> clusterings);
}
