package com.tcb.sensenet.internal.task.degree;

import java.util.Optional;

import com.google.auto.value.AutoValue;
import com.tcb.sensenet.internal.aggregation.aggregators.table.RowWriter;
import com.tcb.sensenet.internal.analysis.degree.WeightedDegreeNormalizationMode;
import com.tcb.sensenet.internal.analysis.degree.WeightedDegreeMode;
import com.tcb.sensenet.internal.log.TaskLogType;
import com.tcb.sensenet.internal.path.analysis.centrality.NodeCentralityType;
import com.tcb.sensenet.internal.path.analysis.centrality.distance.EdgeDistanceMode;
import com.tcb.sensenet.internal.path.analysis.centrality.normalization.CentralityNormalizationMode;
import com.tcb.sensenet.internal.path.analysis.centrality.weight.accumulation.WeightAccumulationMode;
import com.tcb.sensenet.internal.path.analysis.centrality.weight.negative.NegativeValuesMode;
import com.tcb.sensenet.internal.path.analysis.centrality.weight.negative.NegativeValuesStrategy;
import com.tcb.sensenet.internal.util.Nullable;
import com.tcb.cytoscape.cyLib.cytoApiWrappers.CyNetworkAdapter;
import com.tcb.cytoscape.cyLib.log.ParameterReporter;

@AutoValue
public abstract class WeightedDegreeTaskConfig implements ParameterReporter {
		
	public static WeightedDegreeTaskConfig create(
			CyNetworkAdapter network,
			String weightColumnName,
			RowWriter rowWriter,
			WeightedDegreeMode weightedDegreeMode,
			NegativeValuesMode negativeWeightMode,
			WeightedDegreeNormalizationMode normalizationMode
			){
		return new AutoValue_WeightedDegreeTaskConfig(
				network,weightColumnName,rowWriter,weightedDegreeMode,negativeWeightMode,normalizationMode
				);
	}
	
	public abstract CyNetworkAdapter getNetwork();
	public abstract String getWeightColumnName();
	public abstract RowWriter getRowWriter();
	public abstract WeightedDegreeMode getWeightedDegreeMode();
	public abstract NegativeValuesMode getNegativeWeightMode();
	public abstract WeightedDegreeNormalizationMode getNormalizationMode();
	
	public TaskLogType getTaskLogType(){
		return TaskLogType.DEGREE;
	}
}
