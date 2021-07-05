package com.tcb.sensenet.internal.meta.timeline.factories;

import java.util.List;

import com.tcb.sensenet.internal.meta.timeline.MetaTimeline;
import com.tcb.sensenet.internal.meta.timeline.MetaTimelineImpl;

public class MetaSubTimelineFactory {

	private List<Integer> selectedFrames;

	public MetaSubTimelineFactory(List<Integer> selectedFrames){
		this.selectedFrames = selectedFrames;
	}
	
	public MetaTimeline create(MetaTimeline parent){
		return createSubTimeline(parent, selectedFrames);
	}
		
	private MetaTimeline createSubTimeline(MetaTimeline parent, List<Integer> selectedFrames){
		double[] data = new double[selectedFrames.size()];
		for(int i=0;i<selectedFrames.size();i++){
			Integer frame = selectedFrames.get(i);
			double v = parent.get(frame);
			data[i] = v;
		}
		return MetaTimelineImpl.create(data);
	}
}
