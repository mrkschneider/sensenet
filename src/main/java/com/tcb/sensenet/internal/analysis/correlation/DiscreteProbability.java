package com.tcb.sensenet.internal.analysis.correlation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;

import com.tcb.sensenet.internal.util.ArrayUtils;

public class DiscreteProbability {
	public static int[] getEvents(int[] data){
		LinkedHashSet<Integer> events = new LinkedHashSet<>();
		for(int i=0;i<data.length;i++) events.add(data[i]);
		List<Integer> sortedEvents = new ArrayList<>(events);
		Collections.sort(sortedEvents);
		return sortedEvents.stream()
				.mapToInt(i -> i)
				.toArray();
	}
	
	public static double getProbability(int event, int[] data){
		return ((double)ArrayUtils.countValues(event,data)) / data.length;
	}
	
	public static double getJointProbability(
			int eventA,
			int eventB,
			int[] dataA,
			int[] dataB){
		final int size = dataA.length;
		int count = 0;
		for(int i=0;i<size;i++){
			int valA = dataA[i];
			int valB = dataB[i];
			if(valA==eventA && valB==eventB){
				count++;
			}
		}
		return ((double)count) / size;
	}
}
