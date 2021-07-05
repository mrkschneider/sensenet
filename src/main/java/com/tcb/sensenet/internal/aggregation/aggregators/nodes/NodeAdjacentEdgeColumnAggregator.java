package com.tcb.sensenet.internal.aggregation.aggregators.nodes;

import java.util.List;

import org.cytoscape.model.CyEdge;
import org.cytoscape.model.CyNode;

import com.google.common.collect.ImmutableList;
import com.tcb.cytoscape.cyLib.cytoApiWrappers.CyNetworkAdapter;
import com.tcb.cytoscape.cyLib.data.Columns;

public class NodeAdjacentEdgeColumnAggregator implements NodeAggregator<Double> {

	private String column;

	public NodeAdjacentEdgeColumnAggregator(String column){
		this.column = column;
	}
	
	@Override
	public Double aggregate(CyNode node, CyNetworkAdapter network) {
		List<CyEdge> neighbors = network.getAdjacentEdgeList(node, CyEdge.Type.ANY);
		List<Double> values = neighbors.stream()
				.map(n -> network.getRow(n))
				.map(r -> r.getRaw(column,Double.class))
				.collect(ImmutableList.toImmutableList());
		return values.stream()
				.mapToDouble(d -> d)
				.sum();
	}

}
