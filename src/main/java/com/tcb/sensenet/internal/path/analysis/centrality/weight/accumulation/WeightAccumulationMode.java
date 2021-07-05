package com.tcb.sensenet.internal.path.analysis.centrality.weight.accumulation;

public enum WeightAccumulationMode {
	SUM, MAX, MIN, EDGE_COUNT, UNIFORM;
	
	public String toString(){
		switch(this){
		case UNIFORM: return "Uniform";
		case EDGE_COUNT: return "Edge count";
		case SUM: return "Sum";
		case MAX: return "Max";
		case MIN: return "Min";
		default: throw new UnsupportedOperationException();
		}
	}
}
