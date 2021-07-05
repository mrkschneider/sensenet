package com.tcb.sensenet.internal.analysis.divergence;

import java.util.Arrays;

import com.tcb.sensenet.internal.analysis.correlation.Frequencies;
import com.tcb.sensenet.internal.analysis.correlation.IntFrequencies;
import com.tcb.sensenet.internal.analysis.correlation.IntProbabilities;
import com.tcb.sensenet.internal.analysis.correlation.Probabilities;

public class JensenShannonDivergence implements DivergenceStrategy {

	@Override
	public double calculate(Probabilities<Integer> freqA, Probabilities<Integer> freqB) {
		Probabilities<Integer> freqM = IntProbabilities.average(Arrays.asList(freqA,freqB));
		double a = new KullbackLeiblerDivergence().calculate(freqA,freqM);
		double b = new KullbackLeiblerDivergence().calculate(freqB,freqM);
		double r = 0.5 * (a + b);
		return r;
	}

}
