package com.tcb.sensenet.internal.task.labeling.factories;

import org.cytoscape.work.AbstractTaskFactory;
import org.cytoscape.work.TaskIterator;

import com.tcb.sensenet.internal.app.AppGlobals;
import com.tcb.sensenet.internal.task.labeling.ApplyNodeLabelsTask;

public class ResetResidueNumberingTaskFactory extends AbstractTaskFactory {

	private AppGlobals appGlobals;
	
	public ResetResidueNumberingTaskFactory(AppGlobals appGlobals){
		this.appGlobals = appGlobals;
	}
	@Override
	public TaskIterator createTaskIterator() {
		TaskIterator tskIt = new TaskIterator();
		tskIt.append(new SetStandardResidueIndicesTaskFactory(appGlobals).createTaskIterator());
		tskIt.append(new ApplyNodeLabelsTask(
				appGlobals.state.metaNetworkManager, appGlobals.state.networkSettingsManager));
		return tskIt;
	}
	

}
