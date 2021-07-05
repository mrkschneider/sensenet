package com.tcb.sensenet.internal.aggregation.aggregators.edges.timeline;

import com.tcb.sensenet.internal.meta.timeline.MetaTimeline;
import com.tcb.sensenet.internal.util.ObjMap;

public interface MetaTimelineAggregator {
	public ObjMap aggregate(MetaTimeline metaTimeline);
}
