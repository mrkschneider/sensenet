package com.tcb.sensenet.internal.path.weight;

import java.util.stream.Collectors;

import com.tcb.sensenet.internal.path.Path;
import com.tcb.sensenet.internal.path.PathUtil;
import com.tcb.cytoscape.cyLib.cytoApiWrappers.CyNetworkAdapter;

public class UniformPathWeighter implements PathWeighter {

	private CyNetworkAdapter network;

	public UniformPathWeighter(CyNetworkAdapter network){
		this.network = network;
	}
	
	@Override
	public Double getWeight(Path path) {
		return PathUtil.getPathEdges(path, network).stream()
				.map(l -> l.size())
				.collect(Collectors.summarizingDouble(i -> (double)i))
				.getSum();
	}

}
