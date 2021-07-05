package com.tcb.sensenet.internal.task.structureViewer.config;

import java.io.IOException;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Set;

import com.tcb.netmap.structureViewer.StructureViewer;

public class SingleStructureLoader implements StructureLoader {

	private Path pdbPath;
	private String format;

	public SingleStructureLoader(Path pdbPath, String format){
		this.pdbPath = pdbPath;
		this.format = format;
	}
	
	@Override
	public void loadModel(StructureViewer viewer, String modelName) throws IOException {
		Set<String> loadedModels = new HashSet<>(viewer.getModels());
		if(loadedModels.contains(modelName)) 
			throw new IllegalArgumentException("Duplicate model names not allowed: " + modelName);
		viewer.loadModel(pdbPath.toString(), format, modelName);
	}

	@Override
	public void showModel(StructureViewer viewer, String modelName) throws IOException {
		viewer.showModel(modelName);		
	}

}
