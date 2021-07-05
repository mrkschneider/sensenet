package com.tcb.sensenet.internal.UI.state;

import org.cytoscape.app.event.AppsFinishedStartingEvent;
import org.cytoscape.app.event.AppsFinishedStartingListener;
import org.cytoscape.application.events.SetSelectedNetworkViewsEvent;
import org.cytoscape.application.events.SetSelectedNetworkViewsListener;
import org.cytoscape.application.events.SetSelectedNetworksEvent;
import org.cytoscape.application.events.SetSelectedNetworksListener;
import org.cytoscape.model.events.NetworkAddedEvent;
import org.cytoscape.model.events.NetworkAddedListener;
import org.cytoscape.model.events.NetworkDestroyedEvent;
import org.cytoscape.model.events.NetworkDestroyedListener;
import org.cytoscape.work.TaskIterator;

import com.tcb.sensenet.internal.app.AppGlobals;
import com.tcb.sensenet.internal.structureViewer.events.StructureViewerClosedEvent;
import com.tcb.sensenet.internal.structureViewer.events.StructureViewerClosedListener;
import com.tcb.sensenet.internal.task.UI.factories.UpdateUI_TaskFactory;

public class Update_UI_onNetworkChanges implements NetworkAddedListener,
NetworkDestroyedListener,
SetSelectedNetworksListener,
SetSelectedNetworkViewsListener,
AppsFinishedStartingListener,
StructureViewerClosedListener {


	private AppGlobals appGlobals;

	public Update_UI_onNetworkChanges(AppGlobals appGlobals){
		this.appGlobals = appGlobals;
	}
	
	private void updateUI(){
		TaskIterator updateUI_Tasks = new UpdateUI_TaskFactory(appGlobals).createTaskIterator();
		appGlobals.taskManager.execute(updateUI_Tasks);
	}
			
	@Override
	public void handleEvent(NetworkAddedEvent e) {
		updateUI();
		
	}

	@Override
	public void handleEvent(NetworkDestroyedEvent e) {
		updateUI();
	}

	@Override
	public void handleEvent(SetSelectedNetworksEvent arg0) {
		updateUI();
		
	}

	@Override
	public void handleEvent(SetSelectedNetworkViewsEvent arg0) {
		updateUI();
	}

	@Override
	public void handleEvent(AppsFinishedStartingEvent arg0) {
		updateUI();
	}

	@Override
	public void handleEvent(StructureViewerClosedEvent e) {
		updateUI();		
	}

}
