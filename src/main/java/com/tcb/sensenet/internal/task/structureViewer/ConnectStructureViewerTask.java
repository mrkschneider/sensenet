package com.tcb.sensenet.internal.task.structureViewer;

import org.cytoscape.work.AbstractTask;
import org.cytoscape.work.TaskMonitor;

import com.tcb.sensenet.internal.app.AppGlobals;
import com.tcb.sensenet.internal.structureViewer.StructureViewerManager;
import com.tcb.sensenet.internal.structureViewer.events.StructureViewerEventRegistrar;
import com.tcb.sensenet.internal.task.structureViewer.config.ConnectStructureViewerTaskConfig;
import com.tcb.cytoscape.cyLib.cytoApiWrappers.CyNetworkAdapter;
import com.tcb.netmap.structureViewer.StructureViewer;
import com.tcb.netmap.util.InitBlocker;

public class ConnectStructureViewerTask extends AbstractTask {

	private AppGlobals appGlobals;
	private ConnectStructureViewerTaskConfig config;
	
	protected volatile Boolean cancelled;

	public ConnectStructureViewerTask(
			ConnectStructureViewerTaskConfig config,
			AppGlobals appGlobals){
		this.config = config;
		this.appGlobals = appGlobals;
		this.cancelled = false;
	}
	
	@Override
	public void run(TaskMonitor taskMon) throws Exception {
		taskMon.setStatusMessage("Starting structure viewer...");
		
		CyNetworkAdapter network = appGlobals.applicationManager.getCurrentNetwork();
		
		closeOldViewer(network);
		
		StructureViewer viewer = config
				.getViewerFactory()
				.createViewer();
		InitBlocker.waitMaxSeconds(() -> canProceed(viewer), 30);
		if(cancelled) return;
		
		StructureViewerEventRegistrar.register(viewer, appGlobals.eventHelper);
		
		appGlobals.structureViewerManager.setViewer(viewer, config.getViewerType());
		
	}
	
	@Override
	public void cancel(){
		cancelled = true;
	}
	
	private void closeOldViewer(CyNetworkAdapter network){
		StructureViewerManager mgr = appGlobals.structureViewerManager;
		if(!mgr.hasViewer()) return;
		StructureViewer oldViewer = mgr.getViewer();
		oldViewer.stopConnection();
		mgr.getModels().reset();	
	}
	
	private Boolean canProceed(StructureViewer viewer) {
		return viewer.isActive() || cancelled;
	}
	
	
	

}
