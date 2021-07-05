package com.tcb.sensenet.internal.task.export.file;

import org.cytoscape.work.TaskIterator;

import com.tcb.sensenet.internal.app.AppGlobals;

public class WriteFileTaskFactory {
	private AppGlobals appGlobals;

	public WriteFileTaskFactory(AppGlobals appGlobals){
		this.appGlobals = appGlobals;
	}
	
	public TaskIterator createTaskIterator(WriteFileTaskConfig config){
		TaskIterator it = new TaskIterator();
		it.append(new WriteFileTask(config));
		return it;
	}
}
