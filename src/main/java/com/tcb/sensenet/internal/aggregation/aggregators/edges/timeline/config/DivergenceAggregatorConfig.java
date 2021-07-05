package com.tcb.sensenet.internal.aggregation.aggregators.edges.timeline.config;

import com.tcb.sensenet.internal.aggregation.aggregators.edges.timeline.AutocorrelationAggregator;
import com.tcb.sensenet.internal.aggregation.aggregators.edges.timeline.DivergenceAggregator;
import com.tcb.sensenet.internal.aggregation.aggregators.edges.timeline.MetaTimelineAggregator;
import com.tcb.sensenet.internal.aggregation.methods.AggregationMethod;
import com.tcb.sensenet.internal.aggregation.methods.ErrorMethod;
import com.tcb.sensenet.internal.analysis.autocorrelation.replicas.ReplicaAutocorrelationTimeWeightMethod;
import com.tcb.sensenet.internal.analysis.autocorrelation.replicas.strategy.AutocorrelationTimeWeightStrategy;
import com.tcb.sensenet.internal.analysis.autocorrelation.replicas.strategy.AverageAutocorrelationTimeWeightStrategy;
import com.tcb.sensenet.internal.analysis.autocorrelation.replicas.strategy.ChannelAutocorrelationTimeWeightStrategy;
import com.tcb.sensenet.internal.analysis.autocorrelation.replicas.strategy.MaxAutocorrelationTimeWeightStrategy;
import com.tcb.sensenet.internal.analysis.autocorrelation.replicas.strategy.MinAutocorrelationTimeWeightStrategy;
import com.tcb.sensenet.internal.analysis.divergence.DivergenceMethod;
import com.tcb.sensenet.internal.analysis.divergence.DivergenceStrategy;
import com.tcb.sensenet.internal.util.ObjMap;

public class DivergenceAggregatorConfig implements MetaTimelineAggregatorConfig {

	private final DivergenceMethod aggregationMethod;
	private final DivergenceStrategy divergenceStrategy;
	private Integer blocks;
	private Double convergenceLimit;
	
	public DivergenceAggregatorConfig(
			DivergenceMethod divergenceMethod,
			DivergenceStrategy divergenceStrategy,
			Integer blocks,
			Double convergenceLimit){
		this.aggregationMethod = divergenceMethod;
		this.divergenceStrategy = divergenceStrategy;
		this.blocks = blocks;
		this.convergenceLimit = convergenceLimit;
	}
	
	
	@Override
	public MetaTimelineAggregator createAggregator() {
		return new DivergenceAggregator(divergenceStrategy, blocks, convergenceLimit);
	}

	@Override
	public AggregationMethod getMethod() {
		return aggregationMethod;
	}
	
}
