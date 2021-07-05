package com.tcb.sensenet.internal.task.cli.create.factories;

import org.cytoscape.work.TaskIterator;

import com.tcb.sensenet.internal.app.AppGlobals;
import com.tcb.sensenet.internal.task.cli.create.ImportAmberHbondsTaskCLI;
import com.tcb.sensenet.internal.task.cli.factories.AbstractCLITaskFactory;

public class ImportAmberHbondsTaskFactoryCLI extends AbstractCLITaskFactory {

	public ImportAmberHbondsTaskFactoryCLI(
			AppGlobals appGlobals) {
		super(appGlobals);
	}
	
	@Override
	public TaskIterator createTaskIterator() {
		return new TaskIterator(new ImportAmberHbondsTaskCLI(appGlobals));
	}

	@Override
	public String getCommandName() {
		return "importCpptrajHbonds";
	}

	@Override
	public String getCommandDescription() {
		return "Import from CPPTRAJ's 'hbonds' output";
	}

	
}
