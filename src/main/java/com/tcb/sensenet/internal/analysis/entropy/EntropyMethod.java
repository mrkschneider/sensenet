package com.tcb.sensenet.internal.analysis.entropy;

import com.tcb.sensenet.internal.aggregation.methods.AggregationMethod;

public enum EntropyMethod implements AggregationMethod {
	SHANNON;
	
	public String toString(){
		switch(this){
		case SHANNON: return "Shannon";
		default: throw new IllegalArgumentException();
		}
	}
}
