package com.tcb.sensenet.internal.task.cli.labeling.factories;

import org.cytoscape.work.TaskIterator;

import com.tcb.sensenet.internal.app.AppGlobals;
import com.tcb.sensenet.internal.task.cli.factories.AbstractCLITaskFactory;
import com.tcb.sensenet.internal.task.cli.labeling.SetCustomNodeLabelsTaskCLI;

public class SetCustomNodeLabelsTaskFactoryCLI extends AbstractCLITaskFactory {

	public SetCustomNodeLabelsTaskFactoryCLI(
			AppGlobals appGlobals){
		super(appGlobals);
	}

	@Override
	public TaskIterator createTaskIterator() {
		return new TaskIterator(
				new SetCustomNodeLabelsTaskCLI(appGlobals));
	}

	@Override
	public String getCommandName() {
		return "labelNodes";
	}

	@Override
	public String getCommandDescription() {
		return "Set custom node labels";
	}
	
}
