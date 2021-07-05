package com.tcb.sensenet.internal.task.plot.factories;

import org.cytoscape.work.AbstractTaskFactory;
import org.cytoscape.work.TaskIterator;

import com.tcb.sensenet.internal.app.AppGlobals;
import com.tcb.sensenet.internal.plot.LinePlotType;
import com.tcb.sensenet.internal.task.plot.PlotSelectedEdgesTask;
import com.tcb.sensenet.internal.task.plot.PlotSelectedEdgesTaskConfig;

public class PlotSelectedEdgesTaskFactory {
	
	private AppGlobals appGlobals;

	public PlotSelectedEdgesTaskFactory(AppGlobals appGlobals){
		this.appGlobals = appGlobals;
	}

	public TaskIterator createTaskIterator(PlotSelectedEdgesTaskConfig config) {
		return new TaskIterator(new PlotSelectedEdgesTask(appGlobals, config));
	}
}
