package com.tcb.sensenet.internal.task.path.search.factories;

import org.cytoscape.work.TaskIterator;

import com.tcb.sensenet.internal.app.AppGlobals;
import com.tcb.sensenet.internal.task.path.search.SearchPathsTask;
import com.tcb.sensenet.internal.task.path.search.SearchPathsTaskConfig;
import com.tcb.sensenet.internal.util.ObjMap;

public class ShortestPathsTaskFactory {

	private AppGlobals appGlobals;

	public ShortestPathsTaskFactory(
			AppGlobals appGlobals){
		this.appGlobals = appGlobals;
	}
	
	public TaskIterator createTaskIterator(SearchPathsTaskConfig config) {
		TaskIterator it = new TaskIterator();
		ObjMap results = new ObjMap();
		it.append(
				new SearchPathsTask(results, config, appGlobals)
				);
		return it;
	}

}
