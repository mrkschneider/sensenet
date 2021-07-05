package com.tcb.sensenet.internal.events;

import com.tcb.sensenet.internal.meta.network.MetaNetwork;

public class FrameSetRecord {

	private Integer frame;
	private MetaNetwork metaNetwork;

	public FrameSetRecord(MetaNetwork metaNetwork, Integer frame){
		this.metaNetwork = metaNetwork;
		this.frame = frame;
	}
	
	public MetaNetwork getMetaNetwork(){
		return metaNetwork;
	}
	
	public Integer getFrame(){
		return frame;
	}
}
