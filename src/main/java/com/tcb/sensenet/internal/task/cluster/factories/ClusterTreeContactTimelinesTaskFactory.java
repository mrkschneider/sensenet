package com.tcb.sensenet.internal.task.cluster.factories;

import org.cytoscape.work.AbstractTaskFactory;
import org.cytoscape.work.TaskIterator;

import com.tcb.sensenet.internal.app.AppGlobals;
import com.tcb.sensenet.internal.task.UI.factories.UpdateUI_TaskFactory;
import com.tcb.sensenet.internal.task.cluster.ClusterContactTimelinesTask;
import com.tcb.sensenet.internal.task.cluster.ClusterTreeContactTimelinesTask;
import com.tcb.sensenet.internal.task.cluster.ClusteringConfig;
import com.tcb.sensenet.internal.task.cluster.TreeClusteringConfig;
import com.tcb.sensenet.internal.task.plot.factories.ShowClusterAnalysisTaskFactory;

public class ClusterTreeContactTimelinesTaskFactory extends AbstractTaskFactory {

	private AppGlobals appGlobals;
	private TreeClusteringConfig config;

	public  ClusterTreeContactTimelinesTaskFactory(
			TreeClusteringConfig config,
			AppGlobals appGlobals){
		this.config = config;
		this.appGlobals = appGlobals;
	}
	
	@Override
	public TaskIterator createTaskIterator() {
		TaskIterator it = new TaskIterator();
		it.append(
				new ClusterTreeContactTimelinesTask(
						config,
						appGlobals)
				);
		it.append(
				new UpdateUI_TaskFactory(appGlobals)
				.createTaskIterator());
		it.append(
				new ShowClusterAnalysisTaskFactory(appGlobals).createTaskIterator());
		return it;
	}

}
