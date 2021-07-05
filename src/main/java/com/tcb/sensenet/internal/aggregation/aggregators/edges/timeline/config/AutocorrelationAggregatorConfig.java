package com.tcb.sensenet.internal.aggregation.aggregators.edges.timeline.config;

import com.tcb.sensenet.internal.aggregation.aggregators.edges.timeline.AutocorrelationAggregator;
import com.tcb.sensenet.internal.aggregation.aggregators.edges.timeline.MetaTimelineAggregator;
import com.tcb.sensenet.internal.aggregation.methods.AggregationMethod;
import com.tcb.sensenet.internal.aggregation.methods.ErrorMethod;
import com.tcb.sensenet.internal.analysis.autocorrelation.replicas.ReplicaAutocorrelationTimeWeightMethod;
import com.tcb.sensenet.internal.analysis.autocorrelation.replicas.strategy.AutocorrelationTimeWeightStrategy;
import com.tcb.sensenet.internal.analysis.autocorrelation.replicas.strategy.AverageAutocorrelationTimeWeightStrategy;
import com.tcb.sensenet.internal.analysis.autocorrelation.replicas.strategy.ChannelAutocorrelationTimeWeightStrategy;
import com.tcb.sensenet.internal.analysis.autocorrelation.replicas.strategy.MaxAutocorrelationTimeWeightStrategy;
import com.tcb.sensenet.internal.analysis.autocorrelation.replicas.strategy.MinAutocorrelationTimeWeightStrategy;

public class AutocorrelationAggregatorConfig implements MetaTimelineAggregatorConfig {

	private final AggregationMethod aggregationMethod = ErrorMethod.AUTOCORRELATION;
	private Integer blocks;
	private ReplicaAutocorrelationTimeWeightMethod timeMergeMethod;

	public AutocorrelationAggregatorConfig(
			Integer replicas,
			ReplicaAutocorrelationTimeWeightMethod timeMergeMethod){
		this.blocks = replicas;
		this.timeMergeMethod = timeMergeMethod;
	}
	
	
	@Override
	public MetaTimelineAggregator createAggregator() {
		AutocorrelationTimeWeightStrategy mergeStrategy = getTimeMergeStrategy();
		return new AutocorrelationAggregator(blocks, mergeStrategy);
	}

	private AutocorrelationTimeWeightStrategy getTimeMergeStrategy(){
		switch(timeMergeMethod){
		case MIN: return new MinAutocorrelationTimeWeightStrategy();
		case MAX: return new MaxAutocorrelationTimeWeightStrategy();
		case AVERAGE: return new AverageAutocorrelationTimeWeightStrategy();
		default: throw new IllegalArgumentException();
		}
	}

	@Override
	public AggregationMethod getMethod() {
		return aggregationMethod;
	}
	
}
