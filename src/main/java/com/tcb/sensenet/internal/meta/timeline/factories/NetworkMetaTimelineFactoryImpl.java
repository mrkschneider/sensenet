package com.tcb.sensenet.internal.meta.timeline.factories;

import java.util.Arrays;
import java.util.List;

import org.cytoscape.model.CyEdge;

import com.tcb.sensenet.internal.app.AppColumns;
import com.tcb.sensenet.internal.meta.network.MetaNetwork;
import com.tcb.sensenet.internal.meta.timeline.MetaTimeline;
import com.tcb.sensenet.internal.meta.timeline.TimelineManager;
import com.tcb.sensenet.internal.meta.timeline.TimelineStore;

public class NetworkMetaTimelineFactoryImpl implements NetworkMetaTimelineFactory {
	
	private TimelineManager timelineManager;
	private MetaTimelineFactory fac;

	public NetworkMetaTimelineFactoryImpl(MetaTimelineFactory fac, TimelineManager timelineManager){
		this.fac = fac;
		this.timelineManager = timelineManager;
	}
	
	@Override
	public MetaTimeline create(CyEdge e, MetaNetwork metaNetwork) {
		double[][] timelines = getTimelines(e,metaNetwork);
		return fac.create(timelines);
	}
	
	protected double[][] getTimelines(CyEdge edge, MetaNetwork metaNetwork){
		TimelineStore timelineStore = timelineManager.get(metaNetwork);
		List<CyEdge> subEdges = null;
		
		if(isMetaEdge(edge,metaNetwork)){
			subEdges = metaNetwork.getSubedges(edge);
		} else {
			subEdges = Arrays.asList(edge);
		}
		
		return subEdges.stream()
				.map(e -> timelineStore.get(e))
				.map(t -> t.getData())
				.toArray(double[][]::new);
					
	}
	
	private boolean isMetaEdge(CyEdge edge, MetaNetwork metaNetwork){
		return metaNetwork.getHiddenRow(edge).get(AppColumns.IS_METAEDGE, Boolean.class);
	}

	@Override
	public MetaTimelineFactory getMetaTimelineFactory() {
		return fac;
	}
}
