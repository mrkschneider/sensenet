package com.tcb.sensenet.internal.task.cli.export.factories;

import org.cytoscape.work.TaskIterator;

import com.tcb.sensenet.internal.app.AppGlobals;
import com.tcb.sensenet.internal.task.cli.create.ImportAifTaskCLI;
import com.tcb.sensenet.internal.task.cli.export.ExportNodeTableTaskCLI;
import com.tcb.sensenet.internal.task.cli.factories.AbstractCLITaskFactory;

public class ExportNodeTableTaskFactoryCLI extends AbstractCLITaskFactory {

	public ExportNodeTableTaskFactoryCLI(
			AppGlobals appGlobals) {
		super(appGlobals);
	}
	
	@Override
	public TaskIterator createTaskIterator() {
		return new TaskIterator(
				new ExportNodeTableTaskCLI(appGlobals));
	}

	@Override
	public String getCommandName() {
		return "exportNodeTable";
	}

	@Override
	public String getCommandDescription() {
		return "Export node table to .csv";
	}

	
}
