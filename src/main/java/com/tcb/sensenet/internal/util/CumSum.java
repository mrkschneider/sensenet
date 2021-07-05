package com.tcb.sensenet.internal.util;

import java.util.ArrayList;
import java.util.List;

public class CumSum {

	public static List<Double> getCumSums(List<Double> coll){
		if(coll.isEmpty()) return new ArrayList<>();
		List<Double> cumSums = new ArrayList<>(coll.size());
		Double cumSum = coll.get(0);
		cumSums.add(cumSum);
		for(int i=1;i<coll.size();i++) 
			cumSums.add(coll.get(i) + cumSums.get(i-1));
		return cumSums;
	}
}
