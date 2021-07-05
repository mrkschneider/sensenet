package com.tcb.sensenet.internal.analysis.autocorrelation.replicas;

import java.util.List;

import com.google.common.collect.ImmutableList;
import com.tcb.sensenet.internal.analysis.autocorrelation.AutocorrelationAnalysisAdapter;
import com.tcb.sensenet.internal.util.Partitioner;
import com.tcb.mdAnalysis.statistics.autocorrelation.Autocorrelation;
import com.tcb.mdAnalysis.statistics.autocorrelation.AutocorrelationAnalysis;
import com.tcb.mdAnalysis.statistics.autocorrelation.ScalarAutocorrelation;

public class ReplicaAutocorrelationFactory {

	private Double regressionLimit = 0.1;
	
	public ReplicaAutocorrelationFactory(Double regressionLimit){
		this.regressionLimit = regressionLimit;
	}
		
	public List<AutocorrelationAnalysisAdapter> create(List<Double> observations, Integer replicas){
		List<List<Double>> timelineBlocks = Partitioner.partition(observations, replicas);
		List<AutocorrelationAnalysisAdapter> result = timelineBlocks.stream()
				.map(t -> createAutocorrelationAdapter(t))
				.collect(ImmutableList.toImmutableList());
		return result;
	}
	
	protected Autocorrelation createAutocorrelation(List<Double> timeline){
		return new ScalarAutocorrelation(timeline);
	}
	
	private AutocorrelationAnalysisAdapter createAutocorrelationAdapter(List<Double> observations){
		Autocorrelation autocorrelation = createAutocorrelation(observations);
		return AutocorrelationAnalysisAdapter.create(autocorrelation, regressionLimit);
	}
	
	
		
}
