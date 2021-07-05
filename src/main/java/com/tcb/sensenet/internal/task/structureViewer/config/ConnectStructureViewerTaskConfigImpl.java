package com.tcb.sensenet.internal.task.structureViewer.config;

import com.tcb.sensenet.internal.structureViewer.ViewerType;
import com.tcb.netmap.structureViewer.ViewerFactory;

public class ConnectStructureViewerTaskConfigImpl implements ConnectStructureViewerTaskConfig {
	private ViewerFactory viewerFactory;
	private ViewerType viewerType;
	
	public ConnectStructureViewerTaskConfigImpl(ViewerFactory viewerFactory, ViewerType viewerType){
		this.viewerFactory = viewerFactory;
		this.viewerType = viewerType;
	}

	@Override
	public ViewerFactory getViewerFactory() {
		return viewerFactory;
	}

	@Override
	public ViewerType getViewerType() {
		return viewerType;
	}
}
