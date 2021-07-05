package com.tcb.sensenet.internal.aggregation.methods.timeline;

import com.tcb.sensenet.internal.aggregation.methods.AggregationMethod;

public enum TimelineStatisticsMethod implements AggregationMethod {
	TIMELINE_STATISTICS, TIMELINE_STATISTICS_SUBSET;
		
	
	@Override
	public String toString(){
		switch(this){
		case TIMELINE_STATISTICS: return "Timeline statistics";
		case TIMELINE_STATISTICS_SUBSET: return "Timeline subset statistics";
		default: throw new IllegalArgumentException();
		}
	}

}
