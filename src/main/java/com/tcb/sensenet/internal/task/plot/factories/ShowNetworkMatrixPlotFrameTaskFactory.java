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
import com.tcb.sensenet.internal.task.plot.matrix.ShowNetworkMatrixPlotFrameTask;
import com.tcb.sensenet.internal.task.plot.matrix.ShowNetworkMatrixPlotFrameTaskConfig;

public class ShowNetworkMatrixPlotFrameTaskFactory {
	private AppGlobals appGlobals;

	public ShowNetworkMatrixPlotFrameTaskFactory(AppGlobals appGlobals){
		this.appGlobals = appGlobals;
	}

	public TaskIterator createTaskIterator(ShowNetworkMatrixPlotFrameTaskConfig config) {
		TaskIterator it = new TaskIterator();
		it.append(new ShowNetworkMatrixPlotFrameTask(config, appGlobals));					
		return it;
	}
}
