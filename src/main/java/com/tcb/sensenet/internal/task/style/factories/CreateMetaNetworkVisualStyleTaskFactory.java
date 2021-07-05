package com.tcb.sensenet.internal.task.style.factories;

import org.cytoscape.work.AbstractTaskFactory;
import org.cytoscape.work.TaskIterator;

import com.tcb.sensenet.internal.app.AppGlobals;
import com.tcb.sensenet.internal.task.style.CreateMetaNetworkVisualStyleTask;

public class CreateMetaNetworkVisualStyleTaskFactory extends AbstractTaskFactory {

	private AppGlobals appGlobals;

	public CreateMetaNetworkVisualStyleTaskFactory(AppGlobals appGlobals){
		this.appGlobals = appGlobals;
	}
	
	
	@Override
	public TaskIterator createTaskIterator() {
		return new TaskIterator(new CreateMetaNetworkVisualStyleTask(
				appGlobals));
	}

}
