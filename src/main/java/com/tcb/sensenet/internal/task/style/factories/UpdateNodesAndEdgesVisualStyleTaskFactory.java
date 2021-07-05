package com.tcb.sensenet.internal.task.style.factories;

import java.util.List;

import org.cytoscape.model.CyEdge;
import org.cytoscape.model.CyNode;
import org.cytoscape.work.TaskIterator;

import com.tcb.sensenet.internal.app.AppGlobals;
import com.tcb.sensenet.internal.task.style.UpdateNodesAndEdgesVisStyleTask;
import com.tcb.sensenet.internal.task.view.factories.NodesAndEdgesTaskFactory;


public class UpdateNodesAndEdgesVisualStyleTaskFactory implements NodesAndEdgesTaskFactory {

	private AppGlobals appGlobals;

	public UpdateNodesAndEdgesVisualStyleTaskFactory(AppGlobals appGlobals){
		this.appGlobals = appGlobals;
		appGlobals.bundleUtil.register(this);
	}
	
	@Override
	public TaskIterator createTaskIterator(List<CyNode> nodes, List<CyEdge> edges) {
		return new TaskIterator(new UpdateNodesAndEdgesVisStyleTask(nodes,edges,
				appGlobals.state.metaNetworkManager, 
				appGlobals.visualMappingManager,
				appGlobals.networkViewManager));
	}

	@Override
	public TaskIterator createTaskIterator() {
		return createTaskIterator(null,null);
	}
	
}
