package com.tcb.sensenet.internal.aggregation.aggregators.edges.timeline;

import java.util.List;

import com.tcb.sensenet.internal.analysis.autocorrelation.replicas.ReplicaAutocorrelationErrorAnalysis;
import com.tcb.sensenet.internal.analysis.autocorrelation.replicas.strategy.AutocorrelationTimeWeightStrategy;
import com.tcb.sensenet.internal.analysis.divergence.DivergenceStrategy;
import com.tcb.sensenet.internal.analysis.divergence.ReplicaDivergenceAnalysis;
import com.tcb.sensenet.internal.meta.timeline.MetaTimeline;
import com.tcb.sensenet.internal.util.ObjMap;

public class DivergenceAggregator implements MetaTimelineAggregator {
		
	private Integer blocks;
	private DivergenceStrategy divergenceStrategy;
	private Double convergenceLimit;
	
	public DivergenceAggregator(
			DivergenceStrategy divergenceStrategy,
			Integer blocks,
			Double convergenceLimit){
		this.divergenceStrategy = divergenceStrategy;
		this.blocks = blocks;
		this.convergenceLimit = convergenceLimit;
	}

	@Override
	public ObjMap aggregate(MetaTimeline metaTimeline) {
		ReplicaDivergenceAnalysis ana = 
				new ReplicaDivergenceAnalysis(divergenceStrategy);
		ObjMap result = ana.analyse(metaTimeline, blocks, convergenceLimit);
		return result;
	}
	
	
	

		
}
