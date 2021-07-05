package com.tcb.sensenet.internal.analysis.correlation.difference;

import com.tcb.sensenet.internal.meta.timeline.MetaTimeline;

public interface DifferenceTimelineCorrelationStrategy {
	public Double getCorrelation(MetaTimeline a1, MetaTimeline b1, MetaTimeline a2, MetaTimeline b2); 
}
