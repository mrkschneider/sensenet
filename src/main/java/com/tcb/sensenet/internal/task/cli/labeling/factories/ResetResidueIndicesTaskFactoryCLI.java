package com.tcb.sensenet.internal.task.cli.labeling.factories;

import org.cytoscape.work.TaskIterator;

import com.tcb.sensenet.internal.app.AppGlobals;
import com.tcb.sensenet.internal.task.cli.factories.AbstractCLITaskFactory;
import com.tcb.sensenet.internal.task.labeling.factories.ResetResidueNumberingTaskFactory;

public class ResetResidueIndicesTaskFactoryCLI extends AbstractCLITaskFactory {

	public ResetResidueIndicesTaskFactoryCLI(
			AppGlobals appGlobals){
		super(appGlobals);
	}

	@Override
	public TaskIterator createTaskIterator() {
		return new ResetResidueNumberingTaskFactory(appGlobals).createTaskIterator();
	}

	@Override
	public String getCommandName() {
		return "resetResidueNumbers";
	}

	@Override
	public String getCommandDescription() {
		return "reset label residue numbers";
	}
	
}
