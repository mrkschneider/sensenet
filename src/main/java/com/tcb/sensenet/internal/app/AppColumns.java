package com.tcb.sensenet.internal.app;

import static com.tcb.sensenet.internal.CyActivator.APP_COLUMN_PREFIX;

import com.tcb.cytoscape.cyLib.data.Columns;

public enum AppColumns implements Columns {
	SELECTED_CLUSTER_INDEX, CLUSTERING_MODE,
	CUTOFF_COLUMN,
	ALTLOC, RESINSERT,
	SECONDARY_STRUCTURE,
	SELECTION_TIME,
	IS_PROTECTION_NETWORK,
	IMPORTED,
	CORRELATION_FACTOR,
	WEIGHT,
	SOURCE_NODE_NAME,TARGET_NODE_NAME,
	SOURCE_GROUP,TARGET_GROUP,
	GROUP,
	LABEL,
	RESINDEX,
	RESNAME,
	CHAIN,
	RESINDEX_LABEL,
	RESINSERT_LABEL,
	TIMELINE,
	COLLAPSED,
	ATOM_NAME,
	ERROR_ESTIMATE,
	STANDARD_DEVIATION,
	METATIMELINE_TYPE,
	UUID,
	METANETWORKS_APP,
	GROUP_TAG,
	LIFETIME,
	CUTOFF_VALUE,
	AGGREGATION_MODE,
	SELECTED_FRAME,
	TIMELINE_LENGTH,
	BRIDGE_NAME,
	AUTOCORRELATION_TIME,
	AUTOCORRELATION_SAMPLE_SIZE,
	OCCURRENCE, AVERAGE_INTERACTIONS,
	TIMEPOINT_WEIGHT_CUTOFF,
	MUTATED_RESNAME,
	INTERACTION_TYPE,
	IS_METANODE,
	IS_METAEDGE,
	METANODE_SUID,
	METAEDGE_SUID,
	AVAILABLE_INTERACTION_TYPES, SELECTED_INTERACTION_TYPES, 
	SOURCE_CHAIN, TARGET_CHAIN,
	CENTRALITY,
	DEGREE,
	VISITED,
	DIVERGENCE,
	CONVERGENCE_TIME,
	ENTROPY
	;
	
	public String toString(){
		return APP_COLUMN_PREFIX + toRawString();
	}

	
	public String toRawString(){
		switch(this){
		case SELECTED_CLUSTER_INDEX: return "selected cluster index";
		case CLUSTERING_MODE: return "clustering mode";
		case CUTOFF_COLUMN: return "cutoff column";
		case ALTLOC: return "altloc";
		case RESINSERT: return "residue insert";
		case SECONDARY_STRUCTURE: return "secondary structure";
		case SELECTION_TIME: return "selection time";
		case IS_PROTECTION_NETWORK : return "protection network";
		case IMPORTED: return "imported";
		case CORRELATION_FACTOR: return "correlation factor";
		case WEIGHT: return "weight";
		case SOURCE_NODE_NAME: return "source name";
		case TARGET_NODE_NAME: return "target name";
		case SOURCE_GROUP: return "source group";
		case TARGET_GROUP: return "target group";
		case GROUP: return "node group";
		case LABEL: return "label";
		case RESINDEX: return "residue index";
		case RESNAME: return "residue name";
		case CHAIN: return "chain";
		case RESINDEX_LABEL: return "residue index label";
		case RESINSERT_LABEL: return "residue insert label";
		case TIMELINE: return "timeline";
		case COLLAPSED: return "collapsed";
		case ATOM_NAME: return "atom name";
		case ERROR_ESTIMATE: return "error estimate";
		case STANDARD_DEVIATION: return "standard deviation";
		case METATIMELINE_TYPE: return "weight method";
		case UUID: return "uuid";
		case METANETWORKS_APP: return "app";
		case GROUP_TAG: return "group tag";
		case LIFETIME: return "lifetime";
		case CUTOFF_VALUE: return "edge display cutoff";
		case AGGREGATION_MODE: return "aggregation mode";
		case SELECTED_FRAME: return "selected frame";
		case TIMELINE_LENGTH: return "timeline length";
		case BRIDGE_NAME: return "bridge name";
		case AUTOCORRELATION_TIME: return "autocorrelation time";
		case AUTOCORRELATION_SAMPLE_SIZE: return "autocorrelation sample size";
		case OCCURRENCE: return "avg. occurrence";
		case AVERAGE_INTERACTIONS: return "avg. interactions";
		case TIMEPOINT_WEIGHT_CUTOFF: return "timepoint weight cutoff";
		case MUTATED_RESNAME: return "mutated residue name";
		case INTERACTION_TYPE: return "interaction type";
		case IS_METANODE: return "is-metanode";
		case IS_METAEDGE: return "is-metaedge";
		case METANODE_SUID: return "metanode.SUID";
		case METAEDGE_SUID: return "metaedge.SUID";
		case AVAILABLE_INTERACTION_TYPES: return "interaction types";
		case SELECTED_INTERACTION_TYPES: return "selected interaction types";
		case SOURCE_CHAIN: return "source chain";
		case TARGET_CHAIN: return "target chain";
		case CENTRALITY: return "centrality";
		case DEGREE: return "degree";
		case VISITED: return "visited";
		case DIVERGENCE: return "divergence";
		case CONVERGENCE_TIME: return "convergence time";
		case ENTROPY: return "entropy";
		default: throw new IllegalArgumentException("Unknown column: " + this.name());
		}
	}
	
	@Override
	public String toShortString() {
		switch(this){
		case OCCURRENCE: return "occ.";
		case AVERAGE_INTERACTIONS: return "avg.";
		case CORRELATION_FACTOR: return "corf.";
		case LIFETIME: return "lifetime";
		case ERROR_ESTIMATE: return "error";
		case STANDARD_DEVIATION: return "stdev.";
		case WEIGHT: return "weight";
			//$CASES-OMITTED$
		default: return this.toString();
		}
	}
}
