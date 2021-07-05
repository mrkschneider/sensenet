package com.tcb.sensenet.internal.path.analysis.centrality.weight.accumulation;

import java.util.List;

import org.cytoscape.model.CyIdentifiable;

import com.tcb.cytoscape.cyLib.cytoApiWrappers.CyNetworkAdapter;

public class ColumnAverageWeightAccumulationStrategy implements WeightAccumulationStrategy {

	private WeightAccumulationStrategy strategy;

	public ColumnAverageWeightAccumulationStrategy(String columnName, CyNetworkAdapter network){
		this.strategy = new ColumnSumWeightAccumulationStrategy(columnName,network);
	}
	
	@Override
	public <T extends CyIdentifiable> Double weight(List<T> cyIds) {
		Double sum = strategy.weight(cyIds);
		return sum / ((double)cyIds.size());
	}
}
