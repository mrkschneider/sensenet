package com.tcb.sensenet.internal.task.divergence;

import org.cytoscape.model.CyEdge;

import com.google.auto.value.AutoValue;
import com.tcb.sensenet.internal.aggregation.methods.timeline.FrameWeightMethod;
import com.tcb.sensenet.internal.analysis.divergence.DivergenceMethod;
import com.tcb.sensenet.internal.selection.SingleEdgeSelection;
import com.tcb.cytoscape.cyLib.log.ParameterReporter;

@AutoValue
public abstract class EdgeDivergenceTaskConfig implements ParameterReporter {

	public abstract SingleEdgeSelection getEdgeSelection();
	public abstract FrameWeightMethod getWeightMethod();
	public abstract DivergenceMethod getDivergenceMethod();
	public abstract Integer getReplicaCount();
	public abstract Double getConvergenceLimit();
	
	public static EdgeDivergenceTaskConfig create(
			SingleEdgeSelection edge, FrameWeightMethod weightMethod,
			DivergenceMethod divergenceMethod, Integer replicaCount,
			Double convergenceLimit) {
		return new AutoValue_EdgeDivergenceTaskConfig(edge,weightMethod,divergenceMethod,
				replicaCount,convergenceLimit);
	}
}
