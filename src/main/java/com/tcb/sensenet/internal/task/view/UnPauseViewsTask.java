package com.tcb.sensenet.internal.task.view;

import java.util.Collection;

import org.cytoscape.event.CyEventHelper;
import org.cytoscape.view.model.CyNetworkView;
import org.cytoscape.work.AbstractTask;
import org.cytoscape.work.TaskMonitor;

import com.tcb.cytoscape.cyLib.cytoApiWrappers.CyApplicationManagerAdapter;
import com.tcb.cytoscape.cyLib.cytoApiWrappers.CyNetworkAdapter;
import com.tcb.cytoscape.cyLib.cytoApiWrappers.CyNetworkViewManagerAdapter;

public class UnPauseViewsTask extends AbstractTask {
	
	private CyNetworkViewManagerAdapter viewManager;
	private CyApplicationManagerAdapter applicationManager;
	private CyEventHelper eventHelper;

	public UnPauseViewsTask(CyApplicationManagerAdapter applicationManager,
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
		unPauseViews(network,viewManager);
	}
	
	public void unPauseViews(CyNetworkAdapter network, CyNetworkViewManagerAdapter cyNtViewMgr){
		Collection<CyNetworkView> views = cyNtViewMgr.getNetworkViews(network);
		for(CyNetworkView view:views){
			eventHelper.unsilenceEventSource(view);
		}
	}

}
