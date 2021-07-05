package com.tcb.sensenet.internal.task.structureViewer.config;

import java.io.IOException;
import java.nio.file.Path;

import com.tcb.netmap.structureViewer.StructureViewer;

public class TrajectoryLoader implements StructureLoader {

	private Path trajPath;
	private StructureLoader singleStructureLoader;
	private String format;

	public TrajectoryLoader(StructureLoader singleStructureLoader, Path trajPath, String format){
		this.trajPath = trajPath;
		this.format = format;
		this.singleStructureLoader = singleStructureLoader;
	}
	
	@Override
	public void loadModel(StructureViewer viewer, String modelName) throws IOException {
		singleStructureLoader.loadModel(viewer, modelName);
		
		viewer.loadTraj(modelName, trajPath.toString(), format);
	}

	@Override
	public void showModel(StructureViewer viewer, String modelName) throws IOException {
		singleStructureLoader.showModel(viewer, modelName);
	}

}
