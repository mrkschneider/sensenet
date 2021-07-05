package com.tcb.sensenet.internal.aggregation.aggregators.edges.timeline.config;

import com.tcb.sensenet.internal.aggregation.aggregators.edges.timeline.AutocorrelationAggregator;
import com.tcb.sensenet.internal.aggregation.aggregators.edges.timeline.DivergenceAggregator;
import com.tcb.sensenet.internal.aggregation.aggregators.edges.timeline.EntropyAggregator;
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
import com.tcb.sensenet.internal.analysis.entropy.EntropyMethod;
import com.tcb.sensenet.internal.util.ObjMap;

public class EntropyAggregatorConfig implements MetaTimelineAggregatorConfig {
		
	private EntropyMethod method;

	public EntropyAggregatorConfig(EntropyMethod method){
		this.method = method;
	}
	
	
	@Override
	public MetaTimelineAggregator createAggregator() {
		return new EntropyAggregator();
	}

	@Override
	public AggregationMethod getMethod() {
		return method;
	}
	
}
