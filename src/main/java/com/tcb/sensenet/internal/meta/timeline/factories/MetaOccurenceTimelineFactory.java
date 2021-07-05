package com.tcb.sensenet.internal.meta.timeline.factories;

import com.tcb.sensenet.internal.meta.timeline.MetaTimeline;
import com.tcb.sensenet.internal.meta.timeline.MetaTimelineImpl;

public class MetaOccurenceTimelineFactory extends MetaSumTimelineFactory {

	@Override
	public MetaTimeline create(double[][] timelines) {
		double[] metaTimeline = mergeTimelines(timelines);
		
		normalize(metaTimeline);
		return MetaTimelineImpl.create(metaTimeline);
	}
	
	protected void normalize(double[] metaTimeline){
		for(int i=0;i<metaTimeline.length;i++){
			metaTimeline[i] = Math.min(metaTimeline[i],1);
		}
	}
		
}
