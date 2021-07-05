package com.tcb.sensenet.internal.analysis.autocorrelation.replicas.strategy;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import com.tcb.sensenet.internal.analysis.autocorrelation.AutocorrelationAnalysisAdapter;
import com.tcb.sensenet.internal.util.DoubleUtil;

public class MinAutocorrelationTimeWeightStrategy implements AutocorrelationTimeWeightStrategy {

	@Override
	public Double merge(List<AutocorrelationAnalysisAdapter> analyses) {
		List<Double> autocorrelationTimes = analyses.stream()
				.map(a -> a.getAutocorrelationTime())
				.filter(t -> !Double.isNaN(t))
				.collect(Collectors.toList());
		return autocorrelationTimes.stream()
				.mapToDouble(d -> d)
				.min().orElse(1.0);
	}

}
