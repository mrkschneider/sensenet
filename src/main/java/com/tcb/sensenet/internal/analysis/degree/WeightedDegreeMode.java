package com.tcb.sensenet.internal.analysis.degree;

public enum WeightedDegreeMode {
	ADJACENT_EDGE_WEIGHT_SUM;
	
	public String toString(){
		switch(this){
		case ADJACENT_EDGE_WEIGHT_SUM: return "Edge weight sum";
		default: throw new UnsupportedOperationException();
		}
	}
}
