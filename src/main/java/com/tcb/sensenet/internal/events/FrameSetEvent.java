package com.tcb.sensenet.internal.events;

import org.cytoscape.event.CyEvent;

public class FrameSetEvent implements CyEvent<FrameSetRecord> {

	private FrameSetRecord record;

	public FrameSetEvent(FrameSetRecord record){
		this.record = record;
	}
	
	@Override
	public Class<?> getListenerClass() {
		return FrameSetListener.class;
	}

	@Override
	public FrameSetRecord getSource() {
		return record;
	}

}
