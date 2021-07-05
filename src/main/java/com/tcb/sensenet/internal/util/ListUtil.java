package com.tcb.sensenet.internal.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ListUtil {
	
	public static <T> List<T> getEveryNth(List<T> lst, int n){
		return IntStream.range(0, lst.size())
				.filter(i -> i % n == 0)
				.mapToObj(i -> lst.get(i))
				.collect(Collectors.toList());
	}
	
	public static <T> int maxKey(List<T> lst, Function<T,? extends Number> f) {
		if(lst.isEmpty()) throw new IllegalArgumentException("List is empty");
		int k = 0;
		double maxV = Double.NEGATIVE_INFINITY;
		for(int i=0;i<lst.size();i++) {
			T x = lst.get(i);
			double v = f.apply(x).doubleValue();
			if(v > maxV) {
				maxV = v;
				k = i;
			}
		}
		return k;
	}
	
	public static <T> int minKey(List<T> lst, Function<T,? extends Number> f) {
		if(lst.isEmpty()) throw new IllegalArgumentException("List is empty");
		int k = 0;
		double minV = Double.POSITIVE_INFINITY;
		for(int i=0;i<lst.size();i++) {
			T x = lst.get(i);
			double v = f.apply(x).doubleValue();
			if(v < minV) {
				minV = v;
				k = i;
			}
		}
		return k;
	}
	
	public static<T> int firstKey(List<T> lst, Function<T,Boolean> f) {
		for(int i=0;i<lst.size();i++) {
			T x = lst.get(i);
			if(f.apply(x)) return i;
		}
		return -1;
	}

}
