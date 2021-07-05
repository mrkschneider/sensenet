package com.tcb.sensenet.internal.task.cli.create.factories;

import org.cytoscape.work.TaskIterator;

import com.tcb.sensenet.internal.app.AppGlobals;
import com.tcb.sensenet.internal.task.cli.create.ImportAmberNativeContactsTaskCLI;
import com.tcb.sensenet.internal.task.cli.factories.AbstractCLITaskFactory;

public class ImportAmberNativeContactsTaskFactoryCLI extends AbstractCLITaskFactory {

	public ImportAmberNativeContactsTaskFactoryCLI(
			AppGlobals appGlobals) {
		super(appGlobals);
	}
	
	@Override
	public TaskIterator createTaskIterator() {
		return new TaskIterator(
				new ImportAmberNativeContactsTaskCLI(appGlobals));
	}

	@Override
	public String getCommandName() {
		return "importCpptrajNativeContacts";
	}

	@Override
	public String getCommandDescription() {
		return "Import from CPPTRAJ's 'nativecontacts' output";
	}

	
}
