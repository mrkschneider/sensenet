package com.tcb.sensenet.internal.analysis.divergence;

import java.util.HashSet;
import java.util.Set;

import com.tcb.sensenet.internal.analysis.correlation.Probabilities;

public class PopulationShiftDivergence implements DivergenceStrategy {

	@Override
	public double calculate(Probabilities<Integer> freqA, Probabilities<Integer> freqB) {
		Set<Integer> events = new HashSet<>();
		events.addAll(freqA.getEvents());
		events.addAll(freqB.getEvents());
		double result = 0d;
		for(Integer event:events) {
			Double probA = freqA.getProbability(event);
			Double probB = freqB.getProbability(event);
			double d = probB - probA;
			result += Math.abs(d);
		}
		result /= 2.;
		return result;
	}

}
