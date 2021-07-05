package com.tcb.sensenet.internal.path.analysis.centrality.distance;

public class NegativeExponentialWeightDistanceStrategy implements EdgeDistanceStrategy {

	@Override
	public Double getDistance(Double weight) {
		return Math.exp(-weight);
	}

}
