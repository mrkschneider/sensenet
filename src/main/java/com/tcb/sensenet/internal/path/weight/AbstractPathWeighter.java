package com.tcb.sensenet.internal.path.weight;

import java.util.List;

import org.cytoscape.model.CyEdge;

import com.google.common.collect.ImmutableList;
import com.tcb.sensenet.internal.analysis.meta.MetaTimelineStatistics;
import com.tcb.sensenet.internal.meta.network.MetaNetwork;
import com.tcb.sensenet.internal.meta.timeline.MetaTimeline;
import com.tcb.sensenet.internal.meta.timeline.factories.NetworkMetaTimelineFactory;
import com.tcb.sensenet.internal.path.Path;
import com.tcb.sensenet.internal.path.PathUtil;
import com.tcb.cytoscape.cyLib.cytoApiWrappers.CyNetworkAdapter;

public abstract class AbstractPathWeighter implements PathWeighter {

	private CyNetworkAdapter network;
	private MetaNetwork metaNetwork;

	public AbstractPathWeighter(
			MetaNetwork metaNetwork,
			CyNetworkAdapter network){
		this.network = network;
		this.metaNetwork = metaNetwork;
	}
	
	protected abstract NetworkMetaTimelineFactory getMetaTimelineFactory();
	protected abstract Double normalizeWeight(Double weight, Path path);
	
	@Override
	public Double getWeight(Path path) {
		Double weight = 0.0;
		List<List<CyEdge>> pathEdges = PathUtil.getPathEdges(path, network);
		for(List<CyEdge> edges:pathEdges){
			weight += getWeight(edges);
		}
		weight = normalizeWeight(weight,path);
		return weight;
	}
	
	private Double getWeight(List<CyEdge> edges){
		NetworkMetaTimelineFactory metaTimelineFactory = getMetaTimelineFactory();
		List<MetaTimeline> metaTimelines = edges.stream()
				.map(e -> metaTimelineFactory.create(e, metaNetwork))
				.collect(ImmutableList.toImmutableList());
		MetaTimeline metaMetaTimeline = metaTimelineFactory.getMetaTimelineFactory()
				.create(metaTimelines);
		return MetaTimelineStatistics.getAverage(metaMetaTimeline);
	}

}
