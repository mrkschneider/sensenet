package com.tcb.sensenet.internal.meta.timeline.factories;

import org.cytoscape.model.CyEdge;

import com.tcb.sensenet.internal.meta.network.MetaNetwork;
import com.tcb.sensenet.internal.meta.timeline.MetaTimeline;

public interface NetworkMetaTimelineFactory {
	public MetaTimeline create(CyEdge e, MetaNetwork metaNetwork);
	public MetaTimelineFactory getMetaTimelineFactory();
}
