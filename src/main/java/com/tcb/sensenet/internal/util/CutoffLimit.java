package com.tcb.sensenet.internal.util;

import com.tcb.common.util.Predicate;

public class CutoffLimit implements Predicate<Double>{

	protected Double lowIncluding;
	protected Double highExcluding;

	public CutoffLimit(Double lowIncluding, Double highExcluding){
		this.lowIncluding = lowIncluding;
		this.highExcluding = highExcluding;
	}
	
	@Override
	public boolean test(Double cutoff) {
		return lowIncluding <= cutoff && cutoff < highExcluding;
	}
	
}
