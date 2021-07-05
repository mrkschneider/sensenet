package com.tcb.sensenet.internal.analysis.correlation.mutualInformation.pointwise;

import org.apache.commons.lang3.tuple.Pair;

import com.tcb.sensenet.internal.analysis.correlation.DiscreteProbability;
import com.tcb.sensenet.internal.analysis.correlation.Frequencies;

public class NormalizedPMI extends PMI {
	
	@Override
	public double calculate(
			Integer a, Integer b, 
			Frequencies<Integer> freqA,
			Frequencies<Integer> freqB,
			Frequencies<Pair<Integer,Integer>> freqAB){
		double log = super.calculate(a, b, freqA, freqB, freqAB);
		double jointProb = freqAB.getProbability(Pair.of(a, b));
		double norm = -log(jointProb);
		return log / norm;
	}
	
}
