package com.tcb.sensenet.internal.task.weighting.factories;

import java.util.List;

import org.cytoscape.work.AbstractTaskFactory;
import org.cytoscape.work.TaskIterator;

import com.tcb.sensenet.internal.app.AppGlobals;
import com.tcb.sensenet.internal.task.view.factories.UpdateActiveEdgesTaskFactory;
import com.tcb.sensenet.internal.task.weighting.WeightFramesetTask;

public class FramesetWeightingTaskFactory extends AbstractTaskFactory {
	
	private AppGlobals appGlobals;
	private List<Integer> selectedFrames;

	public FramesetWeightingTaskFactory(
			List<Integer> selectedFrames,
			AppGlobals appGlobals){
		this.selectedFrames = selectedFrames;
		this.appGlobals = appGlobals;
	}

	@Override
	public TaskIterator createTaskIterator() {
		TaskIterator tasks = new TaskIterator();
		tasks.append(
				new WeightFramesetTask(
						selectedFrames,
						appGlobals.state.metaNetworkManager,
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
