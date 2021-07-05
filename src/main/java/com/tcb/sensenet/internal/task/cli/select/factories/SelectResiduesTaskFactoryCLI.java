package com.tcb.sensenet.internal.task.cli.select.factories;

import org.cytoscape.work.TaskIterator;

import com.tcb.sensenet.internal.app.AppGlobals;
import com.tcb.sensenet.internal.task.cli.correlation.CorrelationFactorsTaskCLI;
import com.tcb.sensenet.internal.task.cli.create.ImportAifTaskCLI;
import com.tcb.sensenet.internal.task.cli.export.ExportNodeTableTaskCLI;
import com.tcb.sensenet.internal.task.cli.factories.AbstractCLITaskFactory;
import com.tcb.sensenet.internal.task.cli.path.centrality.WeightedNodeCentralityTaskCLI;
import com.tcb.sensenet.internal.task.cli.select.SelectResiduesTaskCLI;

public class SelectResiduesTaskFactoryCLI extends AbstractCLITaskFactory {

	public SelectResiduesTaskFactoryCLI(
			AppGlobals appGlobals) {
		super(appGlobals);
	}
	
	@Override
	public TaskIterator createTaskIterator() {
		return new TaskIterator(
				new SelectResiduesTaskCLI(appGlobals));
	}

	@Override
	public String getCommandName() {
		return "selectResidues";
	}

	@Override
	public String getCommandDescription() {
		return "Select nodes in network by residue indices";
	}

	
}
