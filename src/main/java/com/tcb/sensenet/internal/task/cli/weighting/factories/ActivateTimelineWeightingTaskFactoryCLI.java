package com.tcb.sensenet.internal.task.cli.weighting.factories;

import org.cytoscape.work.TaskIterator;

import com.tcb.sensenet.internal.app.AppGlobals;
import com.tcb.sensenet.internal.task.cli.factories.AbstractCLITaskFactory;
import com.tcb.sensenet.internal.task.cli.weighting.ActivateTimelineWeightingTaskCLI;

public class ActivateTimelineWeightingTaskFactoryCLI extends AbstractCLITaskFactory {

	public ActivateTimelineWeightingTaskFactoryCLI(
			AppGlobals appGlobals){
		super(appGlobals);
	}

	@Override
	public TaskIterator createTaskIterator() {
		return new TaskIterator(
				new ActivateTimelineWeightingTaskCLI(appGlobals));
	}

	@Override
	public String getCommandName() {
		return "weightAverage";
	}

	@Override
	public String getCommandDescription() {
		return "Weight edges by timeline averages";
	}
	
}
