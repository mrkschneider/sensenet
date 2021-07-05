package com.tcb.sensenet.internal.path.analysis.centrality.weight.negative;

public enum NegativeValuesMode {
	ABSOLUTE_VALUE;
	
	public String toString(){
		switch(this){
		case ABSOLUTE_VALUE: return "abs(x)";
		default: throw new UnsupportedOperationException();
		}
	}
}
