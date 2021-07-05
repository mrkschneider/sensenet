package com.tcb.sensenet.internal.task.structureViewer.factories;

import org.cytoscape.work.TaskIterator;

import com.tcb.sensenet.internal.app.AppGlobals;
import com.tcb.sensenet.internal.task.UI.factories.UpdateUI_TaskFactory;
import com.tcb.sensenet.internal.task.structureViewer.ConnectStructureViewerTask;
import com.tcb.sensenet.internal.task.structureViewer.config.ConnectStructureViewerTaskConfig;

public class ConnectStructureViewerTaskFactory {

	private AppGlobals appGlobals;

	public ConnectStructureViewerTaskFactory(
			AppGlobals appGlobals){
		this.appGlobals = appGlobals;
	}
	
	public TaskIterator createTaskIterator(ConnectStructureViewerTaskConfig config) {
		TaskIterator it = new TaskIterator();
		it.append(
				new ConnectStructureViewerTask(config, appGlobals)
				);
		it.append(
				new UpdateUI_TaskFactory(appGlobals).createTaskIterator());
		return it;
	}

}
