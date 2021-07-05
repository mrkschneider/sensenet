package com.tcb.sensenet.internal.path.weight;

import com.tcb.sensenet.internal.meta.network.MetaNetwork;
import com.tcb.sensenet.internal.meta.timeline.TimelineManager;
import com.tcb.sensenet.internal.meta.timeline.factories.MetaOccurenceTimelineFactory;
import com.tcb.sensenet.internal.meta.timeline.factories.NetworkMetaTimelineFactory;
import com.tcb.sensenet.internal.meta.timeline.factories.NetworkMetaTimelineFactoryImpl;
import com.tcb.sensenet.internal.path.Path;
import com.tcb.cytoscape.cyLib.cytoApiWrappers.CyNetworkAdapter;

public class OccurencePathWeighter extends AbstractPathWeighter {

	private TimelineManager timelineManager;

	public OccurencePathWeighter(MetaNetwork metaNetwork, CyNetworkAdapter network, TimelineManager timelineManager) {
		super(metaNetwork, network);
		this.timelineManager = timelineManager;
	}

	@Override
	protected NetworkMetaTimelineFactory getMetaTimelineFactory() {
		return new NetworkMetaTimelineFactoryImpl(new MetaOccurenceTimelineFactory(),timelineManager);
	}

	@Override
	protected Double normalizeWeight(Double weight, Path path) {
		return ((Double)weight) / path.getLength();
	}
	
}
