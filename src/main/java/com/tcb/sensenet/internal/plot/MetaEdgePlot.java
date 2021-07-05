package com.tcb.sensenet.internal.plot;

import org.cytoscape.model.CyEdge;

import com.tcb.sensenet.internal.aggregation.methods.timeline.FrameWeightMethod;
import com.tcb.sensenet.internal.app.AppColumns;
import com.tcb.sensenet.internal.meta.network.MetaNetwork;
import com.tcb.cytoscape.cyLib.cytoApiWrappers.CyRowAdapter;
import com.tcb.cytoscape.cyLib.data.DefaultColumns;

public abstract class MetaEdgePlot extends LinePlot {
	protected CyEdge metaEdge;
	protected FrameWeightMethod weightMethod;
	protected MetaNetwork metaNetwork;

	public MetaEdgePlot(CyEdge metaEdge, MetaNetwork metaNetwork, 
			FrameWeightMethod weightMethod){
		this.metaEdge = metaEdge;
		this.metaNetwork = metaNetwork;
		this.weightMethod = weightMethod;
	}
	
	@Override
	public String getPlotSubTitle() {
		return createPlotSubTitle(metaEdge,metaNetwork,weightMethod);
	}
	
	public static String createPlotSubTitle(
			CyEdge metaEdge, MetaNetwork metaNetwork, FrameWeightMethod weightMethod){
		CyRowAdapter row = metaNetwork.getRow(metaEdge);
		String edgeName = row.getMaybe(AppColumns.LABEL, String.class)
				.orElseGet(() -> row.get(DefaultColumns.SHARED_NAME, String.class));
		return String.format("%s (%s)",
				edgeName,
				weightMethod.name());
	}
}
