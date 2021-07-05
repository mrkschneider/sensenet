package com.tcb.sensenet.internal.analysis.correlation;

import java.util.Arrays;
import java.util.Map;
import java.util.PrimitiveIterator;
import java.util.Set;

import org.apache.commons.lang3.tuple.Pair;

import com.tcb.sensenet.internal.util.iterator.IntIterable;
import com.tcb.sensenet.internal.util.iterator.IntIterator;

import gnu.trove.map.hash.TIntIntHashMap;
import gnu.trove.map.hash.TObjectDoubleHashMap;
import gnu.trove.map.hash.TObjectIntHashMap;

public class JointIntFrequencies implements Frequencies<Pair<Integer,Integer>> {
	
	private final TObjectDoubleHashMap<Pair<Integer,Integer>> m;
	private int length;
	
	private JointIntFrequencies(TObjectDoubleHashMap<Pair<Integer,Integer>> m, int length){
		this.m = m;
		this.length = length;
	}
	
	public static JointIntFrequencies create() {
		TObjectDoubleHashMap<Pair<Integer,Integer>> m = new TObjectDoubleHashMap<>();
		return new JointIntFrequencies(m,0);
	}
	
	public static JointIntFrequencies create(
			IntIterable dataA,
			IntIterable dataB){
		JointIntFrequencies f = create();
		IntIterator itA = dataA.ints();
		IntIterator itB = dataB.ints();
		
		if(dataA.size()!=dataB.size())
			throw new IllegalArgumentException("Arrays must have same length");
		
		while(itA.hasNext()) {
			int dA = itA.nextInt();
			int dB = itB.nextInt();
			Pair<Integer,Integer> p = Pair.of(dA, dB);
			f.add(p);
		}
		
		return f;
	}
	
	public static JointIntFrequencies create(int[] dataA, int[] dataB){
		return create(IntIterable.of(dataA), IntIterable.of(dataB));
	}
	
	public double getFrequency(Pair<Integer,Integer> x){
		return m.get(x);
	}
	
	public int getLength(){
		return length;
	}
	
	public double getProbability(Pair<Integer,Integer> x){
		return getFrequency(x) / getLength();
	}
	
	public Set<Pair<Integer,Integer>> getEvents(){
		return m.keySet();
	}

	@Override
	public void add(Pair<Integer, Integer> x) {
		m.adjustOrPutValue(x, 1, 1);
		length++;
	}
}