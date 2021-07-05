package com.tcb.sensenet.internal.util.iterator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.PrimitiveIterator;

public interface IntIterable extends DoubleIterable {
	public IntIterator ints();
	
	public default DoubleIterator doubles() {
		IntIterator it = ints();
		return new DoubleIterator() {
			@Override
			public double nextDouble() {
				return (double)it.nextInt();
			}
			
			@Override
			public boolean hasNext() {
				return it.hasNext();
			}
		};
	}
	
	public static List<Integer> toList(IntIterable iterable){
		List<Integer> result = new ArrayList<>();
		IntIterator it = iterable.ints();
		while(it.hasNext()) result.add(it.nextInt());
		return result;
	}
	
	public static int[] toArray(IntIterable iterable) {
		int size = iterable.size();
		int[] result = new int[size];
		IntIterator it = iterable.ints();
		int i = 0;
		while(it.hasNext()) {
			result[i] = it.nextInt();
			i++;
		}
		if(i!=size) throw new IllegalArgumentException("Size mismatch");
		return result;
	}
		
	public static IntIterable of(int[] xs) {
		return new IntIterable() {
			@Override
			public IntIterator ints() {
				return IntIterator.of(xs);
			}

			@Override
			public int size() {
				return xs.length;
			}
			
		};
	}
	
	public static IntIterable of(Collection<Integer> xs) {
		return new IntIterable() {
			@Override
			public IntIterator ints() {
				return IntIterator.of(xs);
			}

			@Override
			public int size() {
				return xs.size();
			}
			
		};
	}
}
