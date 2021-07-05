package com.tcb.sensenet.internal.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.tcb.common.util.SafeMap;

public class SieveUtil {
	public static <T> List<T> sieve(List<T> lst, int sieve){
		if(sieve < 1) throw new IllegalArgumentException("Sieve must be >= 1");
		List<T> result = new ArrayList<>();
		final int size = lst.size();
		for(int i=0;i<size;i+=sieve){
			result.add(lst.get(i));
		}
		return result;
	}
	
	public static <T> Map<Integer,T> sieveMap(List<T> lst, int sieve){
		Map<Integer,T> result = new SafeMap<Integer,T>();
		List<T> sieved = sieve(lst, sieve);
		int i=0;
		for(T e:sieved){
			result.put(i, e);
			i+=sieve;
		}
		return result;
	}
}
