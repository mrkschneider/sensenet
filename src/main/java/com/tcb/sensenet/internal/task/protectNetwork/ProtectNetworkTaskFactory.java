package com.tcb.sensenet.internal.task.protectNetwork;

import org.cytoscape.work.AbstractTaskFactory;
import org.cytoscape.work.TaskIterator;

import com.tcb.sensenet.internal.app.AppGlobals;

public class ProtectNetworkTaskFactory extends AbstractTaskFactory {

	private AppGlobals appGlobals;

	public ProtectNetworkTaskFactory(AppGlobals appGlobals){
		this.appGlobals = appGlobals;
	}
	
	@Override
	public TaskIterator createTaskIterator() {
		return new TaskIterator(
				new ProtectNetworkTask(appGlobals.applicationManager, appGlobals.rootNetworkManager));
	}

}
