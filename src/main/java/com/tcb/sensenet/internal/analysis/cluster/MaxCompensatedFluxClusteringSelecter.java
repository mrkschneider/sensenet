package com.tcb.sensenet.internal.analysis.cluster;

import java.util.Collections;
import java.util.List;

import com.google.common.collect.ImmutableList;
import com.tcb.cluster.Cluster;

public class MaxCompensatedFluxClusteringSelecter implements ClusteringSelecter {

	@Override
	public List<Cluster> select(List<List<Cluster>> clusterings) {
		List<ClusterAnalysis> analyses = getAnalyses(clusterings);
		ClusterAnalysis maxFluxAnalysis = getMaxFluxAnalysis(analyses);
		return maxFluxAnalysis.getClusters();
	}

	private List<ClusterAnalysis> getAnalyses(List<List<Cluster>> clusters){
		return clusters.stream()
				.map(l -> new ClusterAnalysis(l))
				.collect(ImmutableList.toImmutableList());
	}
	
	private ClusterAnalysis getMaxFluxAnalysis(List<ClusterAnalysis> analyses){
		List<Double> flux = analyses.stream()
				.map(a -> a.getCompensatedFlux())
				.collect(ImmutableList.toImmutableList());
		int maxFluxIndex = flux.indexOf(Collections.max(flux));
		return analyses.get(maxFluxIndex);
	}
}
