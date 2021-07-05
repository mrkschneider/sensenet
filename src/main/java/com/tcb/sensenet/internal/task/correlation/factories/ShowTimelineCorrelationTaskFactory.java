package com.tcb.sensenet.internal.task.correlation.factories;

import org.cytoscape.work.TaskIterator;

import com.tcb.sensenet.internal.app.AppGlobals;
import com.tcb.sensenet.internal.task.correlation.ShowTimelineCorrelationTask;
import com.tcb.sensenet.internal.task.correlation.TimelineCorrelationTaskConfig;

public class ShowTimelineCorrelationTaskFactory {
	private AppGlobals appGlobals;
	
	public ShowTimelineCorrelationTaskFactory(AppGlobals appGlobals){
		this.appGlobals = appGlobals;
	}
	
	public TaskIterator createTaskIterator(TimelineCorrelationTaskConfig config){
		TaskIterator it = new TaskIterator();
		it.append(new ShowTimelineCorrelationTask(config,appGlobals));
		return it;
	}
}
