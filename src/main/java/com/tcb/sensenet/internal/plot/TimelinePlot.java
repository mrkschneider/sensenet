package com.tcb.sensenet.internal.plot;

import java.util.List;
import java.util.Map;

import org.cytoscape.model.CyEdge;

import com.tcb.sensenet.internal.aggregation.methods.timeline.FrameWeightMethod;
import com.tcb.sensenet.internal.app.AppGlobals;
import com.tcb.sensenet.internal.meta.network.MetaNetwork;
import com.tcb.sensenet.internal.meta.timeline.TimelineManager;
import com.tcb.sensenet.internal.meta.timeline.factories.MetaTimelineFactoryManager;
import com.tcb.sensenet.internal.meta.timeline.factories.NetworkMetaTimelineFactory;
import com.tcb.sensenet.internal.util.ZipUtil;
import com.tcb.aifgen.importer.TimelineType;



public class TimelinePlot extends AbstractTimelinePlot {
	
	private CyEdge metaEdge;
	private MetaNetwork metaNetwork;
	private FrameWeightMethod weightMethod;
	private AppGlobals appGlobals;

	public TimelinePlot(CyEdge metaEdge, MetaNetwork metaNetwork, 
			FrameWeightMethod weightMethod, AppGlobals appGlobals){
		this.metaEdge = metaEdge;
		this.metaNetwork = metaNetwork;
		this.weightMethod = weightMethod;
		this.appGlobals = appGlobals;
		super.init();
		
		this.chart.removeLegend();
	}
			
	@Override
	public String getPlotTitle() {
		return "Timeline";
	}

	@Override
	public String getXLabel() {
		return "Frame";
	}

	@Override
	public String getYLabel() {
		return "Number of interactions";
	}
	
	protected Map<Integer,Double> createTimeline() {
		TimelineType timelineType = metaNetwork.getTimelineType();
		NetworkMetaTimelineFactory metaTimelineFactory = 
				new MetaTimelineFactoryManager(appGlobals.state.timelineManager).getFactory(weightMethod, timelineType);
		List<Double> timeline = metaTimelineFactory.create(metaEdge, metaNetwork).asDoubles();
		Map<Integer,Double> map = ZipUtil.zipMapIndex(timeline);
		return map;
	}

	@Override
	protected String getLineName() {
		return "Timeline";
	}

	@Override
	public String getPlotSubTitle() {
		return MetaEdgePlot.createPlotSubTitle(metaEdge, metaNetwork, weightMethod);
	}

	
	
	

}
