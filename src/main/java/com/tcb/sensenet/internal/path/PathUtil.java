package com.tcb.sensenet.internal.path;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.cytoscape.model.CyEdge;
import org.cytoscape.model.CyNode;

import com.tcb.sensenet.internal.util.CancelledException;
import com.tcb.cytoscape.cyLib.cytoApiWrappers.CyNetworkAdapter;

public class PathUtil {
	
	private static final Integer pathLimit = 100000;
	
	public static List<List<CyEdge>> getPathEdges(Path path, CyNetworkAdapter network){
		List<CyNode> nodes = path.getNodes();
		List<List<CyEdge>> result = new ArrayList<>();
		for(int i=0;i<nodes.size()-1;i++){
			CyNode a = nodes.get(i);
			CyNode b = nodes.get(i+1);
			List<CyEdge> edges = network.getConnectingEdgeList(a, b, CyEdge.Type.ANY);
			result.add(edges);
		}
		return result;
	}
	
	public static Set<CyEdge> getAllPathEdges(Path path, CyNetworkAdapter network){
		return getPathEdges(path,network).stream()
				.flatMap(l -> l.stream())
				.collect(Collectors.toSet());
	}
		
	public static List<Path> appendNocyclicNeighbors(Path current, CyNetworkAdapter network){
		List<Path> paths = new ArrayList<>();
		CyNode last = current.getLast();
		Set<CyNode> neighbors = new LinkedHashSet<>(network.getNeighborList(
				last, CyEdge.Type.ANY));
				
		for(CyNode neighbor:neighbors){
			if(current.contains(neighbor)) continue;
			paths.add(current.add(neighbor));
		}
		return paths;
	}
	
	public static Set<CyNode> getNodesWithinDistance(
			CyNode source, Integer maxDistance, CyNetworkAdapter network){
		Set<CyNode> result = new HashSet<>();
		Set<CyNode> level = new HashSet<>();
		level.add(source);
		for(int i=0;i<maxDistance;i++){
			Set<CyNode> nextLevel = new HashSet<>();
			for(CyNode node:level){
				List<CyNode> neighbors = 
						network.getNeighborList(node, CyEdge.Type.ANY)
						.stream()
						.filter(n -> !result.contains(n))
						.collect(Collectors.toList());
				nextLevel.addAll(neighbors);
			}
			result.addAll(nextLevel);
			level = nextLevel;
		}
		return result;
	}
	
	public static void checkTooManyPaths(List<Path> paths) throws CancelledException {
		if(paths.size() > pathLimit) throw new CancelledException(
				String.format("Too many paths (> %d)",pathLimit));
	}
}
