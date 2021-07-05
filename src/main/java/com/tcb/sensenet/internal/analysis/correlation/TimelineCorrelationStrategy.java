package com.tcb.sensenet.internal.analysis.correlation;

import com.tcb.sensenet.internal.meta.timeline.MetaTimeline;

public interface TimelineCorrelationStrategy {
	public Double getCorrelation(MetaTimeline a, MetaTimeline b);
}
