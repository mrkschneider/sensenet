package com.tcb.sensenet.internal.structureViewer;

import com.tcb.netmap.structureViewer.StructureViewer;

public class StructureViewerManager{
	private StructureViewer viewer;
	private StructureModelManager models;
	private ViewerType viewerType;
	
	public StructureViewerManager(){
		viewer = null;
		viewerType = null;
		models = new StructureModelManager();
	}
	
	public boolean hasViewer(){
		return viewer!=null;
	}
	
	public StructureViewer getViewer(){
		if(!hasViewer()) throw new RuntimeException("Structure viewer is null");
		return viewer;
	}
	
	public ViewerType getViewerType(){
		if(!hasViewer()) throw new RuntimeException("Structure viewer is null");
		return viewerType;
	}
	
	public boolean hasActiveViewer(){
		return hasViewer() && viewer.isActive();
	}
	
	public synchronized void setViewer(StructureViewer newViewer, ViewerType type){
		if(hasViewer()) viewer.stopConnection();
		viewer = newViewer;
		viewerType = type;
	}
	
	public StructureModelManager getModels(){
		return models;
	}
	
}
