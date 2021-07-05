package com.tcb.sensenet.internal.analysis.correlation.difference;

import com.tcb.sensenet.internal.analysis.correlation.mutualInformation.pointwise.PointwiseMutualInformationStrategy;
import com.tcb.sensenet.internal.meta.timeline.MetaTimeline;

public class DifferenceInformationCorrelationStrategy implements DifferenceTimelineCorrelationStrategy {

	private PointwiseMutualInformationStrategy pmi;

	public DifferenceInformationCorrelationStrategy(
			PointwiseMutualInformationStrategy pmi){
		this.pmi = pmi;
	}
	
	@Override
	public Double getCorrelation(MetaTimeline a1, MetaTimeline b1, MetaTimeline a2, MetaTimeline b2) {
		DifferenceInformation info = new DifferenceInformation(pmi);
		Double result = info.calculate(a1, b1, a2, b2);
		return result;
	}

}
