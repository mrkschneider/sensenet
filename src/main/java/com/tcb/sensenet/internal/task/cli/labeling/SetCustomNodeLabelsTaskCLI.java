package com.tcb.sensenet.internal.task.cli.labeling;

import org.cytoscape.work.TaskFactory;
import org.cytoscape.work.TaskIterator;
import org.cytoscape.work.Tunable;

import com.tcb.sensenet.internal.app.AppGlobals;
import com.tcb.sensenet.internal.labeling.LabelSettings;
import com.tcb.sensenet.internal.task.cli.AbstractWrappedTask;
import com.tcb.sensenet.internal.task.labeling.factories.SetCustomNodeLabelsTaskFactory;

public class SetCustomNodeLabelsTaskCLI extends AbstractWrappedTask {

	
	private LabelSettings defaultSettings = new LabelSettings();
	
	@Tunable(description="Remove chain ids")
	public Boolean removeChainIds = defaultSettings.removeChainIds;
	
	@Tunable(description="Remove dashes")
	public Boolean removeDashes = defaultSettings.removeDashes;
	
	@Tunable(description="Use short node names")
	public Boolean useShortNames = defaultSettings.useShortNames;
		
	public SetCustomNodeLabelsTaskCLI(AppGlobals appGlobals) {
		super(appGlobals);
	}

	@Override
	public TaskIterator createWrappedTasks() {
				
		LabelSettings labelSettings = new LabelSettings();
		

		labelSettings.removeChainIds = removeChainIds.booleanValue();
		labelSettings.removeDashes = removeDashes.booleanValue();
		labelSettings.useShortNames = useShortNames.booleanValue();
		
		TaskFactory fac = new SetCustomNodeLabelsTaskFactory(appGlobals,labelSettings);
		return fac.createTaskIterator();
	}

	
	
}
