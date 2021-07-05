package com.tcb.sensenet.internal.analysis.correlation.mutualInformation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.PrimitiveIterator;
import java.util.Set;

import org.apache.commons.lang3.tuple.Pair;

import com.tcb.sensenet.internal.analysis.correlation.DiscreteProbability;
import com.tcb.sensenet.internal.analysis.correlation.Frequencies;
import com.tcb.sensenet.internal.analysis.correlation.IntFrequencies;
import com.tcb.sensenet.internal.analysis.correlation.JointIntFrequencies;
import com.tcb.sensenet.internal.util.ArrayUtils;
import com.tcb.sensenet.internal.util.iterator.IntIterable;

public class MutualInformation {
	
	private static final double log2 = Math.log(2);
	
	public double calculate(
			Frequencies<Integer> freqA,
			Frequencies<Integer> freqB,
			Frequencies<Pair<Integer,Integer>> freqAB){
		Set<Integer> eventsA = freqA.getEvents();
		Set<Integer> eventsB = freqB.getEvents();
				
		double sum = 0.0d;
		for(Integer eventA:eventsA){
			for(Integer eventB:eventsB){
				double jointProb = freqAB.getProbability(Pair.of(eventA, eventB));
				double probA = freqA.getProbability(eventA);
				double probB = freqB.getProbability(eventB);
				double log = log(jointProb / (probA * probB));
				// Math.log returns -INF if argument is 0
				if(log==Double.NEGATIVE_INFINITY) continue;
				sum += jointProb * log;
			}
		}
		
		return sum;
	}
	
	public double calculate(
			IntIterable dataA,
			IntIterable dataB){
		
		Frequencies<Integer> freqA = IntFrequencies.create(dataA);
		Frequencies<Integer> freqB = IntFrequencies.create(dataB);
		JointIntFrequencies jointFreq = JointIntFrequencies.create(dataA, dataB);
		
		return calculate(freqA,freqB,jointFreq);
	}
	
	private double log(double x){
		return Math.log(x) / log2;
	}
	
	
}
