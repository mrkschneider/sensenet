package com.tcb.sensenet.internal.task.cli.create.factories;

import org.cytoscape.work.TaskIterator;

import com.tcb.sensenet.internal.app.AppGlobals;
import com.tcb.sensenet.internal.task.cli.create.ImportPdbHbondsTaskCLI;
import com.tcb.sensenet.internal.task.cli.factories.AbstractCLITaskFactory;

public class ImportPdbHbondsTaskFactoryCLI extends AbstractCLITaskFactory {

	public ImportPdbHbondsTaskFactoryCLI(
			AppGlobals appGlobals) {
		super(appGlobals);
	}
	
	@Override
	public TaskIterator createTaskIterator() {
		return new TaskIterator(
				new ImportPdbHbondsTaskCLI(appGlobals));
	}

	@Override
	public String getCommandName() {
		return "importPdbHbonds";
	}

	@Override
	public String getCommandDescription() {
		return "Import h-bonds from a pdb file";
	}

	
}
