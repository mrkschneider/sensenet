package com.tcb.sensenet.internal.meta.timeline.factories;

import com.tcb.sensenet.internal.meta.timeline.MetaTimeline;
import com.tcb.sensenet.internal.meta.timeline.MetaTimelineImpl;



public class MetaSumTimelineFactory extends AbstractMetaTimelineFactory implements MetaTimelineFactory  {

	@Override
	public MetaTimeline create(double[][] timelines) {
		double[] metaTimeline = mergeTimelines(timelines);
		
		return MetaTimelineImpl.create(metaTimeline);
	}

}
