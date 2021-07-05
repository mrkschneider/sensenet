package com.tcb.sensenet.internal.aggregation.aggregators.edges.timeline;

import com.tcb.sensenet.internal.analysis.autocorrelation.replicas.ReplicaAutocorrelationErrorAnalysis;
import com.tcb.sensenet.internal.analysis.autocorrelation.replicas.strategy.AutocorrelationTimeWeightStrategy;
import com.tcb.sensenet.internal.analysis.correlation.IntProbabilities;
import com.tcb.sensenet.internal.analysis.correlation.Probabilities;
import com.tcb.sensenet.internal.analysis.entropy.ShannonEntropy;
import com.tcb.sensenet.internal.meta.timeline.MetaTimeline;
import com.tcb.sensenet.internal.util.ObjMap;

public class EntropyAggregator implements MetaTimelineAggregator {
	
	public EntropyAggregator(){
		
	}

	@Override
	public ObjMap aggregate(MetaTimeline metaTimeline) {
		Probabilities<Integer> prob = IntProbabilities.create(
				metaTimeline);
		Double entropy = new ShannonEntropy().calculate(prob);
		ObjMap result = new ObjMap();
		result.put("entropy", entropy);
		return result;
	}
	
	
	

		
}
