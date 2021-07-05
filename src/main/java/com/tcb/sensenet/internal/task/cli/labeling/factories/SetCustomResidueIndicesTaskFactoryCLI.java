package com.tcb.sensenet.internal.task.cli.labeling.factories;

import org.cytoscape.work.TaskIterator;

import com.tcb.sensenet.internal.app.AppGlobals;
import com.tcb.sensenet.internal.task.cli.factories.AbstractCLITaskFactory;
import com.tcb.sensenet.internal.task.cli.labeling.SetCustomResidueIndicesTaskCLI;

public class SetCustomResidueIndicesTaskFactoryCLI extends AbstractCLITaskFactory {

	public SetCustomResidueIndicesTaskFactoryCLI(
			AppGlobals appGlobals){
		super(appGlobals);
	}

	@Override
	public TaskIterator createTaskIterator() {
		return new TaskIterator(
				new SetCustomResidueIndicesTaskCLI(appGlobals));
	}

	@Override
	public String getCommandName() {
		return "renumberResidueLabels";
	}

	@Override
	public String getCommandDescription() {
		return "Adjust label residue numbers";
	}
	
}
