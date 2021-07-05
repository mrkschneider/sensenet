package com.tcb.sensenet.internal.task.cli.create.factories;

import org.cytoscape.work.TaskIterator;

import com.tcb.sensenet.internal.app.AppGlobals;
import com.tcb.sensenet.internal.task.cli.create.ImportPdbContactsTaskCLI;
import com.tcb.sensenet.internal.task.cli.factories.AbstractCLITaskFactory;

public class ImportPdbContactsTaskFactoryCLI extends AbstractCLITaskFactory {

	public ImportPdbContactsTaskFactoryCLI(
			AppGlobals appGlobals) {
		super(appGlobals);
	}
	
	@Override
	public TaskIterator createTaskIterator() {
		return new TaskIterator(
				new ImportPdbContactsTaskCLI(appGlobals));
	}

	@Override
	public String getCommandName() {
		return "importPdbContacts";
	}

	@Override
	public String getCommandDescription() {
		return "Import contacts from a pdb file";
	}

	
}
