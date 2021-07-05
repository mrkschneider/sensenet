package com.tcb.sensenet.internal.log;

public enum TaskLogType {
	CLUSTER,
	WEIGHT,
	WEIGHT_ERRORS, LIFETIME, 
	CORRELATION_FACTORS, CORRELATION, IMPORT, TIMELINE,
	NODE_CENTRALITY,
	DEGREE,
	DIFFUSION,
	DIVERGENCE,
	ENTROPY;
	
	public String toString(){
		switch(this){
		case CLUSTER: return "Cluster";
		case WEIGHT: return "Weight";
		case WEIGHT_ERRORS: return "Weight errors";
		case LIFETIME: return "Lifetime";
		case CORRELATION: return "Correlation";
		case CORRELATION_FACTORS: return "Correlation factors";
		case IMPORT: return "Import";
		case TIMELINE: return "Timeline";
		case NODE_CENTRALITY: return "Node centrality";
		case DEGREE: return "Degree";
		case DIFFUSION: return "Diffusion";
		case DIVERGENCE: return "Divergence";
		case ENTROPY: return "Entropy";
		default: throw new IllegalArgumentException();
		}
	}
}
