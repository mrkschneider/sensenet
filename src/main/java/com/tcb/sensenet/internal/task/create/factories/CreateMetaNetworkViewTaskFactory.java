package com.tcb.sensenet.internal.task.create.factories;

import org.cytoscape.work.AbstractTaskFactory;
import org.cytoscape.work.TaskIterator;

import com.tcb.sensenet.internal.app.AppGlobals;
import com.tcb.sensenet.internal.task.create.CreateMetaNetworkViewTask;

public class CreateMetaNetworkViewTaskFactory extends AbstractTaskFactory {
	private AppGlobals appGlobals;

	public CreateMetaNetworkViewTaskFactory(
			AppGlobals appGlobals
			){
		this.appGlobals = appGlobals;
		}
	
	public TaskIterator createTaskIterator(){
		TaskIterator tskIt = new TaskIterator();
		tskIt.append(new CreateMetaNetworkViewTask(appGlobals));
		return tskIt;
	}
	
	

}
