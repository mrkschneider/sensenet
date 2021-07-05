package com.tcb.sensenet.internal.aggregation.methods;

public enum ErrorMethod implements AggregationMethod {
	AUTOCORRELATION;
		
	public String toString(){
		switch(this){
		case AUTOCORRELATION: return "Autocorrelation";
		default: throw new IllegalArgumentException();
		}
	}
}
