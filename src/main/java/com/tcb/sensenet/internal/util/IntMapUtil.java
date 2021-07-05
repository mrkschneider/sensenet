package com.tcb.sensenet.internal.util;

import java.util.List;
import java.util.Map;

import com.google.common.collect.ImmutableList;
import com.tcb.common.util.Tuple;
import com.tcb.sensenet.internal.analysis.matrix.ContactMatrix;

public class IntMapUtil {
	public static <T> Tuple<List<Integer>,List<T>> getSortedKeysValues(Map<Integer,T> map){
		List<Integer> sortedKeys = map.keySet().stream()
				.sorted()
				.collect(ImmutableList.toImmutableList());
		List<T> values = sortedKeys.stream()
				.map(k -> map.get(k))
				.collect(ImmutableList.toImmutableList());
		return new Tuple<>(sortedKeys,values);
	}
}
