package com.tcb.sensenet.internal.path.analysis.centrality.weight.negative;

public class AbsoluteValueNegativeValuesStrategy implements NegativeValuesStrategy {

	@Override
	public Double transform(Double weight) {
		return Math.abs(weight);
	}

}
