package com.tcb.sensenet.internal.task.structureViewer.factories;

import org.cytoscape.work.TaskIterator;

import com.tcb.sensenet.internal.app.AppGlobals;
import com.tcb.sensenet.internal.task.UI.factories.UpdateUI_TaskFactory;
import com.tcb.sensenet.internal.task.structureViewer.LinkModelToStructureViewerTask;
import com.tcb.sensenet.internal.task.structureViewer.config.AddModelToStructureViewerTaskConfig;

public class LinkModelToStructureViewerTaskFactory {

	private AppGlobals appGlobals;

	public LinkModelToStructureViewerTaskFactory(
			AppGlobals appGlobals){
		this.appGlobals = appGlobals;
	}
	
	public TaskIterator createTaskIterator(AddModelToStructureViewerTaskConfig config) {
		TaskIterator it = new TaskIterator();
		it.append(
				new LinkModelToStructureViewerTask(config, appGlobals)
				);
		it.append(
				new UpdateUI_TaskFactory(appGlobals).createTaskIterator());
		return it;
	}

}
