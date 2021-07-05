package com.tcb.sensenet.internal.task.plot.matrix;

import org.jfree.chart.renderer.PaintScale;

import com.google.auto.value.AutoValue;
import com.tcb.sensenet.internal.analysis.matrix.ContactMatrix;
import com.tcb.sensenet.internal.plot.color.ColorScale;
import com.tcb.cytoscape.cyLib.cytoApiWrappers.CyNetworkAdapter;

@AutoValue
public abstract class ShowNetworkMatrixPlotFrameTaskConfig {

	public abstract String getWeightColumn();
	public abstract String getIndexColumn();
	public abstract ColorScale getColorScale();
	
	public static ShowNetworkMatrixPlotFrameTaskConfig create(
			String weightColumn,
			String indexColumn,
			ColorScale colorScale){
		return new AutoValue_ShowNetworkMatrixPlotFrameTaskConfig(weightColumn,indexColumn,colorScale);
	}
}
