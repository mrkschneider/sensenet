package com.tcb.sensenet.internal.analysis.degree;

public enum WeightedDegreeNormalizationMode {
	NONE, MIN_MAX, Z_SCORE;
	
	public String toString(){
		switch(this){
		case NONE: return "None";
		case MIN_MAX: return "Min-max range";
		case Z_SCORE: return "Z-score";
		default: throw new UnsupportedOperationException();
		}
	}
}
