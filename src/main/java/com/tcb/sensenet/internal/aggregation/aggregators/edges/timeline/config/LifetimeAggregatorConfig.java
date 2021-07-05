package com.tcb.sensenet.internal.aggregation.aggregators.edges.timeline.config;

import com.tcb.sensenet.internal.aggregation.aggregators.edges.timeline.LifetimeAggregator;
import com.tcb.sensenet.internal.aggregation.aggregators.edges.timeline.MetaTimelineAggregator;
import com.tcb.sensenet.internal.aggregation.methods.AggregationMethod;
import com.tcb.sensenet.internal.aggregation.methods.LifetimeMethod;

public class LifetimeAggregatorConfig implements MetaTimelineAggregatorConfig {
	
	private final AggregationMethod aggregationMethod = LifetimeMethod.LIFETIME;
	private Integer blocks;
	

	public LifetimeAggregatorConfig(Integer blocks){
		this.blocks = blocks;
	}
	
	@Override
	public MetaTimelineAggregator createAggregator() {
		return new LifetimeAggregator(blocks);
	}


	@Override
	public AggregationMethod getMethod() {
		return aggregationMethod;
	}
	
}
