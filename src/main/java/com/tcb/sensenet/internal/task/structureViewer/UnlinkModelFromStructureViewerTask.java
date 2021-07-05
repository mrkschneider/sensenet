package com.tcb.sensenet.internal.task.structureViewer;

import java.io.IOException;

import org.cytoscape.work.AbstractTask;
import org.cytoscape.work.TaskMonitor;
import org.cytoscape.work.Tunable;

import com.tcb.sensenet.internal.app.AppGlobals;
import com.tcb.sensenet.internal.meta.network.MetaNetwork;
import com.tcb.sensenet.internal.structureViewer.StructureModel;
import com.tcb.sensenet.internal.structureViewer.StructureModelManager;
import com.tcb.netmap.structureViewer.StructureViewer;

public class UnlinkModelFromStructureViewerTask extends AbstractTask {

	@Tunable(description="Remove from viewer")
	public Boolean deleteModel = false;
	
	private AppGlobals appGlobals;
	
	public UnlinkModelFromStructureViewerTask(
			AppGlobals appGlobals){
		this.appGlobals = appGlobals;
	}
	
	@Override
	public void run(TaskMonitor taskMon) throws Exception {
		MetaNetwork metaNetwork = appGlobals.state.metaNetworkManager.getCurrentMetaNetwork();
				
		StructureModelManager models = appGlobals.structureViewerManager.getModels();
				
		if(!models.containsKey(metaNetwork)) return;
		StructureModel model = models.get(metaNetwork);
		models.remove(metaNetwork);
		
		if(deleteModel) deleteModelFromViewer(model);
				
	}
	
	private void deleteModelFromViewer(StructureModel model){
		if(!appGlobals.structureViewerManager.hasActiveViewer()) return;
		
		StructureViewer viewer = appGlobals.structureViewerManager.getViewer();
		try {
			model.deleteModel(viewer);
		} catch(IOException e) {};
	}
	
		

}
