package com.tcb.sensenet.internal.util;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class RangeUtil {
		
	public static List<Integer> intRange(int lowIncludes, int highExcludes, int step){
		if(step < 1){
			throw new IllegalArgumentException("Step must be >= 1");
		}
		List<Integer> result = new ArrayList<>();
		for(Integer i=lowIncludes;i<highExcludes;i+=step){
			result.add(i);
		}
		return result;
	}
		
	public static List<Integer> intRange(int lowIncludes, int highExcludes){
		return intRange(lowIncludes,highExcludes,1);
	}
	
	public static List<Double> doubleRange(int lowIncludes, int highExcludes, int step){
		return intRange(lowIncludes, highExcludes, step).stream()
				.map(i -> i.doubleValue())
				.collect(Collectors.toList());
	}
	
	public static List<Double> doubleRange(int lowIncludes, int highExcludes){
		return doubleRange(lowIncludes,highExcludes,1);
	}
}
