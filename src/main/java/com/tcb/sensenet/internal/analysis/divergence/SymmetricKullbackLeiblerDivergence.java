package com.tcb.sensenet.internal.analysis.divergence;

import com.tcb.sensenet.internal.analysis.correlation.Frequencies;
import com.tcb.sensenet.internal.analysis.correlation.Probabilities;

public class SymmetricKullbackLeiblerDivergence implements DivergenceStrategy {

	@Override
	public double calculate(
			Probabilities<Integer> freqA,
			Probabilities<Integer> freqB){
		double a = new KullbackLeiblerDivergence().calculate(freqA, freqB);
		double b = new KullbackLeiblerDivergence().calculate(freqB, freqA);
		return a + b;
	}
}
