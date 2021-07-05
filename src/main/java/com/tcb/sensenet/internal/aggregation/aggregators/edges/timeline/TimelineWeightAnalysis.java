package com.tcb.sensenet.internal.aggregation.aggregators.edges.timeline;

import java.util.List;

import com.tcb.sensenet.internal.analysis.meta.MetaTimelineStatistics;
import com.tcb.sensenet.internal.meta.timeline.MetaTimeline;
import com.tcb.sensenet.internal.util.ObjMap;

public class TimelineWeightAnalysis {
	
	public ObjMap analyse(MetaTimeline metaTimeline) {
		ObjMap results = new ObjMap();
		results.put("mean",MetaTimelineStatistics.getAverage(metaTimeline));
		results.put("std",MetaTimelineStatistics.getStandardDeviation(metaTimeline));
		return results;
	}
}
