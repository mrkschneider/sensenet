package com.tcb.sensenet.internal.analysis.correlation;

import org.cytoscape.model.CyEdge;

import com.tcb.sensenet.internal.meta.network.MetaNetwork;
import com.tcb.sensenet.internal.meta.timeline.MetaTimeline;
import com.tcb.sensenet.internal.meta.timeline.factories.NetworkMetaTimelineFactory;

public class EdgeTimelineCorrelationStrategy implements EdgeCorrelationStrategy {

	private MetaNetwork metaNetwork;
	private NetworkMetaTimelineFactory metaTimelineFactory;
	private TimelineCorrelationStrategy timelineCorrelationStrategy;

	public EdgeTimelineCorrelationStrategy(
			TimelineCorrelationStrategy timelineCorrelationStrategy,
			MetaNetwork metaNetwork,
			NetworkMetaTimelineFactory metaTimelineFactory){
		this.metaNetwork = metaNetwork;
		this.timelineCorrelationStrategy = timelineCorrelationStrategy;
		this.metaTimelineFactory = metaTimelineFactory;
	}
	
	@Override
	public Double getCorrelation(CyEdge a, CyEdge b) {
		MetaTimeline timelineA = metaTimelineFactory.create(a, metaNetwork);
		MetaTimeline timelineB = metaTimelineFactory.create(b, metaNetwork);
		Double corr = timelineCorrelationStrategy.getCorrelation(timelineA, timelineB);
		return corr;
	}

}
