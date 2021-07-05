package com.tcb.sensenet.internal.util.iterator;

import java.util.Arrays;
import java.util.Collection;
import java.util.PrimitiveIterator;

public interface IntIterator extends PrimitiveIterator.OfInt  {
	public static IntIterator of(int[] xs) {
		return new DefaultIntIterator(Arrays.stream(xs).iterator());
	}
	
	public static IntIterator of(Collection<Integer> xs) {
		return new DefaultIntIterator(xs.stream()
				.mapToInt(i -> i)
				.iterator());
	}
}
