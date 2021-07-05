package com.tcb.sensenet.internal.path;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.cytoscape.model.CyEdge;
import org.cytoscape.model.CyNode;

import com.tcb.sensenet.internal.util.CancelledException;
import com.tcb.cytoscape.cyLib.cytoApiWrappers.CyNetworkAdapter;

public class SimpleDistanceSearcher implements DistanceSearcher {

	private volatile boolean cancelled = false;
	private Set<CyNode> ignored;
	
	public SimpleDistanceSearcher(Collection<CyNode> ignored) {
		this.ignored = new HashSet<>(ignored);
	}
	
	public SimpleDistanceSearcher() {
		this(Arrays.asList());
	}
		
	
	@Override
	public double distance(CyNetworkAdapter network, CyNode source, CyNode target) throws CancelledException {
		double distance = 0d;
		Set<CyNode> level = new HashSet<>();
		level.add(source);
		Set<CyNode> visited = new HashSet<>();
		visited.addAll(ignored);
		
		
		while(!level.isEmpty()) {
			if(cancelled) throw new CancelledException("Cancelled distance search");
			if(level.contains(target)) return distance;
			visited.addAll(level);
			distance += 1;
			level = getNextLevel(network, level, visited);
		}
		
		return Double.POSITIVE_INFINITY;
	}
	
	private Set<CyNode> getNextLevel(CyNetworkAdapter network, Set<CyNode> level, Set<CyNode> visited){
		Set<CyNode> result = new HashSet<>();
		for(CyNode node:level) {
			List<CyNode> nodes = network.getNeighborList(node, CyEdge.Type.ANY)
					.stream()
					.filter(n -> !visited.contains(n))
					.collect(Collectors.toList());
			result.addAll(nodes);
		}
		return result;
	}

	@Override
	public void cancel() {
		cancelled = true;
	}

}
