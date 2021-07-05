package com.tcb.sensenet.internal.task.meta.factories;

import org.cytoscape.work.TaskIterator;

import com.tcb.sensenet.internal.app.AppGlobals;
import com.tcb.sensenet.internal.task.meta.ShowMetaTimelinesTask;
import com.tcb.sensenet.internal.task.meta.ShowMetaTimelinesTaskConfig;

public class ShowMetaTimelinesTaskFactory {

	private AppGlobals appGlobals;

	public ShowMetaTimelinesTaskFactory(AppGlobals appGlobals){
		this.appGlobals = appGlobals;
	}
	
	public TaskIterator createTaskIterator(ShowMetaTimelinesTaskConfig config){
		return new TaskIterator(
				new ShowMetaTimelinesTask(config, appGlobals));
	}
		

}
