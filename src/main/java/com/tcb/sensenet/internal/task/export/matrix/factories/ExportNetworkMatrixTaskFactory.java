package com.tcb.sensenet.internal.task.export.matrix.factories;

import org.cytoscape.work.TaskIterator;

import com.tcb.sensenet.internal.app.AppGlobals;
import com.tcb.sensenet.internal.task.export.aif.ExportAifTask;
import com.tcb.sensenet.internal.task.export.aif.ExportAifTaskConfig;
import com.tcb.sensenet.internal.task.export.matrix.ExportNetworkMatrixTask;
import com.tcb.sensenet.internal.task.export.matrix.ExportNetworkMatrixTaskConfig;

public class ExportNetworkMatrixTaskFactory {
	private AppGlobals appGlobals;
	
	public ExportNetworkMatrixTaskFactory(AppGlobals appGlobals){
		this.appGlobals = appGlobals;
	}
	
	public TaskIterator createTaskIterator(ExportNetworkMatrixTaskConfig config){
		TaskIterator it = new TaskIterator();
		it.append(new ExportNetworkMatrixTask(config,appGlobals));
		return it;
	}
}
