package com.tcb.sensenet.internal.analysis.normalization;

import com.tcb.mdAnalysis.statistics.StandardStatistics;

public class ZScoreNormalizationStrategy implements NormalizationStrategy {

	@Override
	public void normalize(double[] ds) {
		StandardStatistics stat = new StandardStatistics(ds);
		double avg = stat.getMean();
		double std = stat.getStandardDeviation();
				
		for(int i=0;i<ds.length;i++){
			ds[i] = (ds[i] - avg) / std;
		}		
	}

}
