package com.tcb.sensenet.internal.task.cli.view.factories;

import org.cytoscape.work.TaskIterator;

import com.tcb.sensenet.internal.app.AppGlobals;
import com.tcb.sensenet.internal.task.cli.factories.AbstractCLITaskFactory;
import com.tcb.sensenet.internal.task.cli.view.SetActiveInteractionTypesTaskCLI;
import com.tcb.sensenet.internal.task.cli.weighting.ActivateClusterWeightingTaskCLI;

public class SetActiveInteractionTypesTaskFactoryCLI extends AbstractCLITaskFactory {

	public SetActiveInteractionTypesTaskFactoryCLI(
			AppGlobals appGlobals){
		super(appGlobals);
	}

	@Override
	public TaskIterator createTaskIterator() {
		return new TaskIterator(
				new SetActiveInteractionTypesTaskCLI(appGlobals));
	}

	@Override
	public String getCommandName() {
		return "setActiveInteractionTypes";
	}

	@Override
	public String getCommandDescription() {
		return "Set active interaction types";
	}
	
}
