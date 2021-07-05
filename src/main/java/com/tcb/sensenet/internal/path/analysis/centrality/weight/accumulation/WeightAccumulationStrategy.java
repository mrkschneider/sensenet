package com.tcb.sensenet.internal.path.analysis.centrality.weight.accumulation;

import java.util.List;

import org.cytoscape.model.CyIdentifiable;

public interface WeightAccumulationStrategy {
	public <T extends CyIdentifiable> Double weight(List<T> cyIds);
}
