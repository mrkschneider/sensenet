package com.tcb.sensenet.internal.task.path.centrality;

import java.util.Optional;


import com.google.auto.value.AutoValue;
import com.tcb.sensenet.internal.aggregation.aggregators.table.RowWriter;
import com.tcb.sensenet.internal.log.TaskLogType;
import com.tcb.sensenet.internal.path.analysis.centrality.NodeCentralityType;
import com.tcb.sensenet.internal.path.analysis.centrality.distance.EdgeDistanceMode;
import com.tcb.sensenet.internal.path.analysis.centrality.normalization.CentralityNormalizationMode;
import com.tcb.sensenet.internal.path.analysis.centrality.weight.accumulation.WeightAccumulationMode;
import com.tcb.sensenet.internal.path.analysis.centrality.weight.negative.NegativeValuesMode;
import com.tcb.sensenet.internal.util.Nullable;
import com.tcb.cytoscape.cyLib.cytoApiWrappers.CyNetworkAdapter;
import com.tcb.cytoscape.cyLib.log.ParameterReporter;

@AutoValue
public abstract class WeightedNodeCentralityTaskConfig implements ParameterReporter {
		
	public static WeightedNodeCentralityTaskConfig create(
			CyNetworkAdapter network,
			RowWriter tableWriter,
			Optional<String> weightColumnName,
			NodeCentralityType centralityType,
			WeightAccumulationMode nodeWeightMode,
			EdgeDistanceMode distanceMode,
			CentralityNormalizationMode normalizationMode,
			NegativeValuesMode negativeWeightMode){
		return new AutoValue_WeightedNodeCentralityTaskConfig(
				network,tableWriter,
				weightColumnName,centralityType,
				nodeWeightMode,
				distanceMode, normalizationMode, negativeWeightMode);
	}
	
	public abstract CyNetworkAdapter getNetwork();
	public abstract RowWriter getRowWriter();
	public abstract Optional<String> getWeightColumnName();
	public abstract NodeCentralityType getCentralityType();
	public abstract WeightAccumulationMode getMultiEdgeWeightMode();
	public abstract EdgeDistanceMode getDistanceMode();
	public abstract CentralityNormalizationMode getNormalizationMode();
	public abstract NegativeValuesMode getNegativeWeightMode();
	
	public TaskLogType getTaskLogType(){
		return TaskLogType.NODE_CENTRALITY;
	}
}
