package com.tcb.sensenet.internal.task.view;

import java.util.Collection;

import org.cytoscape.event.CyEventHelper;
import org.cytoscape.view.model.CyNetworkView;
import org.cytoscape.work.AbstractTask;
import org.cytoscape.work.TaskMonitor;

import com.tcb.cytoscape.cyLib.cytoApiWrappers.CyApplicationManagerAdapter;
import com.tcb.cytoscape.cyLib.cytoApiWrappers.CyNetworkAdapter;
import com.tcb.cytoscape.cyLib.cytoApiWrappers.CyNetworkViewManagerAdapter;

public class PauseViewsTask extends AbstractTask {
	
	private CyNetworkViewManagerAdapter viewManager;
	private CyEventHelper eventHelper;
	private CyApplicationManagerAdapter applicationManager;

	public PauseViewsTask(CyApplicationManagerAdapter applicationManager,
			CyNetworkViewManagerAdapter viewManager,
			CyEventHelper eventHelper){
		this.applicationManager = applicationManager;
		this.viewManager = viewManager;
		this.eventHelper = eventHelper;
	}
	

	@Override
	public void run(TaskMonitor taskMonitor) throws Exception {
		taskMonitor.setStatusMessage("Deactivating views...");
		CyNetworkAdapter network = applicationManager.getCurrentNetwork();
		pauseViews(network,viewManager);
	}
	
	public void pauseViews(CyNetworkAdapter network, CyNetworkViewManagerAdapter cyNtViewMgr){
		Collection<CyNetworkView> views = cyNtViewMgr.getNetworkViews(network);
		for(CyNetworkView view:views){
			eventHelper.silenceEventSource(view);
		}
	}

}
