package com.tcb.sensenet.internal.util;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import com.tcb.common.util.SafeMap;

public class ZipUtil {
	public static <T,U> Map<T,U> zipMap(List<T> l1, List<U> l2){
		if(l1.size()!=l2.size()){
			throw new IllegalArgumentException("Zipped lists must be of equal length");
		}
		Map<T,U> map = new SafeMap<>();
		final int size = l1.size();
		for(int i=0;i<size;i++){
			T o1 = l1.get(i);
			U o2 = l2.get(i);
			map.put(o1, o2);
		}
		return map;
	}
	
	public static <U> Map<Integer,U> zipMapIndex(List<U> l1){
		List<Integer> indices = IntStream.range(0,l1.size())
				.boxed().collect(Collectors.toList());
		return zipMap(indices,l1);
	}
	
}
