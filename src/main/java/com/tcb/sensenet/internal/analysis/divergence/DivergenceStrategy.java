package com.tcb.sensenet.internal.analysis.divergence;

import com.tcb.sensenet.internal.analysis.correlation.Frequencies;
import com.tcb.sensenet.internal.analysis.correlation.Probabilities;

public interface DivergenceStrategy {
	public double calculate(Probabilities<Integer> freqA, Probabilities<Integer> freqB);
}