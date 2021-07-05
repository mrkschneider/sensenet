package com.tcb.sensenet.internal.task.cli.create.factories;

import org.cytoscape.work.TaskIterator;

import com.tcb.sensenet.internal.app.AppGlobals;
import com.tcb.sensenet.internal.task.cli.create.ImportAifTaskCLI;
import com.tcb.sensenet.internal.task.cli.factories.AbstractCLITaskFactory;

public class ImportAifTaskFactoryCLI extends AbstractCLITaskFactory {

	public ImportAifTaskFactoryCLI(
			AppGlobals appGlobals) {
		super(appGlobals);
	}
	
	@Override
	public TaskIterator createTaskIterator() {
		return new TaskIterator(
				new ImportAifTaskCLI(appGlobals));
	}

	@Override
	public String getCommandName() {
		return "importAif";
	}

	@Override
	public String getCommandDescription() {
		return "Import from .aif/.zaif file";
	}

	
}
