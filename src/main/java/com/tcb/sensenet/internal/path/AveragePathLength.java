package com.tcb.sensenet.internal.path;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.cytoscape.model.CyNode;

import com.tcb.cytoscape.cyLib.cytoApiWrappers.CyNetworkAdapter;

public class AveragePathLength {

	private DistanceSearcher searcher;

	public AveragePathLength(DistanceSearcher searcher) {
		this.searcher = searcher;
	}
	
	public double calculate(CyNetworkAdapter network, Set<CyNode> ignored) {
		List<CyNode> nodes = network.getNodeList().stream()
				.filter(n -> !ignored.contains(n))
				.collect(Collectors.toList());
		final int size = nodes.size();
		double result = 0d;
				
		for(int i=0;i<size;i++) {
			for(int j=i+1;j<size;j++) {
				result += searcher.distance(network, nodes.get(i), nodes.get(j));
			}
		}
		
		result /= 0.5 * (size - 1) * (size - 2);
		return result;
	}
	
	
	
}
