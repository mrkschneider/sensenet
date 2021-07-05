package com.tcb.sensenet.internal.analysis.correlation.mutualInformation.pointwise;

import org.apache.commons.lang3.tuple.Pair;

import com.tcb.sensenet.internal.analysis.correlation.DiscreteProbability;
import com.tcb.sensenet.internal.analysis.correlation.Frequencies;

public class PMI implements PointwiseMutualInformationStrategy {
		
	private static final double log2 = Math.log(2);
	
	@Override
	public double calculate(
			Integer a, Integer b, 
			Frequencies<Integer> freqA,
			Frequencies<Integer> freqB,
			Frequencies<Pair<Integer,Integer>> freqAB){
		double jointProb = freqAB.getProbability(Pair.of(a, b));
		if(jointProb==0.0) return 0.0;
		double probA = freqA.getProbability(a);
		double probB = freqB.getProbability(b);
		double log = log(jointProb / (probA * probB));
		assert(log!=Double.NEGATIVE_INFINITY);
		return log;
	}
	
	protected double log(double x){
		return Math.log(x) / log2;
	}

}
