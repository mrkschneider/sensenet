package com.tcb.sensenet.internal.analysis.autocorrelation.replicas.strategy;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import com.google.common.collect.ImmutableList;
import com.tcb.common.util.ListFilter;

import com.tcb.sensenet.internal.analysis.autocorrelation.AutocorrelationAnalysisAdapter;
import com.tcb.sensenet.internal.util.DoubleUtil;

public class ChannelAutocorrelationTimeWeightStrategy implements AutocorrelationTimeWeightStrategy {

	public ChannelAutocorrelationTimeWeightStrategy(){
	}
	
	@Override
	public Double merge(List<AutocorrelationAnalysisAdapter> analyses) {
		Double result = getSlowChannelAutocorrelationTimes(analyses)
				.stream()
				.mapToDouble(d -> d)
				.average()
				.getAsDouble();
		return result;
	}
	
	private List<Double> getSlowChannelAutocorrelationTimes(List<AutocorrelationAnalysisAdapter> 
	analyses){
		ImmutableList.Builder<Double> result = ImmutableList.builder();
		List<Double> autocorrelationTimes = analyses.stream()
				.map(a -> a.getAutocorrelationTime())
				.map(d -> DoubleUtil.replaceNaN(d, 1.0))
				.collect(ImmutableList.toImmutableList());
		final Double maxAutocorrelationTime = Collections.max(autocorrelationTimes);
		for(Double autocorrelationTime:autocorrelationTimes){
			double unexplained = getSecondChannelAutocorrelationTime(autocorrelationTime,maxAutocorrelationTime);
			if(unexplained==0.0) {
				result.add(maxAutocorrelationTime);
				continue;
			}
			double p = getSlowChannelProbability(unexplained,maxAutocorrelationTime);
			double time = (1.0 / p) * maxAutocorrelationTime;
 			result.add(time);
		}
		return result.build();
	}
	
	private double getSlowChannelProbability(Double secondChannelAutocorrelationTime, Double maxAutocorrelationTime){
		double p = secondChannelAutocorrelationTime / (maxAutocorrelationTime + secondChannelAutocorrelationTime);
		return p;
	}
	
	private double getSecondChannelAutocorrelationTime(Double autocorrelationTime, Double maxAutocorrelationTime){
		double delta = maxAutocorrelationTime - autocorrelationTime;
		if(delta < 0.001) return 0.0;
		double unexplained = (autocorrelationTime * maxAutocorrelationTime) / delta;
		return unexplained;
	}
	
	

}
