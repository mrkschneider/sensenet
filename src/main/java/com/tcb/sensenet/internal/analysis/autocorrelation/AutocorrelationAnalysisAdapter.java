package com.tcb.sensenet.internal.analysis.autocorrelation;

import java.util.List;

import com.google.auto.value.AutoValue;
import com.google.common.collect.ImmutableList;
import com.tcb.sensenet.internal.util.DoubleUtil;
import com.tcb.mdAnalysis.statistics.autocorrelation.Autocorrelation;
import com.tcb.mdAnalysis.statistics.autocorrelation.AutocorrelationAnalysis;
import com.tcb.mdAnalysis.statistics.regression.Regression;

@AutoValue
public abstract class AutocorrelationAnalysisAdapter {
	
	public abstract List<Double> getAutocorrelations();
	public abstract Integer getObservationCount();
	public abstract Regression getRegression();
	public abstract Double getAutocorrelationTime();
			
	public static AutocorrelationAnalysisAdapter create(
			List<Double> autocorrelations,
			Integer observationCount,
			Regression regression,
			Double autocorrelationTime){
		return new AutoValue_AutocorrelationAnalysisAdapter(
				ImmutableList.copyOf(autocorrelations),
				observationCount,
				regression,
				autocorrelationTime);
	}
	
	public static AutocorrelationAnalysisAdapter create(
			Autocorrelation autocorrelation,
			Double regressionLimit){
		AutocorrelationAnalysis analysis = new AutocorrelationAnalysis(
				autocorrelation.getAutocorrelations(),
				regressionLimit);
		return AutocorrelationAnalysisAdapter.create(
				analysis.getAutocorrelations(),
				autocorrelation.getInputDataSize(),
				analysis.getRegression(),
				analysis.getAutocorrelationTime()
				);
	}
		
}
