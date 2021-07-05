package com.tcb.sensenet.internal.path.analysis.centrality;

public enum NodeCentralityType {
	BETWEENNESS, STRESS, PATH_LENGTH;
	
	public String toString(){
		switch(this){
		case BETWEENNESS: return "Betweenness centrality";
		case STRESS: return "Stress centrality";
		case PATH_LENGTH: return "Path length centrality";
		default: throw new UnsupportedOperationException();
		}
	}
}
