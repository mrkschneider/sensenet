package com.tcb.sensenet.internal.task.view.factories;

import java.util.List;

import org.cytoscape.work.AbstractTaskFactory;
import org.cytoscape.work.TaskIterator;

import com.tcb.sensenet.internal.app.AppGlobals;
import com.tcb.sensenet.internal.task.UI.factories.UpdateUI_TaskFactory;
import com.tcb.sensenet.internal.task.view.SetActiveInteractionTypesTask;



public class SetActiveInteractionTypesTaskFactory extends AbstractTaskFactory {

	private AppGlobals appGlobals;
	private List<String> newSelections;

	public SetActiveInteractionTypesTaskFactory(
			List<String> newSelections,
			AppGlobals appGlobals
			){
		this.newSelections = newSelections;
		this.appGlobals = appGlobals;
	}
	
	@Override
	public TaskIterator createTaskIterator() {
		TaskIterator it = new TaskIterator();
		it.append(new SetActiveInteractionTypesTask(
				newSelections, appGlobals.applicationManager, appGlobals.state.metaNetworkManager));
		it.append(new UpdateActiveEdgesTaskFactory(appGlobals)
				.createTaskIterator());
		it.append(new UpdateUI_TaskFactory(appGlobals).createTaskIterator());
		return it;
	}

}
