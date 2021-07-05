package com.tcb.sensenet.internal.task.cli.path.centrality.factories;

import org.cytoscape.work.TaskIterator;

import com.tcb.sensenet.internal.app.AppGlobals;
import com.tcb.sensenet.internal.task.cli.create.ImportAifTaskCLI;
import com.tcb.sensenet.internal.task.cli.export.ExportNodeTableTaskCLI;
import com.tcb.sensenet.internal.task.cli.factories.AbstractCLITaskFactory;
import com.tcb.sensenet.internal.task.cli.path.centrality.WeightedNodeCentralityTaskCLI;

public class WeightedNodeCentralityTaskFactoryCLI extends AbstractCLITaskFactory {

	public WeightedNodeCentralityTaskFactoryCLI(
			AppGlobals appGlobals) {
		super(appGlobals);
	}
	
	@Override
	public TaskIterator createTaskIterator() {
		return new TaskIterator(
				new WeightedNodeCentralityTaskCLI(appGlobals));
	}

	@Override
	public String getCommandName() {
		return "nodeCentrality";
	}

	@Override
	public String getCommandDescription() {
		return "Calculate weighted node centrality";
	}

	
}
