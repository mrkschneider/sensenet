package com.tcb.sensenet.internal.analysis.matrix.weight;

import org.cytoscape.model.CyEdge;

import com.tcb.sensenet.internal.meta.network.MetaNetwork;
import com.tcb.sensenet.internal.meta.timeline.MetaTimeline;
import com.tcb.sensenet.internal.meta.timeline.factories.NetworkMetaTimelineFactory;
import com.tcb.cytoscape.cyLib.data.Columns;

public class TimeFrameEdgeWeighter implements EdgeWeighter {

	private MetaNetwork metaNetwork;
	private NetworkMetaTimelineFactory metaTimelineFactory;
	private Integer frame;

	public TimeFrameEdgeWeighter(
			Integer frame,
			MetaNetwork metaNetwork,
			NetworkMetaTimelineFactory metaTimelineFactory){
		this.metaNetwork = metaNetwork;
		this.metaTimelineFactory = metaTimelineFactory;
		this.frame = frame;
	}
	
	@Override
	public Double weight(CyEdge edge) {
		MetaTimeline metaTimeline = metaTimelineFactory.create(edge, metaNetwork);
		return (double) metaTimeline.get(frame);
	}

}
