package com.tcb.sensenet.internal.task.structureViewer.config;



import com.tcb.sensenet.internal.structureViewer.ViewerType;
import com.tcb.netmap.structureViewer.ViewerFactory;

public interface ConnectStructureViewerTaskConfig {
	public ViewerFactory getViewerFactory();
	public ViewerType getViewerType();
}
