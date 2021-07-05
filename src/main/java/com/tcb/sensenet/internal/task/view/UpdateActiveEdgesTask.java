package com.tcb.sensenet.internal.task.view;

import org.cytoscape.work.AbstractTask;
import org.cytoscape.work.TaskMonitor;

import com.tcb.sensenet.internal.app.AppGlobals;
import com.tcb.sensenet.internal.meta.network.MetaNetwork;
import com.tcb.sensenet.internal.view.ActiveEdgesUpdater;

public class UpdateActiveEdgesTask extends AbstractTask {

	private AppGlobals appGlobals;
	
	public UpdateActiveEdgesTask(AppGlobals appGlobals){
		this.appGlobals = appGlobals;
	}
	@Override
	public void run(TaskMonitor tskMon) throws Exception {
		MetaNetwork metaNetwork = appGlobals.state.metaNetworkManager.getCurrentMetaNetwork();
		Runnable updater = new ActiveEdgesUpdater(metaNetwork);
		updater.run();
	}
	

}
