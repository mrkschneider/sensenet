package com.tcb.sensenet.internal.task.cli.weighting.factories;

import org.cytoscape.work.TaskIterator;

import com.tcb.sensenet.internal.app.AppGlobals;
import com.tcb.sensenet.internal.task.cli.factories.AbstractCLITaskFactory;
import com.tcb.sensenet.internal.task.cli.weighting.ActivateClusterWeightingTaskCLI;

public class ActivateClusterWeightingTaskFactoryCLI extends AbstractCLITaskFactory {

	public ActivateClusterWeightingTaskFactoryCLI(
			AppGlobals appGlobals){
		super(appGlobals);
	}

	@Override
	public TaskIterator createTaskIterator() {
		return new TaskIterator(
				new ActivateClusterWeightingTaskCLI(appGlobals));
	}

	@Override
	public String getCommandName() {
		return "weightCluster";
	}

	@Override
	public String getCommandDescription() {
		return "Weight edges by cluster averages";
	}
	
}
