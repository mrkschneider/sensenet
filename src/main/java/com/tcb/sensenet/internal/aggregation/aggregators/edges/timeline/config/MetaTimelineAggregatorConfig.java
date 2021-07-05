package com.tcb.sensenet.internal.aggregation.aggregators.edges.timeline.config;

import com.tcb.sensenet.internal.aggregation.aggregators.edges.timeline.MetaTimelineAggregator;
import com.tcb.sensenet.internal.aggregation.methods.AggregationMethod;

public interface MetaTimelineAggregatorConfig {
	public MetaTimelineAggregator createAggregator();
	public AggregationMethod getMethod();
}
