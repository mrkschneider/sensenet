package com.tcb.sensenet.internal.util;

public class AbsoluteCutoffLimit extends CutoffLimit {

	public AbsoluteCutoffLimit(Double lowIncluding, Double highExcluding) {
		super(lowIncluding, highExcluding);
	}
	
	@Override
	public boolean test(Double cutoff){
		return super.test(Math.abs(cutoff));
	}
}
