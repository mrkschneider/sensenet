package com.tcb.sensenet.internal.task.view;

import org.cytoscape.event.CyEventHelper;
import org.cytoscape.work.AbstractTask;
import org.cytoscape.work.TaskMonitor;

import com.tcb.cytoscape.cyLib.cytoApiWrappers.CyApplicationManagerAdapter;
import com.tcb.cytoscape.cyLib.cytoApiWrappers.CyNetworkViewAdapter;

public class UpdateViewTask extends AbstractTask {

	private CyEventHelper eventHelper;
	private CyApplicationManagerAdapter applicationManager;
	
	public UpdateViewTask(CyApplicationManagerAdapter applicationManager, CyEventHelper eventHelper){
		this.applicationManager = applicationManager;
		this.eventHelper = eventHelper;
	}
	@Override
	public void run(TaskMonitor tskMon) throws Exception {
		if(!applicationManager.hasCurrentNetworkView()) return;
		CyNetworkViewAdapter view = applicationManager.getCurrentNetworkView();
		eventHelper.flushPayloadEvents();
		view.updateView();
	}
	

}
