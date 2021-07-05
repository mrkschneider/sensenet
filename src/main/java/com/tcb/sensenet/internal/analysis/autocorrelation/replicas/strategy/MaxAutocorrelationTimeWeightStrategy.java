package com.tcb.sensenet.internal.analysis.autocorrelation.replicas.strategy;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import com.tcb.sensenet.internal.analysis.autocorrelation.AutocorrelationAnalysisAdapter;
import com.tcb.sensenet.internal.util.DoubleUtil;

public class MaxAutocorrelationTimeWeightStrategy implements AutocorrelationTimeWeightStrategy {

	@Override
	public Double merge(List<AutocorrelationAnalysisAdapter> analyses) {
		List<Double> autocorrelationTimes = analyses.stream()
				.map(a -> a.getAutocorrelationTime())
				.map(t -> DoubleUtil.replaceNaN(t, 1.0))
				.collect(Collectors.toList());
		return Collections.max(autocorrelationTimes);
	}

}
