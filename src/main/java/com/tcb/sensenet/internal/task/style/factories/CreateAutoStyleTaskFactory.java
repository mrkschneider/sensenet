package com.tcb.sensenet.internal.task.style.factories;

import org.cytoscape.view.vizmap.VisualMappingFunction;
import org.cytoscape.work.TaskIterator;

import com.tcb.sensenet.internal.app.AppGlobals;
import com.tcb.sensenet.internal.task.style.CreateAutoStyleTask;


public class CreateAutoStyleTaskFactory {

	private AppGlobals appGlobals;

	public CreateAutoStyleTaskFactory(AppGlobals appGlobals){
		this.appGlobals = appGlobals;
	}
	
	public TaskIterator createTaskIterator(VisualMappingFunction<?,?> mappingFun) {
		return new TaskIterator(new CreateAutoStyleTask(
				mappingFun,appGlobals));
	}

}
