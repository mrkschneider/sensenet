package com.tcb.sensenet.internal.task.cli.degree.factories;

import org.cytoscape.work.TaskIterator;

import com.tcb.sensenet.internal.app.AppGlobals;
import com.tcb.sensenet.internal.task.cli.correlation.CorrelationFactorsTaskCLI;
import com.tcb.sensenet.internal.task.cli.create.ImportAifTaskCLI;
import com.tcb.sensenet.internal.task.cli.degree.WeightedDegreeTaskCLI;
import com.tcb.sensenet.internal.task.cli.export.ExportNodeTableTaskCLI;
import com.tcb.sensenet.internal.task.cli.factories.AbstractCLITaskFactory;
import com.tcb.sensenet.internal.task.cli.path.centrality.WeightedNodeCentralityTaskCLI;

public class WeightedDegreeTaskFactoryCLI extends AbstractCLITaskFactory {

	public WeightedDegreeTaskFactoryCLI(
			AppGlobals appGlobals) {
		super(appGlobals);
	}
	
	@Override
	public TaskIterator createTaskIterator() {
		return new TaskIterator(
				new WeightedDegreeTaskCLI(appGlobals));
	}

	@Override
	public String getCommandName() {
		return "degree";
	}

	@Override
	public String getCommandDescription() {
		return "Calculate weighted degree";
	}

	
}
