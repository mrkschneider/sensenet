package com.tcb.sensenet.internal.analysis.autocorrelation.replicas;

import java.util.List;
import java.util.stream.Collectors;

import com.tcb.sensenet.internal.analysis.autocorrelation.AutocorrelationAnalysisAdapter;
import com.tcb.sensenet.internal.util.DoubleUtil;
import com.tcb.sensenet.internal.util.ObjMap;

public class ReplicaLifetimeAnalysis {
	
	private Integer blocks;
	private Double regressionLimit;

	public ReplicaLifetimeAnalysis(Integer blocks, Double regressionLimit){
		this.blocks = blocks;
		this.regressionLimit = regressionLimit;
	}
	
	public ObjMap getLifetime(List<Double> observations){
		List<AutocorrelationAnalysisAdapter> autocorrelations = 
				new ReplicaLifetimeFactory(regressionLimit).create(observations, blocks);
		Double lifetime = autocorrelations.stream()
				.map(a -> a.getAutocorrelationTime())
				.filter(d -> !Double.isNaN(d))
				.collect(Collectors.averagingDouble(d -> d));
		ObjMap results = new ObjMap();
		results.put("lifetime", lifetime);
		return results;
	}
}
