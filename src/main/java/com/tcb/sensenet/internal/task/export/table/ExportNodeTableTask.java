package com.tcb.sensenet.internal.task.export.table;

import java.io.File;

import org.cytoscape.work.AbstractTask;
import org.cytoscape.work.TaskIterator;
import org.cytoscape.work.TaskMonitor;

import com.tcb.sensenet.internal.app.AppGlobals;
import com.tcb.sensenet.internal.task.cli.AbstractWrappedTask;
import com.tcb.cytoscape.cyLib.cytoApiWrappers.CyNetworkAdapter;
import com.tcb.cytoscape.cyLib.cytoApiWrappers.CyTableAdapter;

public class ExportNodeTableTask extends AbstractTask {

	private String exportPath;
	private AppGlobals appGlobals;

	public ExportNodeTableTask(
			String exportPath,
			AppGlobals appGlobals){
		this.appGlobals = appGlobals;
		this.exportPath = exportPath;
	}

	@Override
	public void run(TaskMonitor taskMonitor) {
		CyNetworkAdapter network = appGlobals.state.metaNetworkManager.getCurrentNetwork();
		CyTableAdapter table = network.getDefaultNodeTable();
		TaskIterator it = appGlobals.exportTableTaskFactory.createTaskIterator(table, new File(exportPath));
		appGlobals.synTaskManager.execute(it);
	}

}
