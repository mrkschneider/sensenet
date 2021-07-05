package com.tcb.sensenet.internal.task.correlation;

import java.util.Optional;

import com.google.auto.value.AutoValue;
import com.tcb.sensenet.internal.aggregation.aggregators.table.RowWriter;
import com.tcb.sensenet.internal.aggregation.methods.timeline.FrameWeightMethod;
import com.tcb.sensenet.internal.analysis.correlation.EdgeCorrelationMethod;
import com.tcb.sensenet.internal.log.TaskLogType;
import com.tcb.sensenet.internal.map.edge.EdgeMappingMethod;
import com.tcb.sensenet.internal.meta.network.MetaNetwork;
import com.tcb.cytoscape.cyLib.cytoApiWrappers.CyNetworkAdapter;
import com.tcb.cytoscape.cyLib.log.ParameterReporter;

@AutoValue
public abstract class CorrelationFactorsTaskConfig implements ParameterReporter {
	
	public static CorrelationFactorsTaskConfig create(
			EdgeCorrelationMethod correlationMethod,
			FrameWeightMethod weightMethod,
			Optional<MetaNetwork> referenceMetaNetwork,
			Optional<EdgeMappingMethod> edgeMappingMethod,
			RowWriter edgeTableWriter){
		return new AutoValue_CorrelationFactorsTaskConfig(
				correlationMethod,weightMethod,referenceMetaNetwork,edgeMappingMethod,edgeTableWriter);
	}

	public abstract EdgeCorrelationMethod getCorrelationMethod();
	public abstract FrameWeightMethod getWeightMethod();
	public abstract Optional<MetaNetwork> getReferenceMetaNetwork();
	public abstract Optional<EdgeMappingMethod> getEdgeMappingMethod();
	public abstract RowWriter getEdgeTableWriter();

	
	public TaskLogType getTaskLogType(){
		return TaskLogType.CORRELATION_FACTORS;
	}
	
}
