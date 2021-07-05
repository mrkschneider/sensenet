package com.tcb.sensenet.internal.meta.network.checks;

import com.tcb.sensenet.internal.meta.network.MetaNetwork;
import com.tcb.aifgen.importer.TimelineType;

public class MetaNetworkChecks {

	public static void disallowDifferenceNetworks(MetaNetwork metaNetwork){
		if(metaNetwork.getTimelineType().equals(TimelineType.DIFFERENCE_TIMELINE))
			throw new RuntimeException("Not available for difference networks");
	}
}
