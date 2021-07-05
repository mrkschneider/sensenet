package com.tcb.sensenet.internal.aggregation.aggregators.edges.timeline.config;

import java.util.List;

import com.tcb.sensenet.internal.aggregation.aggregators.edges.timeline.MetaTimelineAggregator;
import com.tcb.sensenet.internal.aggregation.aggregators.edges.timeline.SubTimelineWeightAggregator;
import com.tcb.sensenet.internal.aggregation.aggregators.edges.timeline.TimelineWeightAnalysis;
import com.tcb.sensenet.internal.aggregation.methods.AggregationMethod;
import com.tcb.sensenet.internal.aggregation.methods.timeline.TimelineStatisticsMethod;

public class SubTimelineWeightAggregatorConfig implements MetaTimelineAggregatorConfig {
	
	private List<Integer> selectedFrames;
	private final AggregationMethod aggregationMethod = TimelineStatisticsMethod.TIMELINE_STATISTICS_SUBSET;

	public SubTimelineWeightAggregatorConfig(List<Integer> selectedFrames){
		this.selectedFrames = selectedFrames;
	}
	
	@Override
	public MetaTimelineAggregator createAggregator() {
		return new SubTimelineWeightAggregator(selectedFrames);
	}


	@Override
	public AggregationMethod getMethod() {
		return aggregationMethod;
	}
	
}
