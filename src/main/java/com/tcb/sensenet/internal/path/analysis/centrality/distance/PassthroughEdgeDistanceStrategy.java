package com.tcb.sensenet.internal.path.analysis.centrality.distance;

public class PassthroughEdgeDistanceStrategy implements EdgeDistanceStrategy {

	@Override
	public Double getDistance(Double weight) {
		return weight;
	}

}
