package com.tcb.sensenet.internal.task.structureViewer.config;

public class AddModelToStructureViewerTaskConfigImpl implements AddModelToStructureViewerTaskConfig {
	private StructureLoader structLoader;
	private String modelName;
	
	public AddModelToStructureViewerTaskConfigImpl(StructureLoader structLoader, String modelName){
		this.structLoader = structLoader;
		this.modelName = modelName;
	}
	
	@Override
	public StructureLoader getModelLoader() {
		return structLoader;
	}

	@Override
	public String getModelName() {
		return modelName;
	}
}
