package com.tcb.sensenet.internal.analysis.correlation;

import com.tcb.sensenet.internal.analysis.correlation.mutualInformation.MutualInformation;
import com.tcb.sensenet.internal.meta.timeline.MetaTimeline;

public class MutualInformationCorrelationStrategy implements TimelineCorrelationStrategy {

	@Override
	public Double getCorrelation(MetaTimeline a, MetaTimeline b) {
		return new MutualInformation().calculate(a, b);
	}
	
	

}
