package com.tcb.sensenet.internal.task.labeling.factories;

import org.cytoscape.work.AbstractTaskFactory;
import org.cytoscape.work.TaskIterator;

import com.tcb.sensenet.internal.app.AppGlobals;
import com.tcb.sensenet.internal.task.labeling.ApplyNodeLabelsTask;
import com.tcb.sensenet.internal.task.labeling.SetCustomResidueIndicesTask;
import com.tcb.sensenet.internal.task.labeling.SetCustomResidueIndicesTaskConfig;

public class SetCustomResidueIndicesTaskFactory extends AbstractTaskFactory {

	private AppGlobals appGlobals;
	private SetCustomResidueIndicesTaskConfig config;
	
	public SetCustomResidueIndicesTaskFactory(SetCustomResidueIndicesTaskConfig config, AppGlobals appGlobals){
		this.appGlobals = appGlobals;
		this.config = config;
	}
	@Override
	public TaskIterator createTaskIterator() {
		TaskIterator tskIt = new TaskIterator();
		tskIt.append(new SetCustomResidueIndicesTask(config, appGlobals.applicationManager, appGlobals.rootNetworkManager));
		tskIt.append(new ApplyNodeLabelsTask(
				appGlobals.state.metaNetworkManager, appGlobals.state.networkSettingsManager));
		return tskIt;
	}

}
