package com.tcb.sensenet.internal.aggregation.aggregators.edges.timeline;

import java.util.List;

import com.tcb.sensenet.internal.meta.timeline.MetaTimeline;
import com.tcb.sensenet.internal.meta.timeline.factories.MetaSubTimelineFactory;
import com.tcb.sensenet.internal.util.ObjMap;

public class SubTimelineWeightAggregator implements MetaTimelineAggregator {
	
	private List<Integer> selectedFrames;

	public SubTimelineWeightAggregator(List<Integer> selectedFrames){
		this.selectedFrames = selectedFrames;
	}

	@Override
	public ObjMap aggregate(MetaTimeline metaTimeline) {
		MetaTimeline subTimeline = new MetaSubTimelineFactory(selectedFrames).create(metaTimeline);
		ObjMap results = new TimelineWeightAnalysis().analyse(subTimeline);
		return results;
	}

}
