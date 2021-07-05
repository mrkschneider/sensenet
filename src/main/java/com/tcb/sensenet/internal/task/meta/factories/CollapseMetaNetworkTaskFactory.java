package com.tcb.sensenet.internal.task.meta.factories;

import org.cytoscape.work.AbstractTaskFactory;
import org.cytoscape.work.TaskIterator;

import com.tcb.sensenet.internal.app.AppGlobals;
import com.tcb.sensenet.internal.task.meta.CollapseMetaNetworkTask;

public class CollapseMetaNetworkTaskFactory extends AbstractTaskFactory {

	private AppGlobals appGlobals;

	public CollapseMetaNetworkTaskFactory(AppGlobals appGlobals){
		this.appGlobals = appGlobals;
	}
	
	@Override
	public TaskIterator createTaskIterator(){
		return new TaskIterator(
				new CollapseMetaNetworkTask(appGlobals.state.metaNetworkManager,
						appGlobals.metaNetworkViewManager));
	}
		

}
