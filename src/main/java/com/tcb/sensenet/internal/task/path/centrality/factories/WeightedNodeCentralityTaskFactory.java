package com.tcb.sensenet.internal.task.path.centrality.factories;

import org.cytoscape.work.TaskIterator;

import com.tcb.sensenet.internal.app.AppGlobals;
import com.tcb.sensenet.internal.task.degree.WeightedDegreeTask;
import com.tcb.sensenet.internal.task.degree.WeightedDegreeTaskConfig;
import com.tcb.sensenet.internal.task.path.centrality.WeightedNodeCentralityTask;
import com.tcb.sensenet.internal.task.path.centrality.WeightedNodeCentralityTaskConfig;

public class WeightedNodeCentralityTaskFactory {

	private AppGlobals appGlobals;

	public WeightedNodeCentralityTaskFactory(
			AppGlobals appGlobals){
		this.appGlobals = appGlobals;
	}
	
	public TaskIterator createTaskIterator(WeightedNodeCentralityTaskConfig config) {
		TaskIterator it = new TaskIterator();
		it.append(
				new WeightedNodeCentralityTask(config, appGlobals)
				);
		return it;
	}

}
