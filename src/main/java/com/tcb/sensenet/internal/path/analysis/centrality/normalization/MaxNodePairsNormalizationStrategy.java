package com.tcb.sensenet.internal.path.analysis.centrality.normalization;

import com.tcb.sensenet.internal.analysis.normalization.NormalizationStrategy;

public class MaxNodePairsNormalizationStrategy implements NormalizationStrategy {

	@Override
	public void normalize(double[] centralities) {
		final int size = centralities.length;
		// 0.5 for undirected networks
		final double pairs = 0.5 * (size - 1) * (size - 2);
		for(int i=0;i<size;i++){
			centralities[i] /= pairs;
		}
	}

}
