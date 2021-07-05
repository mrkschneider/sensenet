package com.tcb.sensenet.internal.init.timeline;

import java.util.Arrays;
import java.util.Map;
import java.util.Map.Entry;

import org.cytoscape.model.CyEdge;

import com.tcb.atoms.interactions.Interaction;
import com.tcb.sensenet.internal.meta.timeline.MetaTimeline;
import com.tcb.sensenet.internal.meta.timeline.MetaTimelineImpl;
import com.tcb.sensenet.internal.meta.timeline.TimelineStore;

public class TimelineStoreFactory {
	public TimelineStore create(Map<Interaction,CyEdge> edges, boolean copyTimeline){
		TimelineStore timelineStore = new TimelineStore();
		for(Entry<Interaction,CyEdge> entry:edges.entrySet()){
			CyEdge edge = entry.getValue();
			Interaction interaction = entry.getKey();
			double[] data = interaction.getTimeline().getData();
			if(copyTimeline) data = Arrays.copyOf(data, data.length);
			MetaTimeline metaTimeline = MetaTimelineImpl.create(data);
			timelineStore.put(edge, metaTimeline);
		}
		return timelineStore;
	}
}
