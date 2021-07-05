package com.tcb.sensenet.internal.properties;

import java.awt.Color;

import com.tcb.atoms.interactions.InteractionType;
import com.tcb.cluster.linkage.LinkageStrategy;
import com.tcb.sensenet.internal.UI.panels.analysisPanel.network.correlation.CorrelationFactorsAnalysisType;
import com.tcb.sensenet.internal.UI.panels.importPanel.importDialog.groupDefinitionPanel.GroupInputMode;
import com.tcb.sensenet.internal.UI.panels.importPanel.importDialog.importPanel.ImportMode;
import com.tcb.sensenet.internal.UI.panels.stylePanel.auto.edges.EdgeAutoStyle;
import com.tcb.sensenet.internal.UI.panels.stylePanel.auto.nodes.NodeAutoStyle;
import com.tcb.sensenet.internal.aggregation.methods.ErrorMethod;
import com.tcb.sensenet.internal.aggregation.methods.timeline.FrameWeightMethod;
import com.tcb.sensenet.internal.analysis.autocorrelation.replicas.ReplicaAutocorrelationTimeWeightMethod;
import com.tcb.sensenet.internal.analysis.cluster.ClusterLimitMethod;
import com.tcb.sensenet.internal.analysis.cluster.ClusterMethod;
import com.tcb.sensenet.internal.analysis.cluster.TreeClusterMethod;
import com.tcb.sensenet.internal.analysis.cluster.TreeClusteringSelectionMethod;
import com.tcb.sensenet.internal.analysis.correlation.EdgeCorrelationMethod;
import com.tcb.sensenet.internal.analysis.degree.WeightedDegreeNormalizationMode;
import com.tcb.sensenet.internal.analysis.diffusion.RandomWalkMode;
import com.tcb.sensenet.internal.analysis.divergence.DivergenceMethod;
import com.tcb.sensenet.internal.analysis.degree.WeightedDegreeMode;
import com.tcb.sensenet.internal.app.AppColumns;
import com.tcb.sensenet.internal.log.LogType;
import com.tcb.sensenet.internal.log.TaskLogType;
import com.tcb.sensenet.internal.map.edge.EdgeMappingMethod;
import com.tcb.sensenet.internal.path.analysis.centrality.NodeCentralityType;
import com.tcb.sensenet.internal.path.analysis.centrality.distance.EdgeDistanceMode;
import com.tcb.sensenet.internal.path.analysis.centrality.normalization.CentralityNormalizationMode;
import com.tcb.sensenet.internal.path.analysis.centrality.weight.accumulation.WeightAccumulationMode;
import com.tcb.sensenet.internal.path.analysis.centrality.weight.negative.NegativeValuesMode;
import com.tcb.sensenet.internal.structureViewer.ViewerType;
import com.tcb.sensenet.internal.task.structureViewer.config.StructureType;
import com.tcb.cytoscape.cyLib.data.DefaultColumns;

public enum AppProperty {
	AUTOSTART_APP,
	DEFAULT_INTERACTION_IMPORTER,
	DEFAULT_TIMEFRACTION_CUTOFF,
	DEFAULT_TIMEFRACTION_CUTOFF_COLUMN,
	DEFAULT_GROUP_DEFINITION,
	DEFAULT_BACKBONE_NAMES,
	IMPORT_PDB_HBOND_DEFAULT_DISTANCE_CUTOFF,
	IMPORT_PDB_HBOND_DEFAULT_ANGLE_CUTOFF,
	IMPORT_PDB_HBOND_DEFAULT_DONOR_MASK,
	IMPORT_PDB_HBOND_DEFAULT_ACCEPTOR_MASK,
	IMPORT_PDB_HBOND_DEFAULT_INTERACTION_TYPE,
	IMPORT_PDB_CONTACT_DEFAULT_CUTOFF,
	IMPORT_PDB_CONTACT_DEFAULT_MASK,
	IMPORT_PDB_CONTACT_IGNORE_BB,
	IMPORT_PDB_CONTACT_IGNORE_INTRA_RESIDUE_CONTACTS,
	IMPORT_PDB_CONTACT_DEFAULT_INTERACTION_TYPE,
	IMPORT_AMBER_NATIVECONTACTS_IGNORE_BB,
	IMPORT_AMBER_NATIVECONTACTS_IGNORE_INTRA_RESIDUE_CONTACTS,
	IMPORT_AMBER_NATIVECONTACTS_DEFAULT_INTERACTION_TYPE,
	IMPORT_AMBER_HBOND_DEFAULT_INTERACTION_TYPE,
	IMPORT_AMBER_HBOND_IGNORE_BB,
	IMPORT_DSSP_DEFAULT_INTERACTION_TYPE,
	IMPORT_CREATE_VISUAL_STYLE,
	IMPORT_DEFAULT_SIEVE_FRAMES,
	IMPORT_DEFAULT_TIMELINE_MIN_AVG,
	CLUSTER_DEFAULT_METHOD,
	CLUSTER_TREE_DEFAULT_METHOD,
	CLUSTER_TREE_DEFAULT_CLUSTER_COUNT_SELECTION_METHOD,
	CLUSTER_DEFAULT_TARGET_CLUSTERS,
	CLUSTER_DEFAULT_EPSILON,
	CLUSTER_DEFAULT_WEIGHT_METHOD,
	CLUSTER_DEFAULT_CLUSTER_LIMIT,
	CLUSTER_DEFAULT_SIEVE,
	CLUSTER_AGGLOMERATIVE_DEFAULT_LINKAGE,
	STRUCTURE_VIEWER_DEFAULT,
	STRUCTURE_VIEWER_DEFAULT_STRUCTURE_TYPE,
	STRUCTURE_VIEWER_DEFAULT_COMMAND_PYMOL,
	STRUCTURE_VIEWER_DEFAULT_COMMAND_VMD,
	STRUCTURE_VIEWER_DEFAULT_COMMAND_CHIMERA,
	STRUCTURE_VIEWER_DEFAULT_ZOOM,
	STRUCTURE_VIEWER_DEFAULT_SELECTED_COLOR,
	STRUCTURE_VIEWER_DEFAULT_INTERACTION_COLOR,
	STRUCTURE_VIEWER_MAX_SHOWN_RESIDUES,
	STRUCTURE_VIEWER_MAX_SHOWN_INTERACTIONS,
	PATHS_SEARCH_FIXED_LENGTH_DEFAULT_MINLENGTH,
	PATHS_SEARCH_FIXED_LENGTH_DEFAULT_MAXLENGTH,
	WEIGHT_ERROR_DEFAULT_METHOD,
	WEIGHT_ERROR_DEFAULT_WEIGHT_METHOD,
	CORRELATION_FACTORS_DEFAULT_CORRELATION_METHOD,
	CORRELATION_FACTORS_DEFAULT_WEIGHT_METHOD,
	DIFFERENCE_CORRELATION_FACTORS_DEFAULT_EDGE_MAPPING_METHOD,
	DIFFERENCE_CORRELATION_FACTORS_DEFAULT_REF_NETWORK_NAME,
	CORRELATION_FACTORS_DEFAULT_ANALYSIS_TYPE,
	REPLICAS_DEFAULT,
	LOG_TYPE_DEFAULT,
	TASK_LOG_VIEW_TYPE_DEFAULT,
	AUTO_STYLE_NODE_DEFAULT_PROPERTY,
	AUTO_STYLE_NODE_DEFAULT_COLUMN_NAME,
	AUTO_STYLE_NODE_DEFAULT_LOW_COLOR,
	AUTO_STYLE_NODE_DEFAULT_MID_COLOR,
	AUTO_STYLE_NODE_DEFAULT_HIGH_COLOR,
	AUTO_STYLE_NODE_DEFAULT_MIN_SIZE,
	AUTO_STYLE_NODE_DEFAULT_MAX_SIZE,
	AUTO_STYLE_EDGE_DEFAULT_PROPERTY,
	AUTO_STYLE_EDGE_DEFAULT_COLUMN_NAME,
	AUTO_STYLE_EDGE_DEFAULT_MIN_WIDTH,
	AUTO_STYLE_EDGE_DEFAULT_MAX_WIDTH,
	TESTS_ON,
	TEST_DIR,
	NODE_CENTRALITY_DEFAULT_WEIGHT_MODE,
	NODE_CENTRALITY_DEFAULT_WEIGHT_COLUMN,
	NODE_CENTRALITY_DEFAULT_DISTANCE_MODE,
	NODE_CENTRALITY_DEFAULT_NORMALIZATION_MODE,
	NODE_CENTRALITY_DEFAULT_NEGATIVE_WEIGHT_MODE,
	NODE_CENTRALITY_DEFAULT_TYPE,
	AUTOCORRELATION_REPLICA_MERGE_TIME_DEFAULT_METHOD,
	EXPORT_NETWORK_MATRIX_DEFAULT_WEIGHT_COLUMN,
	EXPORT_NETWORK_MATRIX_DEFAULT_NODE_NAME_COLUMN,
	WEIGHTED_DEGREE_DEFAULT_METHOD,
	WEIGHTED_DEGREE_DEFAULT_EDGE_WEIGHT_COLUMN,
	WEIGHTED_DEGREE_DEFAULT_NEGATIVE_WEIGHT_MODE,
	WEIGHTED_DEGREE_DEFAULT_NORMALIZATION_MODE,
	TIMELINE_LIST_DEFAULT_WEIGHT_METHOD,
	RANDOM_WALK_DEFAULT_WEIGHT_COLUMN,
	RANDOM_WALK_DEFAULT_WALK_MODE,
	RANDOM_WALK_DEFAULT_RESTART_PROBABILITY,
	RANDOM_WALK_DEFAULT_NUM_RUNS,
	RANDOM_WALK_DEFAULT_MAX_STEPS,
	DIVERGENCE_DEFAULT_WEIGHT_METHOD,
	DIVERGENCE_DEFAULT_METHOD,
	DIVERGENCE_DEFAULT_CONVERGENCE_LIMIT,
	ENTROPY_DEFAULT_WEIGHT_METHOD,
	BLOCKS_DEFAULT,
	BLOCKED_ERROR_DEFAULT_WEIGHT_METHOD;
	;
	
	
	public static String getDefaultValue(String name){
		AppProperty property = AppProperty.valueOf(name);
		return property.getDefaultValue();
	}
	
	public String getDefaultValue(){
		switch(this){
		case AUTOSTART_APP: return "true";
		case DEFAULT_INTERACTION_IMPORTER: return ImportMode.AIF.name();
		case DEFAULT_GROUP_DEFINITION: return GroupInputMode.AMINO_ACID.name();
		case DEFAULT_BACKBONE_NAMES: return "C,O,N,CA";
		case DEFAULT_TIMEFRACTION_CUTOFF_COLUMN: return AppColumns.OCCURRENCE.name();
		case DEFAULT_TIMEFRACTION_CUTOFF: return "0.1";
		case IMPORT_PDB_HBOND_DEFAULT_DISTANCE_CUTOFF: return "3.5";
		case IMPORT_PDB_HBOND_DEFAULT_ANGLE_CUTOFF: return "135";
		case IMPORT_PDB_HBOND_DEFAULT_DONOR_MASK: return "F.*|O.*|N.*";
		case IMPORT_PDB_HBOND_DEFAULT_ACCEPTOR_MASK: return "F.*|O.*|N.*";
		case IMPORT_PDB_HBOND_DEFAULT_INTERACTION_TYPE: return InteractionType.HBOND.toString();
		case IMPORT_PDB_CONTACT_DEFAULT_CUTOFF: return "4.0";
		case IMPORT_PDB_CONTACT_DEFAULT_MASK: return ".*";
		case IMPORT_PDB_CONTACT_IGNORE_BB: return Boolean.TRUE.toString();
		case IMPORT_PDB_CONTACT_IGNORE_INTRA_RESIDUE_CONTACTS: return Boolean.TRUE.toString();
		case IMPORT_PDB_CONTACT_DEFAULT_INTERACTION_TYPE: return InteractionType.CONTACT.toString();
		case IMPORT_AMBER_NATIVECONTACTS_IGNORE_BB: return Boolean.TRUE.toString();
		case IMPORT_AMBER_NATIVECONTACTS_IGNORE_INTRA_RESIDUE_CONTACTS: return Boolean.TRUE.toString();
		case IMPORT_AMBER_NATIVECONTACTS_DEFAULT_INTERACTION_TYPE: return InteractionType.CONTACT.toString();
		case IMPORT_AMBER_HBOND_DEFAULT_INTERACTION_TYPE: return InteractionType.HBOND.toString();
		case IMPORT_AMBER_HBOND_IGNORE_BB: return Boolean.TRUE.toString();
		case IMPORT_DSSP_DEFAULT_INTERACTION_TYPE: return InteractionType.SECSTRUCT.toString();
		case IMPORT_CREATE_VISUAL_STYLE: return Boolean.TRUE.toString();
		case IMPORT_DEFAULT_SIEVE_FRAMES: return "1";
		case IMPORT_DEFAULT_TIMELINE_MIN_AVG: return "0.0";
		case STRUCTURE_VIEWER_DEFAULT: return ViewerType.PYMOL.name();
		case STRUCTURE_VIEWER_DEFAULT_ZOOM: return Boolean.TRUE.toString();
		case STRUCTURE_VIEWER_DEFAULT_SELECTED_COLOR: return Integer.toString(Color.ORANGE.getRGB());
		case STRUCTURE_VIEWER_DEFAULT_INTERACTION_COLOR: return Integer.toString(Color.RED.getRGB());
		case STRUCTURE_VIEWER_MAX_SHOWN_RESIDUES: return "50";
		case STRUCTURE_VIEWER_MAX_SHOWN_INTERACTIONS: return "100";
		case STRUCTURE_VIEWER_DEFAULT_STRUCTURE_TYPE: return StructureType.SINGLE_STRUCTURE.name();
		case STRUCTURE_VIEWER_DEFAULT_COMMAND_PYMOL: return ViewerType.PYMOL.getDefaultBinName();
		case STRUCTURE_VIEWER_DEFAULT_COMMAND_VMD: return ViewerType.VMD.getDefaultBinName();
		case STRUCTURE_VIEWER_DEFAULT_COMMAND_CHIMERA: return ViewerType.CHIMERA.getDefaultBinName();
		case CLUSTER_DEFAULT_SIEVE: return "1";
		case CLUSTER_DEFAULT_WEIGHT_METHOD: return FrameWeightMethod.SUM.name();
		case CLUSTER_DEFAULT_CLUSTER_LIMIT: return ClusterLimitMethod.TARGET_NUMBER.name();
		case CLUSTER_DEFAULT_TARGET_CLUSTERS: return "10";
		case CLUSTER_DEFAULT_EPSILON: return "0.1";
		case CLUSTER_DEFAULT_METHOD: return ClusterMethod.AGGLOMERATIVE.name();
		case CLUSTER_TREE_DEFAULT_METHOD: return TreeClusterMethod.AGGLOMERATIVE.name();
		case CLUSTER_AGGLOMERATIVE_DEFAULT_LINKAGE: return LinkageStrategy.AVERAGE.name();
		case CLUSTER_TREE_DEFAULT_CLUSTER_COUNT_SELECTION_METHOD: return TreeClusteringSelectionMethod.MAX_COMPENSATED_FLUX.name();
		case PATHS_SEARCH_FIXED_LENGTH_DEFAULT_MINLENGTH: return "0";
		case PATHS_SEARCH_FIXED_LENGTH_DEFAULT_MAXLENGTH: return "3";
		case WEIGHT_ERROR_DEFAULT_METHOD: return ErrorMethod.AUTOCORRELATION.name();
		case WEIGHT_ERROR_DEFAULT_WEIGHT_METHOD: return FrameWeightMethod.SUM.name();
		case CORRELATION_FACTORS_DEFAULT_CORRELATION_METHOD: return EdgeCorrelationMethod.PEARSON.name();
		case CORRELATION_FACTORS_DEFAULT_WEIGHT_METHOD: return FrameWeightMethod.SUM.name();
		case REPLICAS_DEFAULT: return "1";
		case TASK_LOG_VIEW_TYPE_DEFAULT: return TaskLogType.IMPORT.name();
		case LOG_TYPE_DEFAULT: return LogType.GLOBAL.name();
		case AUTO_STYLE_NODE_DEFAULT_PROPERTY: return NodeAutoStyle.COLOR.name();
		case AUTO_STYLE_NODE_DEFAULT_COLUMN_NAME: return null;
		case AUTO_STYLE_NODE_DEFAULT_LOW_COLOR: return Integer.toString(Color.GREEN.getRGB());
		case AUTO_STYLE_NODE_DEFAULT_MID_COLOR: return Integer.toString(Color.YELLOW.getRGB());
		case AUTO_STYLE_NODE_DEFAULT_HIGH_COLOR: return Integer.toString(Color.RED.getRGB());
		case AUTO_STYLE_NODE_DEFAULT_MIN_SIZE: return "10";
		case AUTO_STYLE_NODE_DEFAULT_MAX_SIZE: return "70";
		case AUTO_STYLE_EDGE_DEFAULT_PROPERTY: return EdgeAutoStyle.COLOR.name();
		case AUTO_STYLE_EDGE_DEFAULT_COLUMN_NAME: return AppColumns.WEIGHT.name();
		case AUTO_STYLE_EDGE_DEFAULT_MIN_WIDTH: return "5";
		case AUTO_STYLE_EDGE_DEFAULT_MAX_WIDTH: return "20";
		case TEST_DIR: return "";
		case TESTS_ON: return "false";
		case NODE_CENTRALITY_DEFAULT_WEIGHT_MODE: return WeightAccumulationMode.SUM.name();
		case NODE_CENTRALITY_DEFAULT_WEIGHT_COLUMN: return AppColumns.WEIGHT.toString();
		case NODE_CENTRALITY_DEFAULT_DISTANCE_MODE: return EdgeDistanceMode.INVERSE.name();
		case NODE_CENTRALITY_DEFAULT_NORMALIZATION_MODE: return CentralityNormalizationMode.MIN_MAX.name();
		case NODE_CENTRALITY_DEFAULT_NEGATIVE_WEIGHT_MODE: return NegativeValuesMode.ABSOLUTE_VALUE.name();
		case NODE_CENTRALITY_DEFAULT_TYPE: return NodeCentralityType.BETWEENNESS.name();
		case AUTOCORRELATION_REPLICA_MERGE_TIME_DEFAULT_METHOD: return ReplicaAutocorrelationTimeWeightMethod.MAX.name();
		case EXPORT_NETWORK_MATRIX_DEFAULT_NODE_NAME_COLUMN: return DefaultColumns.SHARED_NAME.toString();
		case EXPORT_NETWORK_MATRIX_DEFAULT_WEIGHT_COLUMN: return AppColumns.WEIGHT.toString();
		case WEIGHTED_DEGREE_DEFAULT_METHOD: return WeightedDegreeMode.ADJACENT_EDGE_WEIGHT_SUM.name();
		case WEIGHTED_DEGREE_DEFAULT_EDGE_WEIGHT_COLUMN: return AppColumns.WEIGHT.toString();
		case WEIGHTED_DEGREE_DEFAULT_NEGATIVE_WEIGHT_MODE: return NegativeValuesMode.ABSOLUTE_VALUE.name();
		case WEIGHTED_DEGREE_DEFAULT_NORMALIZATION_MODE: return WeightedDegreeNormalizationMode.NONE.name();
		case DIFFERENCE_CORRELATION_FACTORS_DEFAULT_REF_NETWORK_NAME: return "";
		case DIFFERENCE_CORRELATION_FACTORS_DEFAULT_EDGE_MAPPING_METHOD: return EdgeMappingMethod.NAME.name();
		case CORRELATION_FACTORS_DEFAULT_ANALYSIS_TYPE: return CorrelationFactorsAnalysisType.NEIGHBOUR.name();
		case TIMELINE_LIST_DEFAULT_WEIGHT_METHOD: return FrameWeightMethod.SUM.name();
		case RANDOM_WALK_DEFAULT_MAX_STEPS: return "1000";
		case RANDOM_WALK_DEFAULT_NUM_RUNS: return "1000";
		case RANDOM_WALK_DEFAULT_RESTART_PROBABILITY: return "0.01";
		case RANDOM_WALK_DEFAULT_WALK_MODE: return RandomWalkMode.DEFAULT.name();
		case RANDOM_WALK_DEFAULT_WEIGHT_COLUMN: return AppColumns.DEGREE.toString();
		case DIVERGENCE_DEFAULT_WEIGHT_METHOD: return FrameWeightMethod.SUM.name();
		case DIVERGENCE_DEFAULT_METHOD: return DivergenceMethod.POPULATION_SHIFT.name();
		case DIVERGENCE_DEFAULT_CONVERGENCE_LIMIT: return "0.1";
		case ENTROPY_DEFAULT_WEIGHT_METHOD: return FrameWeightMethod.SUM.name();
		case BLOCKS_DEFAULT: return "1";
		case BLOCKED_ERROR_DEFAULT_WEIGHT_METHOD: return FrameWeightMethod.SUM.name();
		
		default: throw new IllegalArgumentException("Could not find default value for: " + this.name());
		}
	}
}
