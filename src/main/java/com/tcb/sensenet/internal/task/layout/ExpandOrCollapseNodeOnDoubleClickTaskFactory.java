package com.tcb.sensenet.internal.task.layout;

import org.cytoscape.model.CyNode;
import org.cytoscape.task.NodeViewTaskFactory;
import org.cytoscape.view.model.CyNetworkView;
import org.cytoscape.view.model.View;
import org.cytoscape.work.Task;
import org.cytoscape.work.TaskIterator;

import com.tcb.sensenet.internal.app.AppGlobals;
import com.tcb.cytoscape.cyLib.cytoApiWrappers.CyNetworkViewAdapter;

public class ExpandOrCollapseNodeOnDoubleClickTaskFactory implements NodeViewTaskFactory {
	
	private AppGlobals appGlobals;
	
	public ExpandOrCollapseNodeOnDoubleClickTaskFactory(
			AppGlobals appGlobals){
		this.appGlobals = appGlobals;
	}

	@Override
	public TaskIterator createTaskIterator(View<CyNode> nodeView, CyNetworkView networkView) {
		Task expandOrCollapseNodeTask = new ExpandOrCollapseNodeTask(
				nodeView, 
				new CyNetworkViewAdapter(networkView),
				appGlobals.state.metaNetworkManager,
				appGlobals.metaNetworkViewManager);
		return new TaskIterator(expandOrCollapseNodeTask);
	}
	
	@Override
	public boolean isReady(View<CyNode> arg0, CyNetworkView arg1) {
		return true;
	}

}
