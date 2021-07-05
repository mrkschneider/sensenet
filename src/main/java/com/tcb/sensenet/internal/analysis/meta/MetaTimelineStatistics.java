package com.tcb.sensenet.internal.analysis.meta;

import org.apache.commons.math3.stat.correlation.Covariance;

import com.tcb.sensenet.internal.meta.timeline.MetaTimeline;
import com.tcb.mdAnalysis.statistics.StandardStatistics;

public class MetaTimelineStatistics {
	
	public static Double getCovariance(MetaTimeline a, MetaTimeline b){
		Covariance cov = new Covariance();
		return cov.covariance(a.getData().clone(), b.getData().clone(), true);
	}
	
	public static Double getCorrelation(MetaTimeline a, MetaTimeline b){
		Double covariance = getCovariance(a,b);
		Double norm = getStandardDeviation(a) * getStandardDeviation(b);
		return covariance / norm;
	}
		
	public static Double getAverage(MetaTimeline a) {
		return new StandardStatistics(a.getData()).getMean();
	}
	
	public static Double getStandardDeviation(MetaTimeline a){
		return new StandardStatistics(a.getData()).getStandardDeviation();
	}
}
