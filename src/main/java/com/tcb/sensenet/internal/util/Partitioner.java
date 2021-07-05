package com.tcb.sensenet.internal.util;

import java.util.List;

import com.google.common.collect.ImmutableList;

public class Partitioner {


	public static <T> List<List<T>> partition(List<T> lst, Integer partitions){
		final int totalSize = lst.size();
		if(partitions < 1) throw new IllegalArgumentException("Number of partitions must be >= 1");
		if(totalSize % partitions != 0) throw new IllegalArgumentException(
				"Number of partitions must be a factor of list length");
		ImmutableList.Builder<List<T>> result = ImmutableList.builder();
		final int blockSize = totalSize / partitions;
		final int limit = totalSize - (blockSize - 1);
		for(int i=0;i<limit;i+=blockSize){
			List<T> subLst = lst.subList(i, i+blockSize);
			result.add(subLst);
		}
		return result.build();
	}
}
