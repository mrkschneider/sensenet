package com.tcb.sensenet.internal.task.UI.factories;

import org.cytoscape.task.visualize.ApplyPreferredLayoutTaskFactory;
import org.cytoscape.work.AbstractTaskFactory;
import org.cytoscape.work.TaskIterator;
import org.cytoscape.work.TaskManager;

import com.tcb.sensenet.internal.task.UI.ApplyPreferredLayoutTaskAdapter;
import com.tcb.cytoscape.cyLib.cytoApiWrappers.CyApplicationManagerAdapter;
import com.tcb.cytoscape.cyLib.cytoApiWrappers.CyNetworkViewManagerAdapter;



public class ApplyPreferredLayoutTaskAdapterFactory extends AbstractTaskFactory {

	private ApplyPreferredLayoutTaskFactory applyPreferredLayoutTaskFactory;
	private CyApplicationManagerAdapter applicationManager;
	private CyNetworkViewManagerAdapter networkViewManager;
	private TaskManager taskManager;


	public ApplyPreferredLayoutTaskAdapterFactory(
			ApplyPreferredLayoutTaskFactory applyPreferredLayoutTaskFactory,
			CyApplicationManagerAdapter applicationManager,
			CyNetworkViewManagerAdapter networkViewManager,
			TaskManager taskManager
){
		this.applyPreferredLayoutTaskFactory = applyPreferredLayoutTaskFactory;
		this.applicationManager = applicationManager;
		this.networkViewManager = networkViewManager;
		this.taskManager = taskManager;
}
	
	
	@Override
	public TaskIterator createTaskIterator() {
		return new TaskIterator(new ApplyPreferredLayoutTaskAdapter(
				applyPreferredLayoutTaskFactory, applicationManager,
				networkViewManager, taskManager));
	}

}
