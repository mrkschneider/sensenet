package com.tcb.sensenet.internal.analysis.autocorrelation.replicas;

public enum ReplicaAutocorrelationTimeWeightMethod {
	MAX, AVERAGE, MIN;
	
	public String toString(){
		switch(this){
		case MAX: return "Max";
		case AVERAGE: return "Avg";
		case MIN: return "Min";
		//case CHANNEL: return "decay channel";
		default: throw new IllegalArgumentException();
		}
	}
	
}
