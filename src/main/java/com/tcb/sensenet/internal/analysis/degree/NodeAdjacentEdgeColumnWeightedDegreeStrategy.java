package com.tcb.sensenet.internal.analysis.degree;

import java.util.List;

import org.cytoscape.model.CyEdge;
import org.cytoscape.model.CyNode;

import com.google.common.collect.ImmutableList;
import com.tcb.sensenet.internal.aggregation.aggregators.nodes.NodeAdjacentEdgeColumnAggregator;
import com.tcb.sensenet.internal.aggregation.aggregators.nodes.NodeAggregator;
import com.tcb.sensenet.internal.path.analysis.centrality.weight.negative.AbsoluteValueNegativeValuesStrategy;
import com.tcb.sensenet.internal.path.analysis.centrality.weight.negative.NegativeValuesStrategy;
import com.tcb.cytoscape.cyLib.cytoApiWrappers.CyNetworkAdapter;
import com.tcb.cytoscape.cyLib.data.Columns;

public class NodeAdjacentEdgeColumnWeightedDegreeStrategy implements WeightedDegreeStrategy {

	private final String column;
	private NegativeValuesStrategy negativeValuesStrategy;
	
	public NodeAdjacentEdgeColumnWeightedDegreeStrategy(
			String column,
			NegativeValuesStrategy negativeValuesStrategy){
		this.column = column;
		this.negativeValuesStrategy = negativeValuesStrategy;
	}

	@Override
	public Double getDegree(CyNode node, CyNetworkAdapter network) {
		List<CyEdge> neighbors = network.getAdjacentEdgeList(node, CyEdge.Type.ANY);
		List<Double> values = neighbors.stream()
				.map(n -> network.getRow(n))
				.map(r -> r.getRaw(column,Double.class))
				.map(d -> negativeValuesStrategy.transform(d))
				.collect(ImmutableList.toImmutableList());
		return values.stream()
				.mapToDouble(d -> d)
				.sum();
	}
	
	

}
