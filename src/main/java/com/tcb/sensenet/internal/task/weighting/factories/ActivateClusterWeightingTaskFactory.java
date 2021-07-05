package com.tcb.sensenet.internal.task.weighting.factories;

import java.util.List;

import org.cytoscape.work.AbstractTaskFactory;
import org.cytoscape.work.TaskIterator;

import com.google.common.collect.ImmutableList;
import com.tcb.cluster.Cluster;
import com.tcb.sensenet.internal.aggregation.methods.timeline.FrameWeightMethod;
import com.tcb.sensenet.internal.app.AppGlobals;
import com.tcb.sensenet.internal.task.UI.factories.UpdateUI_TaskFactory;
import com.tcb.sensenet.internal.task.weighting.InitClusterWeightingTask;

public class ActivateClusterWeightingTaskFactory extends AbstractTaskFactory {

	
	private AppGlobals appGlobals;
	private FrameWeightMethod method;
	private Cluster cluster;
	private Integer selectedClusterIndex;

	public ActivateClusterWeightingTaskFactory(AppGlobals appGlobals,
			FrameWeightMethod method,
			Integer selectedClusterIndex,
			Cluster clusterSelection){
		this.appGlobals = appGlobals;
		this.method = method;
		this.selectedClusterIndex = selectedClusterIndex;
		this.cluster = clusterSelection;
	}
	
	@Override
	public TaskIterator createTaskIterator() {
		List<Integer> selectedFrames = cluster.getData().stream()
				.map(s -> Integer.valueOf(s))
				.collect(ImmutableList.toImmutableList());
		TaskIterator tasks = new TaskIterator();
		tasks.append(
				new InitClusterWeightingTask(appGlobals.state.metaNetworkManager,
						method, selectedClusterIndex));
		tasks.append(
				new FramesetWeightingTaskFactory(selectedFrames,appGlobals)
				.createTaskIterator());
		tasks.append(
				new UpdateUI_TaskFactory(appGlobals)
				.createTaskIterator());
		return tasks;
	}
	
}
