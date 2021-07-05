package com.tcb.sensenet.internal.path.analysis.centrality.weight.negative;

public class IgnoreNegativeValuesStrategy implements NegativeValuesStrategy {

	@Override
	public Double transform(Double weight) {
		return Math.max(0.0, weight);
	}

}
