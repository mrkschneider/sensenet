package com.tcb.sensenet.internal.task.plot.factories;

import org.cytoscape.work.AbstractTaskFactory;
import org.cytoscape.work.TaskIterator;

import com.tcb.sensenet.internal.analysis.cluster.ClusteringMode;
import com.tcb.sensenet.internal.app.AppColumns;
import com.tcb.sensenet.internal.app.AppGlobals;
import com.tcb.sensenet.internal.meta.network.MetaNetwork;
import com.tcb.sensenet.internal.task.plot.ClearResultPanelTask;
import com.tcb.sensenet.internal.task.plot.ShowClusterAnalysisTask;
import com.tcb.sensenet.internal.task.plot.ShowClusterTreeAnalysisTask;

public class ShowClusterAnalysisTaskFactory extends AbstractTaskFactory {
	private AppGlobals appGlobals;

	public ShowClusterAnalysisTaskFactory(AppGlobals appGlobals){
		this.appGlobals = appGlobals;
	}

	@Override
	public TaskIterator createTaskIterator() {
		TaskIterator it = new TaskIterator();
		it.append(new ShowClusterAnalysisTask(appGlobals));					
		return it;
	}
}
