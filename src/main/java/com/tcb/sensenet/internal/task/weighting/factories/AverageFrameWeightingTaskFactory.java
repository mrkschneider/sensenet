package com.tcb.sensenet.internal.task.weighting.factories;

import org.cytoscape.work.AbstractTaskFactory;
import org.cytoscape.work.TaskIterator;

import com.tcb.sensenet.internal.app.AppGlobals;
import com.tcb.sensenet.internal.task.view.factories.UpdateActiveEdgesTaskFactory;
import com.tcb.sensenet.internal.task.weighting.WeightAverageFrameTask;

public class AverageFrameWeightingTaskFactory extends AbstractTaskFactory {
	
	private AppGlobals appGlobals;

	public AverageFrameWeightingTaskFactory(AppGlobals appGlobals){
		this.appGlobals = appGlobals;
	}

	@Override
	public TaskIterator createTaskIterator() {
		TaskIterator tasks = new TaskIterator();
		tasks.append(
				new WeightAverageFrameTask(appGlobals.state.metaNetworkManager,
						appGlobals.metaTimelineFactoryManager,
						appGlobals.state.logManager,
						appGlobals.synTaskManager)
				);
		tasks.append(
				new UpdateActiveEdgesTaskFactory(appGlobals)
				.createTaskIterator());
		return tasks;
	}
	
	
}
