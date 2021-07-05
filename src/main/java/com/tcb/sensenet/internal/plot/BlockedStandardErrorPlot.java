package com.tcb.sensenet.internal.plot;

import java.util.List;

import org.cytoscape.model.CyEdge;

import com.google.common.collect.ImmutableList;
import com.tcb.sensenet.internal.aggregation.methods.timeline.FrameWeightMethod;
import com.tcb.sensenet.internal.app.AppColumns;
import com.tcb.sensenet.internal.app.AppGlobals;
import com.tcb.sensenet.internal.meta.network.MetaNetwork;
import com.tcb.sensenet.internal.meta.network.checks.MetaNetworkChecks;
import com.tcb.sensenet.internal.meta.timeline.MetaTimeline;
import com.tcb.sensenet.internal.meta.timeline.TimelineManager;
import com.tcb.sensenet.internal.meta.timeline.factories.MetaTimelineFactoryManager;
import com.tcb.sensenet.internal.meta.timeline.factories.NetworkMetaTimelineFactory;
import com.tcb.sensenet.internal.util.RangeUtil;
import com.tcb.cytoscape.cyLib.errors.InvalidColumnException;
import com.tcb.aifgen.importer.TimelineType;
import com.tcb.mdAnalysis.statistics.blockAverage.BlockAverage;


public class BlockedStandardErrorPlot extends MetaEdgePlot {
	
	private AppGlobals appGlobals;
	private Integer blocks;

	public BlockedStandardErrorPlot(CyEdge metaEdge, MetaNetwork metaNetwork,
			FrameWeightMethod weightMethod,
			Integer blocks,
			AppGlobals appGlobals){
		super(metaEdge,metaNetwork,weightMethod);
		super.init();
		this.appGlobals = appGlobals;
		this.blocks = blocks;
	}

	@Override
	public String getPlotTitle() {
		return "Blocked standard errors";
	}
	
	@Override
	public void plot() throws InvalidColumnException {
		MetaNetworkChecks.disallowDifferenceNetworks(metaNetwork);
		TimelineType timelineType = metaNetwork.getTimelineType();
		NetworkMetaTimelineFactory metaTimelineFactory = 
				new MetaTimelineFactoryManager(appGlobals.state.timelineManager).getFactory(weightMethod, timelineType);
		MetaTimeline metaTimeline = metaTimelineFactory
				.create(metaEdge, metaNetwork);

		final int maxSize = metaTimeline.getLength() / (4 * blocks); 
		List<Double> blockedStandardErrors = new BlockAverage(metaTimeline.asDoubles())
				.getBlockedStandardErrors()
				.stream()
				.limit(maxSize)
				.collect(ImmutableList.toImmutableList());
		List<Double> X = RangeUtil.doubleRange(0, blockedStandardErrors.size());
		plotValues(X,blockedStandardErrors,
				colors[0],
				getDefaultStroke(),
				"Blocked standard errors");
	}

	@Override
	public String getXLabel() {
		return "Block size";
	}

	@Override
	public String getYLabel() {
		return "Standard error";
	}
}
