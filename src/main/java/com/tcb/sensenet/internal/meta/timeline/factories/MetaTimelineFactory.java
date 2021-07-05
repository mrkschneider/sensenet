package com.tcb.sensenet.internal.meta.timeline.factories;

import java.util.List;

import com.tcb.sensenet.internal.meta.timeline.MetaTimeline;

public interface MetaTimelineFactory {
	
	public MetaTimeline createFromStrings(List<String> timelines);
	public MetaTimeline create(double[][] timelines);
	public MetaTimeline create(List<MetaTimeline> timelines);
	
}
