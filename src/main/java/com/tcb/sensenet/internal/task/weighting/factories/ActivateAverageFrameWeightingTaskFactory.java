package com.tcb.sensenet.internal.task.weighting.factories;

import org.cytoscape.work.AbstractTaskFactory;
import org.cytoscape.work.TaskIterator;

import com.tcb.sensenet.internal.aggregation.methods.timeline.FrameWeightMethod;
import com.tcb.sensenet.internal.app.AppGlobals;
import com.tcb.sensenet.internal.task.UI.factories.UpdateUI_TaskFactory;
import com.tcb.sensenet.internal.task.weighting.InitAverageFrameWeightingTask;

public class ActivateAverageFrameWeightingTaskFactory extends AbstractTaskFactory {

	
	private AppGlobals appGlobals;
	private FrameWeightMethod method;

	public ActivateAverageFrameWeightingTaskFactory(AppGlobals appGlobals,
			FrameWeightMethod method){
		this.appGlobals = appGlobals;
		this.method = method;
	}
	
	@Override
	public TaskIterator createTaskIterator() {
		TaskIterator tasks = new TaskIterator();
		
		tasks.append(
				new InitAverageFrameWeightingTask(appGlobals.state.metaNetworkManager,
						appGlobals.metaTimelineFactoryManager, method));
		tasks.append(
				new AverageFrameWeightingTaskFactory(appGlobals)
				.createTaskIterator());
		tasks.append(
				new UpdateUI_TaskFactory(appGlobals).createTaskIterator());
		return tasks;
	}
	
}
