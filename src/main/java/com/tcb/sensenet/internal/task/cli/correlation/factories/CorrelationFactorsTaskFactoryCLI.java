package com.tcb.sensenet.internal.task.cli.correlation.factories;

import org.cytoscape.work.TaskIterator;

import com.tcb.sensenet.internal.app.AppGlobals;
import com.tcb.sensenet.internal.task.cli.correlation.CorrelationFactorsTaskCLI;
import com.tcb.sensenet.internal.task.cli.create.ImportAifTaskCLI;
import com.tcb.sensenet.internal.task.cli.export.ExportNodeTableTaskCLI;
import com.tcb.sensenet.internal.task.cli.factories.AbstractCLITaskFactory;
import com.tcb.sensenet.internal.task.cli.path.centrality.WeightedNodeCentralityTaskCLI;

public class CorrelationFactorsTaskFactoryCLI extends AbstractCLITaskFactory {

	public CorrelationFactorsTaskFactoryCLI(
			AppGlobals appGlobals) {
		super(appGlobals);
	}
	
	@Override
	public TaskIterator createTaskIterator() {
		return new TaskIterator(
				new CorrelationFactorsTaskCLI(appGlobals));
	}

	@Override
	public String getCommandName() {
		return "edgeCorrelationFactors";
	}

	@Override
	public String getCommandDescription() {
		return "Calculate edge timeline correlation factors";
	}

	
}
