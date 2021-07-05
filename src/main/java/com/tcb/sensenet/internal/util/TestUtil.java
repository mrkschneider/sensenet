package com.tcb.sensenet.internal.util;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class TestUtil {
		
	public static <T> void assertListEquals(List<T> a, List<T> b){
		int aSize = a.size();
		int bSize = b.size();
		if(aSize!=bSize) throw new SizeMismatchError(String.format(
				"Size mismatch: %d vs. %d",aSize,bSize));
		for(int i=0;i<a.size();i++) {
			T aV = a.get(i);
			T bV = b.get(i);
			if(!aV.equals(bV)) throw new AssertionError(String.format(
					"Elements at position %d not equal: %s vs. %s",i,aV.toString(),bV.toString()));
		}
	}
	
	public static void assertArrayEquals(double[] a, double[] b) {
		assertListEquals(
				Arrays.stream(a).boxed().collect(Collectors.toList()),
				Arrays.stream(b).boxed().collect(Collectors.toList()));
	}
	
	public static void assertArrayEquals(int[] a, int[] b) {
		assertListEquals(
				Arrays.stream(a).boxed().collect(Collectors.toList()),
				Arrays.stream(b).boxed().collect(Collectors.toList()));
	}
}
