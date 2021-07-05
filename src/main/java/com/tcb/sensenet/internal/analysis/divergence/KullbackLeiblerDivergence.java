package com.tcb.sensenet.internal.analysis.divergence;

import java.util.HashSet;
import java.util.Set;

import com.tcb.sensenet.internal.analysis.correlation.Frequencies;
import com.tcb.sensenet.internal.analysis.correlation.Probabilities;
import com.tcb.sensenet.internal.util.MathLog;

public class KullbackLeiblerDivergence implements DivergenceStrategy {
	
	@Override
	public double calculate(
			Probabilities<Integer> freqA,
			Probabilities<Integer> freqB){
		Set<Integer> events = getEvents(freqA,freqB);
		double sum = 0.;
		for(Integer x:events){
			double p = freqA.getProbability(x);
			if(p==0.) continue;
			double q = freqB.getProbability(x);
			// JVM returns Double.INFINITY upon division by 0.0, which works well for our case
			double log = log(p/q);
			double r = p * log;
			sum += r;			
		}
		return sum;
	}
		
	private Set<Integer> getEvents(Probabilities<Integer> freqA, Probabilities<Integer> freqB){
		Set<Integer> events = new HashSet<>();
		events.addAll(freqA.getEvents());
		events.addAll(freqB.getEvents());
		return events;
	}
	
	protected double log(double x){
		return MathLog.log2(x);
	}
}
