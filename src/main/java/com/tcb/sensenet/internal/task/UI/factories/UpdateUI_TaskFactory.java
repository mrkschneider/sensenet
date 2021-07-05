package com.tcb.sensenet.internal.task.UI.factories;

import java.util.Arrays;

import org.cytoscape.work.AbstractTaskFactory;
import org.cytoscape.work.TaskIterator;

import com.tcb.sensenet.internal.app.AppGlobals;
import com.tcb.sensenet.internal.task.UI.UpdateUI_Task;

public class UpdateUI_TaskFactory extends AbstractTaskFactory {

	private AppGlobals appGlobals;

	public UpdateUI_TaskFactory(AppGlobals appGlobals){
		this.appGlobals = appGlobals;
	}
	
	@Override
	public TaskIterator createTaskIterator() {
		return new TaskIterator(new UpdateUI_Task(
				Arrays.asList(
				appGlobals.stateManagers.appPanelStateManager,
				appGlobals.stateManagers.showInteractionsPanelStateManager,
				appGlobals.stateManagers.aggregationModeSelectionPanelStateManager,
				appGlobals.stateManagers.aggregationModeControlsPanelStateManager,
				appGlobals.stateManagers.selectedFramePanelStateManager,
				appGlobals.stateManagers.selectedClusterPanelStateManager,
				appGlobals.stateManagers.weightPanelStateManager,
				appGlobals.stateManagers.viewerPanelStateManager,
				appGlobals.stateManagers.viewerStatusPanelStateManager,
				appGlobals.stateManagers.analysisPanelStateManager
				)));
	}

}
