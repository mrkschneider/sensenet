package com.tcb.sensenet.internal.analysis.normalization;

import com.tcb.mdAnalysis.statistics.StandardStatistics;

public class MinMaxNormalizationStrategy implements NormalizationStrategy {

	@Override
	public void normalize(double[] ds) {
		StandardStatistics stat = new StandardStatistics(ds);
		double max = stat.getMax();
		double min = stat.getMin();
		double range = max - min;
		
		for(int i=0;i<ds.length;i++){
			ds[i] = (ds[i] - min) / range;
		}		
	}

}
