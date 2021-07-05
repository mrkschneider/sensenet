package com.tcb.sensenet.internal.structureViewer.events;

import org.cytoscape.event.CyEvent;

import com.tcb.netmap.structureViewer.StructureViewer;

public class StructureViewerClosedEvent implements CyEvent<StructureViewer>{

	private StructureViewer viewer;

	public StructureViewerClosedEvent(StructureViewer viewer){
		this.viewer = viewer;
	}
	
	@Override
	public Class<?> getListenerClass() {
		return StructureViewerClosedListener.class;
	}

	@Override
	public StructureViewer getSource() {
		return viewer;
	}

}
