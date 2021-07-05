package com.tcb.sensenet.internal.task.view.factories;

import java.util.ArrayList;

import org.cytoscape.model.CyNode;
import org.cytoscape.work.AbstractTaskFactory;
import org.cytoscape.work.TaskIterator;

import com.tcb.sensenet.internal.app.AppGlobals;
import com.tcb.sensenet.internal.task.style.factories.UpdateNodesAndEdgesVisualStyleTaskFactory;
import com.tcb.sensenet.internal.task.view.UpdateActiveEdgesTask;
import com.tcb.sensenet.internal.task.view.UpdateViewTask;


public class UpdateActiveEdgesTaskFactory extends AbstractTaskFactory {

	private AppGlobals appGlobals;

	public UpdateActiveEdgesTaskFactory(
			AppGlobals appGlobals
			){
		this.appGlobals = appGlobals;
	}
	
	@Override
	public TaskIterator createTaskIterator() {
		TaskIterator it = new TaskIterator();
		it.append(new UpdateActiveEdgesTask(appGlobals));
		it.append(new UpdateViewTask(appGlobals.applicationManager, appGlobals.eventHelper));
		it.append(new UpdateNodesAndEdgesVisualStyleTaskFactory(appGlobals)
				.createTaskIterator(new ArrayList<CyNode>(), null));
		return it;
	}

}
