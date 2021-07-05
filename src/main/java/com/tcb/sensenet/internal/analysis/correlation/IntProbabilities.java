package com.tcb.sensenet.internal.analysis.correlation;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.PrimitiveIterator;
import java.util.Set;

import com.tcb.sensenet.internal.util.iterator.IntIterable;

import gnu.trove.map.hash.TIntDoubleHashMap;

public class IntProbabilities implements Probabilities<Integer> {

	private TIntDoubleHashMap m;

	public IntProbabilities(TIntDoubleHashMap m) {
		this.m = m;
	}
	
	@Override
	public double getProbability(Integer x) {
		return m.get(x);
	}

	@Override
	public Set<Integer> getEvents() {
		Set<Integer> keys = new HashSet<Integer>();
		for(int k:m.keys()) keys.add(k);
		return keys;
	}
	
	public static IntProbabilities average(List<? extends Probabilities<Integer>> probs) {
		TIntDoubleHashMap m = new TIntDoubleHashMap();

		for(Probabilities<Integer> prob:probs) {
			for(Integer k:prob.getEvents()) {
				double v = prob.getProbability(k);
				m.adjustOrPutValue(k, v, v);
			}
		}
		
		int size = probs.size();
		for(int k:m.keys()) m.put(k, m.get(k) / size);
		
		return new IntProbabilities(m);
	}
	
	public static IntProbabilities create(IntIterable data) {
		IntFrequencies freq = IntFrequencies.create(data);
		return IntProbabilities.average(Arrays.asList(freq));
	}
	
	public static IntProbabilities create(int[] data) {
		return create(IntIterable.of(data));
	}

}
