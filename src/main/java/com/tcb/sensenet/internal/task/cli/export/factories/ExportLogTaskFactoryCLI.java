package com.tcb.sensenet.internal.task.cli.export.factories;

import org.cytoscape.work.TaskIterator;

import com.tcb.sensenet.internal.app.AppGlobals;
import com.tcb.sensenet.internal.task.cli.create.ImportAifTaskCLI;
import com.tcb.sensenet.internal.task.cli.export.ExportEdgeTableTaskCLI;
import com.tcb.sensenet.internal.task.cli.export.ExportLogTaskCLI;
import com.tcb.sensenet.internal.task.cli.export.ExportNodeTableTaskCLI;
import com.tcb.sensenet.internal.task.cli.factories.AbstractCLITaskFactory;

public class ExportLogTaskFactoryCLI extends AbstractCLITaskFactory {

	public ExportLogTaskFactoryCLI(
			AppGlobals appGlobals) {
		super(appGlobals);
	}
	
	@Override
	public TaskIterator createTaskIterator() {
		return new TaskIterator(
				new ExportLogTaskCLI(appGlobals));
	}

	@Override
	public String getCommandName() {
		return "exportLog";
	}

	@Override
	public String getCommandDescription() {
		return "Export log to file";
	}

	
}
