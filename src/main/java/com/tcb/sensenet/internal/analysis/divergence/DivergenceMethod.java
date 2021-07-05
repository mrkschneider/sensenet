package com.tcb.sensenet.internal.analysis.divergence;

import com.tcb.sensenet.internal.aggregation.methods.AggregationMethod;

public enum DivergenceMethod implements AggregationMethod {
	SYMMETRICAL_KULLBACK_LEIBLER, JENSEN_SHANNON, POPULATION_SHIFT;
	
	public String toString(){
		switch(this){
		case SYMMETRICAL_KULLBACK_LEIBLER: return "Kullback-Leibler symm.";
		case JENSEN_SHANNON: return "Jensen-Shannon";
		case POPULATION_SHIFT: return "Population shift";
		default: throw new IllegalArgumentException();
		}
	}
}
