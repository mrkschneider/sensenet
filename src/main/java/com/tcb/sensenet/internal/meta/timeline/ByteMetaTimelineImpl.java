package com.tcb.sensenet.internal.meta.timeline;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.google.common.collect.ImmutableList;
import com.tcb.sensenet.internal.util.ArrayUtils;
import com.tcb.sensenet.internal.util.DeprecatedException;
import com.tcb.sensenet.internal.util.iterator.IntIterator;

@Deprecated
public class ByteMetaTimelineImpl implements MetaTimeline,Serializable {
	private static final long serialVersionUID = 1L;

	private final byte[] timeline;
		
	public byte[] getTimelineDeprecated() {
		return timeline;
	}
	
	public static ByteMetaTimelineImpl create(float[] data){
		throw new DeprecatedException();
	}
	
	public ByteMetaTimelineImpl(byte[] data){
		throw new DeprecatedException();
	}
	
	@Override
	public List<Double> asDoubles() {
		throw new DeprecatedException();
	}

	@Override
	public double get(int frame) {
		throw new DeprecatedException();
	}

	@Override
	public int getInt(int frame) {
		throw new DeprecatedException();
	}

	@Override
	public List<Integer> asInts() {
		throw new DeprecatedException();
	}

	@Override
	public double[] getData() {
		throw new DeprecatedException();
	}

	@Override
	public IntIterator ints() {
		throw new DeprecatedException();
	}

	@Override
	public int size() {
		throw new DeprecatedException();
	}

	@Override
	public Integer getLength() {
		throw new DeprecatedException();
	}

}
