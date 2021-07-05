package com.tcb.sensenet.internal.task.cli.export.factories;

import org.cytoscape.work.TaskIterator;

import com.tcb.sensenet.internal.app.AppGlobals;
import com.tcb.sensenet.internal.task.cli.create.ImportAifTaskCLI;
import com.tcb.sensenet.internal.task.cli.export.ExportEdgeTableTaskCLI;
import com.tcb.sensenet.internal.task.cli.export.ExportNodeTableTaskCLI;
import com.tcb.sensenet.internal.task.cli.factories.AbstractCLITaskFactory;

public class ExportEdgeTableTaskFactoryCLI extends AbstractCLITaskFactory {

	public ExportEdgeTableTaskFactoryCLI(
			AppGlobals appGlobals) {
		super(appGlobals);
	}
	
	@Override
	public TaskIterator createTaskIterator() {
		return new TaskIterator(
				new ExportEdgeTableTaskCLI(appGlobals));
	}

	@Override
	public String getCommandName() {
		return "exportEdgeTable";
	}

	@Override
	public String getCommandDescription() {
		return "Export edge table to .csv";
	}

	
}
