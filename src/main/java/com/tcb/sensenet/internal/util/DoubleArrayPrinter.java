package com.tcb.sensenet.internal.util;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.google.common.collect.ImmutableList;
import com.tcb.common.util.Rounder;

public class DoubleArrayPrinter {
	private Integer decimalsAfterPoint;
	private String separator;

	public DoubleArrayPrinter(String separator, Integer decimalsAfterPoint){
		this.decimalsAfterPoint = decimalsAfterPoint;
		this.separator = separator;
	}
	
	public String toString(double[] arr){
		List<Double> values = Arrays.stream(arr)
				.boxed()
				.map(d -> Rounder.round(d, decimalsAfterPoint))
				.collect(ImmutableList.toImmutableList());
		String s = values.stream()
				.map(d -> d.toString())
				.collect(Collectors.joining(separator));
		return s;	
	}
}
