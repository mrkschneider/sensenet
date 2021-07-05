package com.tcb.sensenet.internal.analysis.correlation.mutualInformation.pointwise;

import org.apache.commons.lang3.tuple.Pair;

import com.tcb.sensenet.internal.analysis.correlation.DiscreteProbability;
import com.tcb.sensenet.internal.analysis.correlation.Frequencies;
import com.tcb.sensenet.internal.util.MathLog;

public class PMIPower implements PointwiseMutualInformationStrategy {
	
	private double power;

	public PMIPower(double power){
		this.power = power;
	}
	@Override
	public double calculate(
			Integer a, Integer b, 
			Frequencies<Integer> freqA,
			Frequencies<Integer> freqB,
			Frequencies<Pair<Integer,Integer>> freqAB){
		double jointProb = freqAB.getProbability(Pair.of(a, b));
		jointProb = Math.pow(jointProb,power);
		if(jointProb==0.0) return 0.0;
		double probA = freqA.getProbability(a);
		double probB = freqB.getProbability(b);
		double log = log(jointProb / (probA * probB));
		assert(log!=Double.NEGATIVE_INFINITY);
		return log;
	}

	protected double log(double x){
		return MathLog.log2(x);
	}

}
