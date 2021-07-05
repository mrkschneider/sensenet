package com.tcb.sensenet.internal.path.analysis.centrality.normalization;

public enum CentralityNormalizationMode {
	NONE, MIN_MAX, MAX_NODE_PAIRS, Z_SCORE;
	
	public String toString(){
		switch(this){
		case NONE: return "None";
		case MIN_MAX: return "Min-max range";
		case MAX_NODE_PAIRS: return "Max node pairs";
		case Z_SCORE: return "Z-score";
		default: throw new UnsupportedOperationException();
		}
	}
}
