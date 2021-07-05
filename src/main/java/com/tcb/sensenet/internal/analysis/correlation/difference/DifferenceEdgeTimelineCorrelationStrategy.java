package com.tcb.sensenet.internal.analysis.correlation.difference;

import java.util.Map;
import java.util.Optional;

import org.cytoscape.model.CyEdge;

import com.tcb.sensenet.internal.analysis.correlation.EdgeCorrelationStrategy;
import com.tcb.sensenet.internal.app.AppColumns;
import com.tcb.sensenet.internal.map.edge.EdgeMapper;
import com.tcb.sensenet.internal.meta.network.MetaNetwork;
import com.tcb.sensenet.internal.meta.timeline.MetaTimeline;
import com.tcb.sensenet.internal.meta.timeline.factories.EmptyMetaTimelineFactory;
import com.tcb.sensenet.internal.meta.timeline.factories.MetaTimelineFactory;
import com.tcb.sensenet.internal.meta.timeline.factories.NetworkMetaTimelineFactory;
import com.tcb.cytoscape.cyLib.data.DefaultColumns;

public class DifferenceEdgeTimelineCorrelationStrategy implements EdgeCorrelationStrategy {

	private DifferenceTimelineCorrelationStrategy strategy;
	private MetaNetwork metaNetwork;
	private MetaNetwork refMetaNetwork;
	private NetworkMetaTimelineFactory metaTimelineFactory;
	private EdgeMapper edgeMapper;

	public DifferenceEdgeTimelineCorrelationStrategy(
			DifferenceTimelineCorrelationStrategy strategy,
			MetaNetwork metaNetwork,
			MetaNetwork refMetaNetwork,
			NetworkMetaTimelineFactory metaTimelineFactory,
			EdgeMapper edgeMapper){
		this.strategy = strategy;
		this.metaNetwork = metaNetwork;
		this.refMetaNetwork = refMetaNetwork;
		this.metaTimelineFactory = metaTimelineFactory;
		this.edgeMapper = edgeMapper;
	}
	
	@Override
	public Double getCorrelation(CyEdge a, CyEdge b) {
		MetaTimeline timelineA = metaTimelineFactory.create(a, metaNetwork);
		MetaTimeline timelineB = metaTimelineFactory.create(b, metaNetwork);
		MetaTimeline refTimelineA = getRefMetaTimeline(a);
		MetaTimeline refTimelineB = getRefMetaTimeline(b);
		Double corr = strategy.getCorrelation(timelineA, timelineB, refTimelineA, refTimelineB);
		return corr;
	}
	
	private MetaTimeline getRefMetaTimeline(CyEdge edge){
		Integer timelineLength = metaNetwork.getHiddenDataRow().get(
				AppColumns.TIMELINE_LENGTH, Integer.class);
		Optional<CyEdge> aRef = edgeMapper.getMapped(edge, metaNetwork);
		if(!aRef.isPresent()) return EmptyMetaTimelineFactory.create(timelineLength);
		MetaTimeline refMetaTimeline = metaTimelineFactory.create(aRef.get(), refMetaNetwork);
		return refMetaTimeline;
	}
	
}
