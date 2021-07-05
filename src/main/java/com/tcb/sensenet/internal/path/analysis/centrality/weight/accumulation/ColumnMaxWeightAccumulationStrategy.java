package com.tcb.sensenet.internal.path.analysis.centrality.weight.accumulation;

import java.util.List;

import org.cytoscape.model.CyIdentifiable;

import com.tcb.cytoscape.cyLib.cytoApiWrappers.CyNetworkAdapter;

public class ColumnMaxWeightAccumulationStrategy implements WeightAccumulationStrategy {

	private String columnName;
	private CyNetworkAdapter network;

	public ColumnMaxWeightAccumulationStrategy(String columnName, CyNetworkAdapter network){
		this.columnName = columnName;
		this.network = network;
	}
		
	@Override
	public <T extends CyIdentifiable> Double weight(List<T> cyIds) {
		return cyIds.stream()
				.map(c -> network.getRow(c))
				.map(r -> r.getRawMaybe(columnName, Double.class).orElse(null))
				.filter(d -> d!=null && (!(d.equals(Double.NaN))))
				.max(Double::compare)
				.orElseThrow(() -> new IllegalArgumentException("Invalid value for weighting"));
	}
}
