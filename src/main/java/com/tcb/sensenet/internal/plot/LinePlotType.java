package com.tcb.sensenet.internal.plot;

import org.cytoscape.model.CyEdge;

import com.tcb.sensenet.internal.aggregation.methods.timeline.FrameWeightMethod;
import com.tcb.sensenet.internal.app.AppGlobals;
import com.tcb.sensenet.internal.meta.network.MetaNetwork;
import com.tcb.sensenet.internal.meta.timeline.TimelineManager;

public enum LinePlotType {
	BLOCKED_STANDARD_ERROR,AUTOCORRELATION,TIMELINE;
	
	public LinePlot createPlot(CyEdge metaEdge, MetaNetwork metaNetwork, 
			FrameWeightMethod method,
			Integer blocks,
			AppGlobals appGlobals){
		switch(this){
			case BLOCKED_STANDARD_ERROR: return new BlockedStandardErrorPlot(metaEdge,metaNetwork,
					method, blocks, appGlobals);
			case AUTOCORRELATION: return new AutocorrelationPlot(metaEdge,metaNetwork,
					method, blocks, appGlobals);
			case TIMELINE: return new TimelinePlot(metaEdge,metaNetwork,method,appGlobals);
			default: throw new IllegalArgumentException("Unknown plot type");
		}
	}
}
