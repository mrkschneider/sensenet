package com.tcb.sensenet.internal.task.cli.diffusion.factories;

import org.cytoscape.work.TaskIterator;

import com.tcb.sensenet.internal.app.AppGlobals;
import com.tcb.sensenet.internal.task.cli.correlation.CorrelationFactorsTaskCLI;
import com.tcb.sensenet.internal.task.cli.create.ImportAifTaskCLI;
import com.tcb.sensenet.internal.task.cli.degree.WeightedDegreeTaskCLI;
import com.tcb.sensenet.internal.task.cli.diffusion.RandomWalkTaskCLI;
import com.tcb.sensenet.internal.task.cli.export.ExportNodeTableTaskCLI;
import com.tcb.sensenet.internal.task.cli.factories.AbstractCLITaskFactory;
import com.tcb.sensenet.internal.task.cli.path.centrality.WeightedNodeCentralityTaskCLI;

public class RandomWalkTaskFactoryCLI extends AbstractCLITaskFactory {

	public RandomWalkTaskFactoryCLI(
			AppGlobals appGlobals) {
		super(appGlobals);
	}
	
	@Override
	public TaskIterator createTaskIterator() {
		return new TaskIterator(
				new RandomWalkTaskCLI(appGlobals));
	}

	@Override
	public String getCommandName() {
		return "randomWalk";
	}

	@Override
	public String getCommandDescription() {
		return "Perform random walk";
	}

	
}
