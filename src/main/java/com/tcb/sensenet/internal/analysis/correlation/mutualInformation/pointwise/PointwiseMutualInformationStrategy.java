package com.tcb.sensenet.internal.analysis.correlation.mutualInformation.pointwise;

import org.apache.commons.lang3.tuple.Pair;

import com.tcb.sensenet.internal.analysis.correlation.Frequencies;

public interface PointwiseMutualInformationStrategy {
	public double calculate(
			Integer a, Integer b, 
			Frequencies<Integer> freqA,
			Frequencies<Integer> freqB,
			Frequencies<Pair<Integer,Integer>> freqAB);
}
