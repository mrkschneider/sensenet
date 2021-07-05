package com.tcb.sensenet.internal.util.iterator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.PrimitiveIterator;
import java.util.stream.DoubleStream;

public interface DoubleIterable {
	public DoubleIterator doubles();
	public int size();
		
	public static double[] toArray(DoubleIterable iterable) {
		int size = iterable.size();
		double[] result = new double[size];
		DoubleIterator it = iterable.doubles();
		int i = 0;
		while(it.hasNext()) {
			result[i] = it.nextDouble();
			i++;
		}
		if(i!=size) throw new IllegalArgumentException("Size mismatch");
		return result;
	}
	
	public static List<Double> toList(DoubleIterable iterable){
		List<Double> result = new ArrayList<>();
		DoubleIterator it = iterable.doubles();
		while(it.hasNext()) result.add(it.nextDouble());
		return result;
	}
	
	public static DoubleIterable of(double[] xs) {
		return new DoubleIterable() {	
			@Override
			public DoubleIterator doubles() {
				return DoubleIterator.of(xs);
			}
				
			@Override
			public int size() {
				return xs.length;
			}
		};
	}
	
	public static DoubleIterable of(Collection<Double> xs) {
		return new DoubleIterable() {	
			@Override
			public DoubleIterator doubles() {
				return DoubleIterator.of(xs);
			}
				
			@Override
			public int size() {
				return xs.size();
			}

		};
	}
	
}
