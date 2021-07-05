package com.tcb.sensenet.internal.task.labeling.factories;

import org.cytoscape.work.AbstractTaskFactory;
import org.cytoscape.work.TaskIterator;

import com.tcb.sensenet.internal.app.AppGlobals;
import com.tcb.sensenet.internal.task.labeling.SetStandardResidueIndicesTask;

public class SetStandardResidueIndicesTaskFactory extends AbstractTaskFactory {

	private AppGlobals appGlobals;
	
	public SetStandardResidueIndicesTaskFactory(AppGlobals appGlobals){
		this.appGlobals = appGlobals;
	}
	
	@Override
	public TaskIterator createTaskIterator() {
		TaskIterator tskIt = new TaskIterator();
		tskIt.append(new SetStandardResidueIndicesTask(appGlobals.applicationManager, appGlobals.rootNetworkManager));
		return tskIt;
	}

}
