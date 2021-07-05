package com.tcb.sensenet.internal.path.analysis.centrality.weight.accumulation;

import java.util.List;

import org.cytoscape.model.CyIdentifiable;

import com.tcb.cytoscape.cyLib.cytoApiWrappers.CyNetworkAdapter;

public class ColumnSumWeightAccumulationStrategy implements WeightAccumulationStrategy {

	private String columnName;
	private CyNetworkAdapter network;

	public ColumnSumWeightAccumulationStrategy(String columnName, CyNetworkAdapter network){
		this.columnName = columnName;
		this.network = network;
	}
		
	@Override
	public <T extends CyIdentifiable> Double weight(List<T> cyIds) {
		Double sum = 0.0d;
		for(T cyId:cyIds){
			Double v = network.getRow(cyId).getRawMaybe(columnName, Double.class)
					.orElse(null);
			if(v==null || v.equals(Double.NaN)) {
				continue;
			}
			sum += v;
		}
		return sum;
	}
}
