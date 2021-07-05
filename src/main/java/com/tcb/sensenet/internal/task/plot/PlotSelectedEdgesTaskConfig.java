package com.tcb.sensenet.internal.task.plot;

import java.util.List;

import org.cytoscape.model.CyEdge;

import com.google.auto.value.AutoValue;
import com.tcb.sensenet.internal.aggregation.methods.timeline.FrameWeightMethod;
import com.tcb.sensenet.internal.plot.LinePlotType;
import com.tcb.cytoscape.cyLib.log.ParameterReporter;

@AutoValue
public abstract class PlotSelectedEdgesTaskConfig implements ParameterReporter {
	
	public abstract List<CyEdge> getEdgeSelection();
	public abstract Integer getBlocks();
	public abstract LinePlotType getPlotType();
	public abstract FrameWeightMethod getWeightMethod();
	
	public static PlotSelectedEdgesTaskConfig create(
			List<CyEdge> edgeSelection, Integer blocks, LinePlotType plotType,
			FrameWeightMethod weightMethod) {
		return new AutoValue_PlotSelectedEdgesTaskConfig(edgeSelection,blocks,plotType,weightMethod);
	}
}
