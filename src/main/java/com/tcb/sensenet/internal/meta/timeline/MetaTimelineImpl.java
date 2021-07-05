package com.tcb.sensenet.internal.meta.timeline;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.PrimitiveIterator;
import java.util.PrimitiveIterator.OfDouble;
import java.util.PrimitiveIterator.OfInt;
import java.util.stream.Collectors;

import com.google.common.collect.ImmutableList;
import com.tcb.sensenet.internal.util.ArrayUtils;
import com.tcb.sensenet.internal.util.iterator.DefaultIntIterator;
import com.tcb.sensenet.internal.util.iterator.DoubleIterable;
import com.tcb.sensenet.internal.util.iterator.DoubleIterator;
import com.tcb.sensenet.internal.util.iterator.IntIterable;
import com.tcb.sensenet.internal.util.iterator.IntIterator;

public class MetaTimelineImpl implements MetaTimeline,Serializable {
	private static final long serialVersionUID = 2L;
	
	private final double[] timeline;
		
	public static MetaTimeline create(double[] data){
		return new MetaTimelineImpl(data);
	}
	
	public static MetaTimeline create(DoubleIterable data) {
		return create(DoubleIterable.toArray(data));
	}
		
	private MetaTimelineImpl(double[] data){
		this.timeline = data;
	}
	
	@Override
	public Integer getLength() {
		return timeline.length;
	}
	
	@Override
	public double[] getData() {
		return timeline;
	}
	
	@Override
	public double get(int frame){
		return timeline[frame];
	}
	
	@Override
	public int getInt(int frame) {
		return (int)get(frame);
	}
	
	@Override
	public DoubleIterator doubles() {
		return DoubleIterator.of(timeline);
	}

	@Override
	public IntIterator ints() {
		return new DefaultIntIterator(
				Arrays.stream(timeline)
				.mapToInt(d -> (int)d)
				.iterator());
	}

	@Override
	public List<Double> asDoubles() {
		return Arrays.stream(timeline)
				.boxed()
				.collect(Collectors.toList());
	}

	@Override
	public List<Integer> asInts() {
		List<Integer> result = new ArrayList<>();
		PrimitiveIterator.OfInt it = ints();
		while(it.hasNext()) result.add(it.nextInt());
		return result;
	}

	@Override
	public int size() {
		return getLength();
	}
	
}
