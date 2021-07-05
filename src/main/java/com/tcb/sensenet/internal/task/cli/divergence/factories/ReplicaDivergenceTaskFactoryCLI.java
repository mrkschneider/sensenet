package com.tcb.sensenet.internal.task.cli.divergence.factories;

import org.cytoscape.work.TaskIterator;

import com.tcb.sensenet.internal.app.AppGlobals;
import com.tcb.sensenet.internal.task.cli.correlation.CorrelationFactorsTaskCLI;
import com.tcb.sensenet.internal.task.cli.create.ImportAifTaskCLI;
import com.tcb.sensenet.internal.task.cli.divergence.ReplicaDivergenceTaskCLI;
import com.tcb.sensenet.internal.task.cli.export.ExportNodeTableTaskCLI;
import com.tcb.sensenet.internal.task.cli.factories.AbstractCLITaskFactory;
import com.tcb.sensenet.internal.task.cli.path.centrality.WeightedNodeCentralityTaskCLI;

public class ReplicaDivergenceTaskFactoryCLI extends AbstractCLITaskFactory {

	public ReplicaDivergenceTaskFactoryCLI(
			AppGlobals appGlobals) {
		super(appGlobals);
	}
	
	@Override
	public TaskIterator createTaskIterator() {
		return new TaskIterator(
				new ReplicaDivergenceTaskCLI(appGlobals));
	}

	@Override
	public String getCommandName() {
		return "replicaDivergence";
	}

	@Override
	public String getCommandDescription() {
		return "Calculate edge timeline divergence between replicas";
	}

	
}
