package com.tcb.sensenet.internal.meta.timeline;

import java.util.List;
import java.util.PrimitiveIterator;

import com.tcb.sensenet.internal.util.iterator.DoubleIterable;
import com.tcb.sensenet.internal.util.iterator.IntIterable;


public interface MetaTimeline extends IntIterable,DoubleIterable {
	public Integer getLength();
	public double[] getData();
	public double get(int frame);
	public int getInt(int frame);
	public List<Double> asDoubles();
	public List<Integer> asInts();
}
