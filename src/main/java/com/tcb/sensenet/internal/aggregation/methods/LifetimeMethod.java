package com.tcb.sensenet.internal.aggregation.methods;

public enum LifetimeMethod implements AggregationMethod {
	LIFETIME;
	
	public String toString(){
		switch(this){
		case LIFETIME: return "Lifetime";
		default: throw new IllegalArgumentException();
		}
	}
}
