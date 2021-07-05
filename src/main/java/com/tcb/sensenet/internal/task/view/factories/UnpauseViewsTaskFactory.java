package com.tcb.sensenet.internal.task.view.factories;

import org.cytoscape.work.AbstractTaskFactory;
import org.cytoscape.work.TaskIterator;

import com.tcb.sensenet.internal.app.AppGlobals;
import com.tcb.sensenet.internal.task.view.UnPauseViewsTask;


public class UnpauseViewsTaskFactory extends AbstractTaskFactory {

	private AppGlobals appGlobals;

	public UnpauseViewsTaskFactory(AppGlobals appGlobals){
		this.appGlobals = appGlobals;
		appGlobals.bundleUtil.register(this);
	}
	
	@Override
	public TaskIterator createTaskIterator() {
		return new TaskIterator(new UnPauseViewsTask(
				appGlobals.applicationManager,
				appGlobals.networkViewManager,
				appGlobals.eventHelper));
	}

}
