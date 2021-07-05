package com.tcb.sensenet.internal.task.table.factories;

import org.cytoscape.work.AbstractTaskFactory;
import org.cytoscape.work.TaskIterator;

import com.tcb.sensenet.internal.app.AppGlobals;
import com.tcb.sensenet.internal.task.meta.CollapseMetaNetworkTask;
import com.tcb.sensenet.internal.task.table.ImportCsvTableTask;

public class ImportCsvTableTaskFactory {

	private AppGlobals appGlobals;

	public ImportCsvTableTaskFactory(AppGlobals appGlobals){
		this.appGlobals = appGlobals;
	}
	
	public TaskIterator createTaskIterator(ImportCsvTableTask.Config config){
		return new TaskIterator(
				new ImportCsvTableTask(appGlobals,config));
	}
		

}
