package com.tcb.sensenet.internal.aggregation.aggregators.edges.timeline;

import com.tcb.sensenet.internal.analysis.autocorrelation.replicas.ReplicaAutocorrelationErrorAnalysis;
import com.tcb.sensenet.internal.analysis.autocorrelation.replicas.strategy.AutocorrelationTimeWeightStrategy;
import com.tcb.sensenet.internal.meta.timeline.MetaTimeline;
import com.tcb.sensenet.internal.util.ObjMap;

public class AutocorrelationAggregator implements MetaTimelineAggregator {
		
	private Integer blocks;
	private AutocorrelationTimeWeightStrategy timeMerger;
	private static final Double regressionLimit = 0.1;
	
	public AutocorrelationAggregator(
			Integer blocks,
			AutocorrelationTimeWeightStrategy timeMerger){
		this.blocks = blocks;
		this.timeMerger = timeMerger;
	}

	@Override
	public ObjMap aggregate(MetaTimeline metaTimeline) {
		ObjMap result = new ReplicaAutocorrelationErrorAnalysis(blocks, regressionLimit, timeMerger)
				.analyse(metaTimeline);
		return result;
	}
	
	
	

		
}
