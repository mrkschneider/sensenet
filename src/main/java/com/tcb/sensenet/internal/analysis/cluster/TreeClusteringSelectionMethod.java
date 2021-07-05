package com.tcb.sensenet.internal.analysis.cluster;

public enum TreeClusteringSelectionMethod {
	MAX_COMPENSATED_FLUX;
	
	public String toString(){
		switch(this){
		case MAX_COMPENSATED_FLUX: return "Max comp. flux";
		default: throw new IllegalArgumentException();
		}
	}
	
	public ClusteringSelecter getSelecter(){
		switch(this){
		case MAX_COMPENSATED_FLUX: return new MaxCompensatedFluxClusteringSelecter();
		default: throw new IllegalArgumentException();
		}
	}
}
