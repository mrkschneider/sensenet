package com.tcb.sensenet.internal.analysis.correlation;

import com.tcb.sensenet.internal.analysis.meta.MetaTimelineStatistics;
import com.tcb.sensenet.internal.meta.timeline.MetaTimeline;

public class PearsonCorrelationStrategy implements TimelineCorrelationStrategy {
	
	@Override
	public Double getCorrelation(MetaTimeline a, MetaTimeline b) {
		return MetaTimelineStatistics.getCorrelation(a, b);
	}
}
