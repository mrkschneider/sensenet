package com.tcb.sensenet.internal.meta.timeline.factories;

import com.tcb.sensenet.internal.aggregation.methods.timeline.FrameWeightMethod;
import com.tcb.sensenet.internal.meta.timeline.TimelineManager;
import com.tcb.aifgen.importer.TimelineType;


public class MetaTimelineFactoryManager {
	
	protected NetworkMetaTimelineFactory metaOccurenceTimelineFactory;
	protected NetworkMetaTimelineFactory metaSumTimelineFactory;
	protected NetworkMetaTimelineFactory metaOccurenceDifferenceTimelineFactory;
	protected NetworkMetaTimelineFactory metaSumDifferenceTimelineFactory;
	
	protected TimelineManager timelineManager;

	public MetaTimelineFactoryManager(TimelineManager timelineManager){
		this.timelineManager = timelineManager;
		reset();
	}
	
	public void reset(){
		this.metaOccurenceTimelineFactory = createFactory(new MetaOccurenceTimelineFactory());
		this.metaSumTimelineFactory = createFactory(new MetaSumTimelineFactory());
		this.metaOccurenceDifferenceTimelineFactory = createFactory(new MetaOccurenceDifferenceTimelineFactory());
		this.metaSumDifferenceTimelineFactory = createFactory(new MetaSumDifferenceTimelineFactory());
	}
	
	protected NetworkMetaTimelineFactory createFactory(MetaTimelineFactory fac){
		return new NetworkMetaTimelineFactoryImpl(fac, timelineManager);
	}
	
	public NetworkMetaTimelineFactory getFactory(FrameWeightMethod weightMethod, TimelineType timelineType){
		switch(timelineType){
		case TIMELINE: return getNormalFactory(weightMethod);
		case DIFFERENCE_TIMELINE: return getDifferenceFactory(weightMethod);
		default: throw new UnsupportedOperationException(timelineType.name());
		}
		
	}
	
	private NetworkMetaTimelineFactory getNormalFactory(FrameWeightMethod weightMethod){
		switch(weightMethod){
			case OCCURRENCE: return metaOccurenceTimelineFactory;
			case SUM:       return metaSumTimelineFactory;
			default: throw new UnsupportedOperationException(weightMethod.name());
		}
	}
	
	private NetworkMetaTimelineFactory getDifferenceFactory(FrameWeightMethod weightMethod){
		switch(weightMethod){
			case OCCURRENCE: return metaOccurenceDifferenceTimelineFactory;
			case SUM:       return metaSumDifferenceTimelineFactory;
			default: throw new UnsupportedOperationException();
		}
	}
	
	
}
