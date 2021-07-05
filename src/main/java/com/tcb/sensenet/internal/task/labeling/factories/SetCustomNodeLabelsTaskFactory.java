package com.tcb.sensenet.internal.task.labeling.factories;

import org.cytoscape.work.AbstractTaskFactory;
import org.cytoscape.work.TaskIterator;

import com.tcb.sensenet.internal.app.AppGlobals;
import com.tcb.sensenet.internal.labeling.LabelSettings;
import com.tcb.sensenet.internal.task.labeling.ApplyNodeLabelsTask;
import com.tcb.sensenet.internal.task.labeling.SetCustomNodeLabelSettingsTask;

public class SetCustomNodeLabelsTaskFactory extends AbstractTaskFactory {

	private AppGlobals appGlobals;
	private LabelSettings labelSettings;
	
	public SetCustomNodeLabelsTaskFactory(AppGlobals appGlobals,
			LabelSettings labelSettings){
		this.appGlobals = appGlobals;
		this.labelSettings = labelSettings;
		
	}
	@Override
	public TaskIterator createTaskIterator() {
		TaskIterator tskIt = new TaskIterator();
		tskIt.append(new SetCustomNodeLabelSettingsTask(appGlobals.state.metaNetworkManager, 
				appGlobals.state.networkSettingsManager, labelSettings));
		tskIt.append(
				new ApplyNodeLabelsTask(appGlobals.state.metaNetworkManager, appGlobals.state.networkSettingsManager));
		return tskIt;
	}

}
