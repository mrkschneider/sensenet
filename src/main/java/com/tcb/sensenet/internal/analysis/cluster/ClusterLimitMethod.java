package com.tcb.sensenet.internal.analysis.cluster;

public enum ClusterLimitMethod {
	TARGET_NUMBER, EPSILON;
	
	public String toString(){
		switch(this){
		case TARGET_NUMBER: return "Target cluster count";
		case EPSILON: return "Epsilon";
		default: throw new UnsupportedOperationException();
		}
	}
}
