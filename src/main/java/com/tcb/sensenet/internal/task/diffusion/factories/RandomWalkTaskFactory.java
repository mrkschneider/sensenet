package com.tcb.sensenet.internal.task.diffusion.factories;

import org.cytoscape.work.TaskIterator;

import com.tcb.sensenet.internal.app.AppGlobals;
import com.tcb.sensenet.internal.task.degree.WeightedDegreeTask;
import com.tcb.sensenet.internal.task.degree.WeightedDegreeTaskConfig;
import com.tcb.sensenet.internal.task.diffusion.RandomWalkTask;
import com.tcb.sensenet.internal.task.path.centrality.WeightedNodeCentralityTask;
import com.tcb.sensenet.internal.task.path.centrality.WeightedNodeCentralityTaskConfig;

public class RandomWalkTaskFactory {

	private AppGlobals appGlobals;

	public RandomWalkTaskFactory(
			AppGlobals appGlobals){
		this.appGlobals = appGlobals;
	}
	
	public TaskIterator createTaskIterator(RandomWalkTask.Config config) {
		TaskIterator it = new TaskIterator();
		it.append(
				new RandomWalkTask(config, appGlobals)
				);
		return it;
	}

}
