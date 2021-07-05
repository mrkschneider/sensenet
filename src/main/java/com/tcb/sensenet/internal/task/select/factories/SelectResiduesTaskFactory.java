package com.tcb.sensenet.internal.task.select.factories;

import org.cytoscape.work.AbstractTaskFactory;
import org.cytoscape.work.TaskIterator;

import com.tcb.sensenet.internal.app.AppGlobals;
import com.tcb.sensenet.internal.task.UI.factories.UpdateUI_TaskFactory;
import com.tcb.sensenet.internal.task.cluster.ClusterContactTimelinesTask;
import com.tcb.sensenet.internal.task.cluster.ClusteringConfig;
import com.tcb.sensenet.internal.task.plot.factories.ShowClusterAnalysisTaskFactory;
import com.tcb.sensenet.internal.task.select.SelectResiduesTask;
import com.tcb.sensenet.internal.task.select.SelectResiduesTaskConfig;

public class SelectResiduesTaskFactory extends AbstractTaskFactory {

	private AppGlobals appGlobals;
	private SelectResiduesTaskConfig config;

	public  SelectResiduesTaskFactory(
			SelectResiduesTaskConfig config,
			AppGlobals appGlobals){
		this.config = config;
		this.appGlobals = appGlobals;
	}
	
	@Override
	public TaskIterator createTaskIterator() {
		TaskIterator it = new TaskIterator();
		it.append(
				new SelectResiduesTask(
						config,
						appGlobals)
				);
		return it;
	}

}
