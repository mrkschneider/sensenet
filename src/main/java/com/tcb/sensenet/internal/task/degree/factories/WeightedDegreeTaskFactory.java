package com.tcb.sensenet.internal.task.degree.factories;

import org.cytoscape.work.TaskIterator;

import com.tcb.sensenet.internal.app.AppGlobals;
import com.tcb.sensenet.internal.task.degree.WeightedDegreeTask;
import com.tcb.sensenet.internal.task.degree.WeightedDegreeTaskConfig;
import com.tcb.sensenet.internal.task.path.centrality.WeightedNodeCentralityTask;
import com.tcb.sensenet.internal.task.path.centrality.WeightedNodeCentralityTaskConfig;

public class WeightedDegreeTaskFactory {

	private AppGlobals appGlobals;

	public WeightedDegreeTaskFactory(
			AppGlobals appGlobals){
		this.appGlobals = appGlobals;
	}
	
	public TaskIterator createTaskIterator(WeightedDegreeTaskConfig config) {
		TaskIterator it = new TaskIterator();
		it.append(
				new WeightedDegreeTask(config, appGlobals)
				);
		return it;
	}

}
