package com.tcb.sensenet.internal.task.view.factories;

import org.cytoscape.work.AbstractTaskFactory;
import org.cytoscape.work.TaskIterator;

import com.tcb.sensenet.internal.app.AppGlobals;
import com.tcb.sensenet.internal.task.view.UpdateWeightCutoffTask;
import com.tcb.cytoscape.cyLib.data.Columns;

public class ChangeEdgeCutoffTaskFactory extends AbstractTaskFactory {

	private AppGlobals appGlobals;
	private Double newCutoff;
	private Columns weightColumn;


	public ChangeEdgeCutoffTaskFactory(
			Double newCutoff,
			Columns weightColumn,
			AppGlobals appGlobals){
		this.newCutoff = newCutoff;
		this.weightColumn = weightColumn;
		this.appGlobals = appGlobals;
	}
	
	
	@Override
	public TaskIterator createTaskIterator() {
		TaskIterator it = new TaskIterator();
		it.append(new UpdateWeightCutoffTask(
				newCutoff, weightColumn, appGlobals.applicationManager, appGlobals.state.metaNetworkManager));
		it.append(new UpdateActiveEdgesTaskFactory(appGlobals)
				.createTaskIterator());
		return it;
	}

}
