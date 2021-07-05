package com.tcb.sensenet.internal.task.path.search.factories;

import org.cytoscape.work.TaskIterator;

import com.tcb.sensenet.internal.app.AppGlobals;
import com.tcb.sensenet.internal.task.path.search.SearchPathsTask;
import com.tcb.sensenet.internal.task.path.search.SearchPathsTaskConfig;
import com.tcb.sensenet.internal.task.path.search.ShowSearchPathsAnalysisTask;
import com.tcb.sensenet.internal.util.ObjMap;

public class ShowPathsAnalysisTaskFactory {

	private AppGlobals appGlobals;

	public ShowPathsAnalysisTaskFactory(
			AppGlobals appGlobals){
		this.appGlobals = appGlobals;
	}
	
	public TaskIterator createTaskIterator(SearchPathsTaskConfig config) {
		TaskIterator it = new TaskIterator();
		ObjMap results = new ObjMap();
		it.append(new SearchPathsTask(results, config, appGlobals));
		it.append(new ShowSearchPathsAnalysisTask(results,config, appGlobals));
		return it;
	}

}
