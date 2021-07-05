package com.tcb.sensenet.internal.analysis.autocorrelation.replicas.strategy;

import java.util.List;

import com.tcb.sensenet.internal.analysis.autocorrelation.AutocorrelationAnalysisAdapter;

public interface AutocorrelationTimeWeightStrategy {
	public Double merge(List<AutocorrelationAnalysisAdapter> autocorrelationAnalyses);
}
