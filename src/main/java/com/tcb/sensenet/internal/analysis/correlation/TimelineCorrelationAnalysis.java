package com.tcb.sensenet.internal.analysis.correlation;

import java.util.List;

import com.google.common.collect.ImmutableList;
import com.tcb.sensenet.internal.meta.timeline.MetaTimeline;

public class TimelineCorrelationAnalysis {

	private List<Double> mutualInformations;
	private List<Double> correlations;
	
	public static TimelineCorrelationAnalysis calculate(
			MetaTimeline selectedTimeline,
			List<MetaTimeline> timelines){
		ImmutableList.Builder<Double> mutualInformations = ImmutableList.builder();
		ImmutableList.Builder<Double> correlations = ImmutableList.builder();
		
		TimelineCorrelationStrategy pearsonStrategy = new PearsonCorrelationStrategy();
		TimelineCorrelationStrategy mutualInformationStrategy = new MutualInformationCorrelationStrategy();
		
		for(MetaTimeline metaTimeline:timelines){
			Double mutualInformation = mutualInformationStrategy.getCorrelation(selectedTimeline, metaTimeline);
			Double correlation = pearsonStrategy.getCorrelation(selectedTimeline, metaTimeline);
			correlations.add(correlation);
			mutualInformations.add(mutualInformation);
		}
		
		return new TimelineCorrelationAnalysis(
				correlations.build(),
				mutualInformations.build());
	}
	
	private TimelineCorrelationAnalysis(List<Double> correlations, List<Double> mutualInformations){
		this.correlations = correlations;
		this.mutualInformations = mutualInformations;
	}
	
	public List<Double> getMutualInformations(){
		return mutualInformations;
	}
	
	public List<Double> getCorrelations(){
		return correlations;
	}
			
}
