package com.tcb.sensenet.internal.analysis.correlation;

public enum EdgeCorrelationMethod {
	PEARSON, MUTUAL_INFORMATION, DIFFERENCE_MUTUAL_INFORMATION;
		
	public String toString(){
		switch(this){
		case PEARSON: return "Pearson";
		case MUTUAL_INFORMATION: return "Mutual information";
		case DIFFERENCE_MUTUAL_INFORMATION: return "Mutual information difference";
		//case INDEPENDENT_INFORMATION: return "Independent information";
		default: throw new IllegalArgumentException("Unknown enum");
		}
	}
}
