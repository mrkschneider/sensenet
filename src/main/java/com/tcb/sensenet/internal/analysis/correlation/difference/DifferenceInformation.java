package com.tcb.sensenet.internal.analysis.correlation.difference;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.PrimitiveIterator;
import java.util.Set;

import org.apache.commons.lang3.tuple.Pair;

import com.tcb.sensenet.internal.analysis.correlation.DiscreteProbability;
import com.tcb.sensenet.internal.analysis.correlation.Frequencies;
import com.tcb.sensenet.internal.analysis.correlation.IntFrequencies;
import com.tcb.sensenet.internal.analysis.correlation.JointIntFrequencies;
import com.tcb.sensenet.internal.analysis.correlation.mutualInformation.pointwise.PMI;
import com.tcb.sensenet.internal.analysis.correlation.mutualInformation.pointwise.PointwiseMutualInformationStrategy;
import com.tcb.sensenet.internal.util.iterator.IntIterable;

public class DifferenceInformation {
	
	private final PointwiseMutualInformationStrategy pmi;
	
	public DifferenceInformation(
			PointwiseMutualInformationStrategy pmi){
		this.pmi = pmi;
	}
		
	public double calculate(
			IntIterable a1,
			IntIterable b1,
			IntIterable a2,
			IntIterable b2){
		Frequencies<Integer> freqA1 = IntFrequencies.create(a1);
		Frequencies<Integer> freqA2 = IntFrequencies.create(a2);
		Frequencies<Integer> freqB1 = IntFrequencies.create(b1);
		Frequencies<Integer> freqB2 = IntFrequencies.create(b2);
		Frequencies<Pair<Integer,Integer>> freqA1B1 = JointIntFrequencies.create(a1, b1);
		Frequencies<Pair<Integer,Integer>> freqA2B2 = JointIntFrequencies.create(a2, b2);
				
		Set<Integer> eventsA = getEventUnion(freqA1.getEvents(),freqA2.getEvents());
		Set<Integer> eventsB = getEventUnion(freqB1.getEvents(),freqB2.getEvents());
				
		double sum = 0.0d;
		for(Integer eventA:eventsA){
			for(Integer eventB:eventsB){
				double info1 = getPartialInformation(eventA, eventB, freqA1, freqB1, freqA1B1);
				double info2 = getPartialInformation(eventA, eventB, freqA2, freqB2, freqA2B2);
				double delta = Math.abs(info1 - info2);
				sum += delta;
			}
		}
		return sum;
	}
	
	private double getPartialInformation(
			Integer eventA, Integer eventB,
			Frequencies<Integer> freqA, Frequencies<Integer> freqB,
			Frequencies<Pair<Integer,Integer>> freqAB){
		return pmi.calculate(eventA, eventB, freqA, freqB, freqAB);
	}
		
	private Set<Integer> getEventUnion(Set<Integer> a, Set<Integer> b){
		Set<Integer> eventSet = new HashSet<>();
		eventSet.addAll(a);
		eventSet.addAll(b);
		return eventSet;
	}
}
