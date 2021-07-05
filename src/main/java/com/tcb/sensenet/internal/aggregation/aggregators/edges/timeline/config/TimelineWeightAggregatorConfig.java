package com.tcb.sensenet.internal.aggregation.aggregators.edges.timeline.config;

import com.tcb.sensenet.internal.aggregation.aggregators.edges.timeline.MetaTimelineAggregator;
import com.tcb.sensenet.internal.aggregation.aggregators.edges.timeline.TimelineWeightAggregator;
import com.tcb.sensenet.internal.aggregation.aggregators.edges.timeline.TimelineWeightAnalysis;
import com.tcb.sensenet.internal.aggregation.methods.AggregationMethod;
import com.tcb.sensenet.internal.aggregation.methods.timeline.TimelineStatisticsMethod;

public class TimelineWeightAggregatorConfig implements MetaTimelineAggregatorConfig {
	
	private final AggregationMethod aggregationMethod = TimelineStatisticsMethod.TIMELINE_STATISTICS;
	
	@Override
	public MetaTimelineAggregator createAggregator() {
		return new TimelineWeightAggregator();
	}


	@Override
	public AggregationMethod getMethod() {
		return aggregationMethod;
	}
	
}
