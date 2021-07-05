package com.tcb.sensenet.internal.task.export.aif.factories;

import org.cytoscape.work.TaskIterator;

import com.tcb.sensenet.internal.app.AppGlobals;
import com.tcb.sensenet.internal.task.export.aif.ExportAifTask;
import com.tcb.sensenet.internal.task.export.aif.ExportAifTaskConfig;

public class ExportTilTaskFactory {
	private AppGlobals appGlobals;
	
	public ExportTilTaskFactory(AppGlobals appGlobals){
		this.appGlobals = appGlobals;
	}
	
	public TaskIterator createTaskIterator(ExportAifTaskConfig config){
		TaskIterator it = new TaskIterator();
		it.append(new ExportAifTask(config,appGlobals));
		return it;
	}
}
