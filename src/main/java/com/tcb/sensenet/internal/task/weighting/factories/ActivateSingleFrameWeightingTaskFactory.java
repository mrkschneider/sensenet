package com.tcb.sensenet.internal.task.weighting.factories;

import java.util.Arrays;

import org.cytoscape.work.AbstractTaskFactory;
import org.cytoscape.work.TaskIterator;

import com.tcb.sensenet.internal.aggregation.methods.timeline.FrameWeightMethod;
import com.tcb.sensenet.internal.app.AppGlobals;
import com.tcb.sensenet.internal.task.UI.factories.UpdateUI_TaskFactory;
import com.tcb.sensenet.internal.task.weighting.InitSingleFrameWeightingTask;

public class ActivateSingleFrameWeightingTaskFactory extends AbstractTaskFactory {

	
	private AppGlobals appGlobals;
	private FrameWeightMethod method;
	private Integer frame;

	public ActivateSingleFrameWeightingTaskFactory(AppGlobals appGlobals, 
			FrameWeightMethod method, Integer frame){
		this.appGlobals = appGlobals;
		this.method = method;
		this.frame = frame;
	}
	
	@Override
	public TaskIterator createTaskIterator() {
		TaskIterator tasks = new TaskIterator();
		tasks.append(
				new InitSingleFrameWeightingTask(appGlobals.state.metaNetworkManager,
						method,frame));
		tasks.append(
				new FramesetWeightingTaskFactory(Arrays.asList(frame), appGlobals)
				.createTaskIterator());
		tasks.append(
				new UpdateUI_TaskFactory(appGlobals)
				.createTaskIterator());
		return tasks;
	}
	
}
