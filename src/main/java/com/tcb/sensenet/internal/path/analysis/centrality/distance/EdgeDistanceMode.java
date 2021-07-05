package com.tcb.sensenet.internal.path.analysis.centrality.distance;

public enum EdgeDistanceMode {
	NONE, INVERSE, NEGATIVE_EXPONENTIAL;
	
	public String toString(){
		switch(this){
		case NONE: return "None";
		case INVERSE: return "1 / x";
		case NEGATIVE_EXPONENTIAL: return "exp(-x)";
		default: throw new UnsupportedOperationException();
		}
	}
}
