package com.tcb.sensenet.internal.task.cli.table.factories;

import org.cytoscape.work.TaskIterator;

import com.tcb.sensenet.internal.app.AppGlobals;
import com.tcb.sensenet.internal.task.cli.correlation.CorrelationFactorsTaskCLI;
import com.tcb.sensenet.internal.task.cli.create.ImportAifTaskCLI;
import com.tcb.sensenet.internal.task.cli.degree.WeightedDegreeTaskCLI;
import com.tcb.sensenet.internal.task.cli.diffusion.RandomWalkTaskCLI;
import com.tcb.sensenet.internal.task.cli.export.ExportNodeTableTaskCLI;
import com.tcb.sensenet.internal.task.cli.factories.AbstractCLITaskFactory;
import com.tcb.sensenet.internal.task.cli.path.centrality.WeightedNodeCentralityTaskCLI;
import com.tcb.sensenet.internal.task.cli.table.ImportCsvTableTaskCLI;

public class ImportCsvTableTaskFactoryCLI extends AbstractCLITaskFactory {

	public ImportCsvTableTaskFactoryCLI(
			AppGlobals appGlobals) {
		super(appGlobals);
	}
	
	@Override
	public TaskIterator createTaskIterator() {
		return new TaskIterator(
				new ImportCsvTableTaskCLI(appGlobals));
	}

	@Override
	public String getCommandName() {
		return "table import";
	}

	@Override
	public String getCommandDescription() {
		return "Import csv table";
	}

	
}
