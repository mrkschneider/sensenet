package com.tcb.sensenet.internal.task.style.factories;

import org.cytoscape.work.AbstractTaskFactory;
import org.cytoscape.work.TaskIterator;

import com.tcb.sensenet.internal.app.AppGlobals;
import com.tcb.sensenet.internal.task.style.UpdateVisStyleTask;


public class UpdateVisualStyleTaskFactory extends AbstractTaskFactory {

	private AppGlobals appGlobals;

	public UpdateVisualStyleTaskFactory(AppGlobals appGlobals){
		this.appGlobals = appGlobals;
	}
	
	@Override
	public TaskIterator createTaskIterator() {
		return new TaskIterator(new UpdateVisStyleTask(
				appGlobals.state.metaNetworkManager, appGlobals.visualMappingManager,
				appGlobals.networkViewManager));
	}

}
