package com.tcb.sensenet.internal.task.create.factories;

import org.cytoscape.work.TaskIterator;

import com.tcb.sensenet.internal.app.AppGlobals;
import com.tcb.sensenet.internal.init.ImportConfig;
import com.tcb.sensenet.internal.task.create.CreateMetaNetworkTask;

public class CreateMetaNetworkTaskFactory {

	private AppGlobals appGlobals;


	public CreateMetaNetworkTaskFactory(AppGlobals appGlobals){
		this.appGlobals = appGlobals;
	}

	public TaskIterator createTaskIterator(ImportConfig config) {
		return new TaskIterator(new CreateMetaNetworkTask(config,appGlobals));
	}

}
