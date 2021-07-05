package com.tcb.sensenet.internal.analysis.autocorrelation.replicas;

import java.util.List;
import java.util.stream.Collectors;

import com.google.common.collect.ImmutableList;
import com.tcb.sensenet.internal.analysis.autocorrelation.AutocorrelationAnalysisAdapter;
import com.tcb.sensenet.internal.analysis.autocorrelation.replicas.strategy.AutocorrelationTimeWeightStrategy;
import com.tcb.sensenet.internal.meta.timeline.MetaTimeline;
import com.tcb.sensenet.internal.util.DoubleUtil;
import com.tcb.sensenet.internal.util.ObjMap;
import com.tcb.mdAnalysis.statistics.StandardStatistics;

public class ReplicaAutocorrelationErrorAnalysis {
	
	private final Integer blocks;
	private AutocorrelationTimeWeightStrategy timeMerger;
	private Double regressionLimit;

	public ReplicaAutocorrelationErrorAnalysis(
			Integer blocks,
			Double regressionLimit,
			AutocorrelationTimeWeightStrategy timeMerger){
		this.blocks = blocks;
		this.regressionLimit = regressionLimit;
		this.timeMerger = timeMerger;
	}
	
	public ObjMap analyse(MetaTimeline metaTimeline){
		List<Double> observations = metaTimeline.asDoubles();
		List<AutocorrelationAnalysisAdapter> autocorrelations = new ReplicaAutocorrelationFactory(regressionLimit)
				.create(observations, blocks);
		Double autocorrelationTime = timeMerger.merge(autocorrelations);
		Double effectiveSampleSize = observations.size() / autocorrelationTime;
		effectiveSampleSize = Math.max(effectiveSampleSize, 1.0);
		Double error = new StandardStatistics(observations).standardError(effectiveSampleSize);
		ObjMap results = new ObjMap();
		results.put("autocorrelations",autocorrelations);
		results.put("autocorrelationTime",autocorrelationTime);
		results.put("effectiveSampleSize",effectiveSampleSize);
		results.put("error",error);
		return results;
	}
	
}
