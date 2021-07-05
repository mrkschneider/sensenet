package com.tcb.sensenet.internal.analysis.correlation;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.cytoscape.model.CyEdge;

import com.tcb.sensenet.internal.meta.network.MetaNetwork;
import com.tcb.sensenet.internal.meta.timeline.MetaTimeline;
import com.tcb.sensenet.internal.meta.timeline.factories.NetworkMetaTimelineFactory;
import com.tcb.sensenet.internal.util.CancelledException;
import com.tcb.sensenet.internal.util.ObjMap;
import com.tcb.cytoscape.cyLib.cytoApiWrappers.CyNetworkAdapter;
import com.tcb.common.util.SafeMap;

public class CorrelationFactorsAnalysis {
	private volatile boolean cancelled = false;
	
	public ObjMap analyse(
			EdgeCorrelationStrategy edgeCorrelationStrategy,
			CyNetworkAdapter network){
	List<CyEdge> edges = network.getEdgeList();

	Map<CyEdge,Double> correlationFactors = edges.stream().parallel()
			.collect(Collectors.toMap(
					e -> e,
					e -> getCorrelationFactor(e,network,edgeCorrelationStrategy),
					(v1,v2) -> {throw new IllegalArgumentException();},
					SafeMap::new));
	ObjMap results = new ObjMap();
	results.put("correlationFactors", correlationFactors);
	results.put("network", network);	
	return results;
	}
	
	private double getCorrelationFactor(CyEdge edge, CyNetworkAdapter network,
			EdgeCorrelationStrategy edgeCorrelationStrategy){
		if(cancelled) throw new CancelledException("Received cancel request");
		Set<CyEdge> neighbors = new HashSet<>(network.getNeighborEdges(edge));
		double result = 0.0d;
		for(CyEdge neighbor:neighbors){
			Double correlation = edgeCorrelationStrategy.getCorrelation(edge, neighbor);
			result += Math.abs(correlation);
		}
		return result;
	}
	

	public void cancel(){
		cancelled = true;
	}
	
}

