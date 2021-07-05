package com.tcb.sensenet.internal.util.iterator;

import java.util.Arrays;
import java.util.Collection;
import java.util.PrimitiveIterator;
import java.util.PrimitiveIterator.OfDouble;

public interface DoubleIterator extends PrimitiveIterator.OfDouble {
	public static DoubleIterator of(double[] xs) {
		return new DefaultDoubleIterator(Arrays.stream(xs).iterator());
	}
		
	public static DoubleIterator of(Collection<Double> xs) {
		return new DefaultDoubleIterator(
				xs.stream().mapToDouble(d -> d).iterator());
	}
}
