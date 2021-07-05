package com.tcb.sensenet.internal.task.structureViewer.factories;

import org.cytoscape.work.TaskIterator;

import com.tcb.sensenet.internal.app.AppGlobals;
import com.tcb.sensenet.internal.task.structureViewer.TransferResidueColorsTask;

public class SyncResidueColorsTaskFactory {

	private AppGlobals appGlobals;
	
	public SyncResidueColorsTaskFactory(
			AppGlobals appGlobals){
		this.appGlobals = appGlobals;
	}
	
	public TaskIterator createTaskIterator() {
		TaskIterator it = new TaskIterator();
		it.append(
				new TransferResidueColorsTask(appGlobals)
				);
		return it;
	}

}
