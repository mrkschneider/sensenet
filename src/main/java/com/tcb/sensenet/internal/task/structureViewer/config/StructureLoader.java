package com.tcb.sensenet.internal.task.structureViewer.config;

import java.io.IOException;

import com.tcb.netmap.structureViewer.StructureViewer;

public interface StructureLoader {
	public void loadModel(StructureViewer viewer, String modelName) throws IOException;
	public void showModel(StructureViewer viewer, String modelName) throws IOException;
}
