package com.tcb.sensenet.internal.task.structureViewer;

import org.cytoscape.work.AbstractTask;
import org.cytoscape.work.TaskMonitor;

import com.tcb.sensenet.internal.app.AppGlobals;
import com.tcb.sensenet.internal.meta.network.MetaNetwork;
import com.tcb.sensenet.internal.structureViewer.StructureModel;

public class UnpauseNetworkLinkTask extends AbstractTask {

	private AppGlobals appGlobals;

	public UnpauseNetworkLinkTask(
			AppGlobals appGlobals){
		this.appGlobals = appGlobals;
	}
	
	@Override
	public void run(TaskMonitor taskMon) throws Exception {
		MetaNetwork metaNetwork = appGlobals.state.metaNetworkManager.getCurrentMetaNetwork();
		StructureModel model = appGlobals.structureViewerManager.getModels().get(metaNetwork);
		model.setPaused(false);
	}
		

}
