package com.tcb.sensenet.internal.aggregation.aggregators.edges.timeline;

import com.tcb.sensenet.internal.analysis.autocorrelation.replicas.ReplicaLifetimeAnalysis;
import com.tcb.sensenet.internal.meta.timeline.MetaTimeline;
import com.tcb.sensenet.internal.util.ObjMap;

public class LifetimeAggregator implements MetaTimelineAggregator {

	private Integer blocks;
	private static final Double regressionLimit = 0.1;

	public LifetimeAggregator(Integer blocks){
		this.blocks = blocks;
	}
	
	@Override
	public ObjMap aggregate(MetaTimeline metaTimeline) {
		ReplicaLifetimeAnalysis analysis = new ReplicaLifetimeAnalysis(blocks, regressionLimit);
		ObjMap results = analysis.getLifetime(metaTimeline.asDoubles());
		return results;
	}
	
	
		
}
