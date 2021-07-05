package com.tcb.sensenet.internal.analysis.correlation.mutualInformation.pointwise;

import org.apache.commons.lang3.tuple.Pair;

import com.tcb.sensenet.internal.analysis.correlation.DiscreteProbability;
import com.tcb.sensenet.internal.analysis.correlation.Frequencies;

public class PositivePMIPower implements PointwiseMutualInformationStrategy {
	
	private double power;

	public PositivePMIPower(double power){
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
		double result = jointProb / (probA * probB);
		return result;
	}
			
}
