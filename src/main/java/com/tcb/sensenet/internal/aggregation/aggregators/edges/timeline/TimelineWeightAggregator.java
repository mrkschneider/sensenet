package com.tcb.sensenet.internal.aggregation.aggregators.edges.timeline;

import com.tcb.sensenet.internal.meta.timeline.MetaTimeline;
import com.tcb.sensenet.internal.util.ObjMap;

public class TimelineWeightAggregator implements MetaTimelineAggregator {
	
	@Override
	public ObjMap aggregate(MetaTimeline metaTimeline) {
		ObjMap results = new TimelineWeightAnalysis().analyse(metaTimeline);
		return results;
	}

}
