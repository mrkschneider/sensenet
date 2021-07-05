package com.tcb.sensenet.internal.path.analysis.centrality.distance;

public class InverseWeightDistanceStrategy implements EdgeDistanceStrategy {
			
	@Override
	public Double getDistance(Double x) {
		if(x.equals(0.0)) return Double.POSITIVE_INFINITY;
		return 1.0 / x;
	}

}
