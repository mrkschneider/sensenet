package com.tcb.sensenet.internal.meta.timeline.factories;

import java.util.List;

import com.tcb.sensenet.internal.meta.timeline.MetaTimeline;
import com.tcb.sensenet.internal.meta.timeline.MetaTimelineImpl;

public class EmptyMetaTimelineFactory  {

	public static MetaTimeline create(Integer length){
		double[] data = new double[length];
		return MetaTimelineImpl.create(data);
	}

}
