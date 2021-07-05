package com.tcb.sensenet.internal.task.UI;

import org.cytoscape.task.visualize.ApplyPreferredLayoutTaskFactory;
import org.cytoscape.work.AbstractTask;
import org.cytoscape.work.TaskIterator;
import org.cytoscape.work.TaskManager;
import org.cytoscape.work.TaskMonitor;

import com.tcb.cytoscape.cyLib.cytoApiWrappers.CyApplicationManagerAdapter;
import com.tcb.cytoscape.cyLib.cytoApiWrappers.CyNetworkViewManagerAdapter;

public class ApplyPreferredLayoutTaskAdapter extends AbstractTask {
	private CyApplicationManagerAdapter applicationManager;
	private CyNetworkViewManagerAdapter viewManager;
	private TaskManager taskManager;
	private ApplyPreferredLayoutTaskFactory taskFactory;

	public ApplyPreferredLayoutTaskAdapter(
			ApplyPreferredLayoutTaskFactory taskFactory,
			CyApplicationManagerAdapter applicationManager,
			CyNetworkViewManagerAdapter viewManager,
			TaskManager taskManager){
		this.taskFactory = taskFactory;
		this.applicationManager = applicationManager;
		this.viewManager = viewManager;
		this.taskManager = taskManager;
	}

	@Override
	public void run(TaskMonitor taskMonitor) throws Exception {
		TaskIterator it = taskFactory.createTaskIterator(
				viewManager.getNetworkViews(
						applicationManager.getCurrentNetwork()));
		taskManager.execute(it);
	}

}
