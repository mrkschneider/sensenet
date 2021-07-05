package com.tcb.sensenet.internal.analysis.correlation;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.PrimitiveIterator;
import java.util.Set;
import java.util.stream.Collectors;

import com.tcb.common.util.ListFilter;
import com.tcb.sensenet.internal.util.ListUtil;
import com.tcb.sensenet.internal.util.iterator.IntIterable;
import com.tcb.sensenet.internal.util.iterator.IntIterator;

import gnu.trove.map.hash.TIntDoubleHashMap;
import gnu.trove.map.hash.TIntIntHashMap;

public class IntFrequencies implements Frequencies<Integer> {
	
	private final TIntDoubleHashMap m;
	private int length;
	
	private IntFrequencies(TIntDoubleHashMap m, int length){
		this.m = m;
		this.length = length;
	}
	
	public static IntFrequencies create(){
		TIntDoubleHashMap m = new TIntDoubleHashMap();
		return new IntFrequencies(m,0);
	}
		
	public static IntFrequencies create(IntIterable data) {
		IntFrequencies f = create();
		IntIterator it = data.ints();
		
		while(it.hasNext()) f.add(it.nextInt());
		return f;
	}
	
	public static IntFrequencies create(int[] data){
		return create(IntIterable.of(data));
	}
	
	public static Frequencies<Integer> merge(Collection<Frequencies<Integer>> freqs){
		if(freqs.isEmpty()) throw new IllegalArgumentException("Frequencies empty");
		TIntDoubleHashMap m = new TIntDoubleHashMap();
		Set<Integer> lengths = new HashSet<>();
		for(Frequencies<Integer> freq:freqs){
			lengths.add(freq.getLength());
			for(Integer x:freq.getEvents()){
				double y = freq.getFrequency(x);
				m.adjustOrPutValue(x, y, y);
			}
		}
		int length = ListFilter.singleton(lengths).get();
		return new IntFrequencies(m,length);
	}
	
	
	public static Frequencies<Integer> average(Collection<Frequencies<Integer>> freqs){
		TIntDoubleHashMap m = new TIntDoubleHashMap();
		Frequencies<Integer> f = merge(freqs);
		final int size = freqs.size();
		for(Integer x:f.getEvents()){
			m.put(x, f.getFrequency(x) / size);
		}
		int length = f.getLength();
		return new IntFrequencies(m,length);
	}
	
	@Override
	public double getFrequency(Integer x){
		return m.get(x);
	}
	
	@Override
	public int getLength(){
		return length;
	}
	
	@Override
	public double getProbability(Integer x){
		return getFrequency(x) / getLength();
	}
	
	@Override
	public Set<Integer> getEvents(){
		return Arrays.stream(m.keys())
				.boxed()
				.collect(Collectors.toSet());
	}

	@Override
	public void add(Integer x) {
		m.adjustOrPutValue(x,1,1);
		length++;
	}
}
