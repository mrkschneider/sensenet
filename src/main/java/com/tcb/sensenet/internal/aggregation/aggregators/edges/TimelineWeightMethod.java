package com.tcb.sensenet.internal.aggregation.aggregators.edges;

public enum TimelineWeightMethod {
	AVERAGE_FRAME, SINGLE_FRAME, CLUSTERS;
	
	public String toString(){
		switch(this){
		case AVERAGE_FRAME: return "Average frame";
		case SINGLE_FRAME: return "Single frame";
		case CLUSTERS: return "Clusters";
		default: throw new IllegalArgumentException();
		}
		
	}
}
