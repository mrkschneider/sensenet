package com.tcb.sensenet.internal.task.cli.entropy.factories;

import org.cytoscape.work.TaskIterator;

import com.tcb.sensenet.internal.app.AppGlobals;
import com.tcb.sensenet.internal.task.cli.correlation.CorrelationFactorsTaskCLI;
import com.tcb.sensenet.internal.task.cli.create.ImportAifTaskCLI;
import com.tcb.sensenet.internal.task.cli.entropy.EntropyTaskCLI;
import com.tcb.sensenet.internal.task.cli.export.ExportNodeTableTaskCLI;
import com.tcb.sensenet.internal.task.cli.factories.AbstractCLITaskFactory;
import com.tcb.sensenet.internal.task.cli.path.centrality.WeightedNodeCentralityTaskCLI;

public class EntropyTaskFactoryCLI extends AbstractCLITaskFactory {

	public EntropyTaskFactoryCLI(
			AppGlobals appGlobals) {
		super(appGlobals);
	}
	
	@Override
	public TaskIterator createTaskIterator() {
		return new TaskIterator(
				new EntropyTaskCLI(appGlobals));
	}

	@Override
	public String getCommandName() {
		return "entropy";
	}

	@Override
	public String getCommandDescription() {
		return "Calculate timeline entropy";
	}

	
}
