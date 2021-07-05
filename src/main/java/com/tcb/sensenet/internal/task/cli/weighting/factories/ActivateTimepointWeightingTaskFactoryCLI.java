package com.tcb.sensenet.internal.task.cli.weighting.factories;

import org.cytoscape.work.TaskIterator;

import com.tcb.sensenet.internal.app.AppGlobals;
import com.tcb.sensenet.internal.task.cli.factories.AbstractCLITaskFactory;
import com.tcb.sensenet.internal.task.cli.weighting.ActivateTimepointWeightingTaskCLI;

public class ActivateTimepointWeightingTaskFactoryCLI extends AbstractCLITaskFactory {

	public ActivateTimepointWeightingTaskFactoryCLI(
			AppGlobals appGlobals){
		super(appGlobals);
	}

	@Override
	public TaskIterator createTaskIterator() {
		return new TaskIterator(
				new ActivateTimepointWeightingTaskCLI(appGlobals));
	}

	@Override
	public String getCommandName() {
		return "weightSingleFrame";
	}

	@Override
	public String getCommandDescription() {
		return "Weight edges as in a single time frame";
	}
	
}
