package com.tcb.sensenet.internal.task.view.factories;

import org.cytoscape.work.AbstractTaskFactory;
import org.cytoscape.work.TaskIterator;

import com.tcb.sensenet.internal.app.AppGlobals;
import com.tcb.sensenet.internal.task.view.UpdateViewTask;



public class UpdateViewTaskFactory extends AbstractTaskFactory {

	private AppGlobals appGlobals;

	public UpdateViewTaskFactory(AppGlobals appGlobals){
		this.appGlobals = appGlobals;
		appGlobals.bundleUtil.register(this);
	}
	
	@Override
	public TaskIterator createTaskIterator() {
		return new TaskIterator(new UpdateViewTask(appGlobals.applicationManager, appGlobals.eventHelper));
	}
	

}
