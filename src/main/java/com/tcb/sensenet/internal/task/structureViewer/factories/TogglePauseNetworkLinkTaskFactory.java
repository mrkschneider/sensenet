package com.tcb.sensenet.internal.task.structureViewer.factories;

import org.cytoscape.work.TaskIterator;

import com.tcb.sensenet.internal.app.AppGlobals;
import com.tcb.sensenet.internal.task.UI.factories.UpdateUI_TaskFactory;
import com.tcb.sensenet.internal.task.structureViewer.TogglePauseNetworkLinkTask;

public class TogglePauseNetworkLinkTaskFactory {

	private AppGlobals appGlobals;

	public TogglePauseNetworkLinkTaskFactory(
			AppGlobals appGlobals){
		this.appGlobals = appGlobals;
	}
	
	public TaskIterator createTaskIterator() {
		TaskIterator it = new TaskIterator();
		it.append(
				new TogglePauseNetworkLinkTask(appGlobals)
				);
		it.append(
				new UpdateUI_TaskFactory(appGlobals).createTaskIterator());
		return it;
	}

}
