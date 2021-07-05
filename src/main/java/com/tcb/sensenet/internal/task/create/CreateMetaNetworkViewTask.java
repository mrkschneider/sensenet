package com.tcb.sensenet.internal.task.create;

import org.cytoscape.view.model.CyNetworkView;
import org.cytoscape.work.AbstractTask;
import org.cytoscape.work.TaskMonitor;

import com.tcb.sensenet.internal.app.AppGlobals;
import com.tcb.cytoscape.cyLib.cytoApiWrappers.CyNetworkAdapter;

public class CreateMetaNetworkViewTask extends AbstractTask {
	private AppGlobals appGlobals;
		
	public CreateMetaNetworkViewTask(
			AppGlobals appGlobals){
		this.appGlobals = appGlobals;
	}
	
	
	
	private void createView(TaskMonitor tskMon){
		CyNetworkAdapter network = appGlobals.applicationManager.getCurrentNetwork();
		CyNetworkView view = appGlobals.networkViewFactory.createNetworkView(network);
		appGlobals.networkViewManager.addNetworkView(view);
	}
	
	public void run(TaskMonitor tskMon) throws Exception {
		tskMon.setStatusMessage("Creating network view...");
		createView(tskMon);
		}
	
}
