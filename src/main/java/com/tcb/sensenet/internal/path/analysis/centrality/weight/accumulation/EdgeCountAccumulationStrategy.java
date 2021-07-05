package com.tcb.sensenet.internal.path.analysis.centrality.weight.accumulation;

import java.util.List;

import org.cytoscape.model.CyIdentifiable;

public class EdgeCountAccumulationStrategy implements WeightAccumulationStrategy {

	@Override
	public <T extends CyIdentifiable> Double weight(List<T> cyIds) {
		return Integer.valueOf(cyIds.size()).doubleValue();
	}

}
