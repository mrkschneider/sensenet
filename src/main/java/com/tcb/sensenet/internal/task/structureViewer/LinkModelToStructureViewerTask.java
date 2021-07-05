package com.tcb.sensenet.internal.task.structureViewer;

import org.cytoscape.work.AbstractTask;
import org.cytoscape.work.TaskMonitor;

import com.tcb.sensenet.internal.app.AppGlobals;
import com.tcb.sensenet.internal.meta.network.MetaNetwork;
import com.tcb.sensenet.internal.structureViewer.StructureModel;
import com.tcb.sensenet.internal.structureViewer.StructureViewerManager;
import com.tcb.sensenet.internal.task.structureViewer.config.AddModelToStructureViewerTaskConfig;
import com.tcb.sensenet.internal.task.structureViewer.config.StructureLoader;
import com.tcb.netmap.structureViewer.StructureViewer;

public class LinkModelToStructureViewerTask extends AbstractTask {

	private AppGlobals appGlobals;
	private AddModelToStructureViewerTaskConfig config;

	public LinkModelToStructureViewerTask(
			AddModelToStructureViewerTaskConfig config,
			AppGlobals appGlobals){
		this.config = config;
		this.appGlobals = appGlobals;
	}
	
	@Override
	public void run(TaskMonitor taskMon) throws Exception {
		MetaNetwork metaNetwork = appGlobals.state.metaNetworkManager.getCurrentMetaNetwork();
		StructureViewerManager viewerManager = appGlobals.structureViewerManager;
				
		if(!viewerManager.hasViewer()) throw new RuntimeException("No viewer connected");
		
		StructureViewer viewer = viewerManager.getViewer();
		
		if(!viewer.isActive()) throw new RuntimeException("Viewer is not active");
		
		String modelName = config.getModelName();
		
		StructureLoader structLoader = config.getModelLoader();
		
		structLoader.loadModel(viewer, modelName);
		structLoader.showModel(viewer,modelName);
		
		StructureModel model = new StructureModel(modelName);
		appGlobals.structureViewerManager.getModels().putOrReplace(metaNetwork, model);
	}
	
		

}
