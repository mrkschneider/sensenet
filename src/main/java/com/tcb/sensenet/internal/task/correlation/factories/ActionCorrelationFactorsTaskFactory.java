package com.tcb.sensenet.internal.task.correlation.factories;

import org.cytoscape.work.TaskIterator;

import com.tcb.sensenet.internal.UI.table.TableType;
import com.tcb.sensenet.internal.app.AppGlobals;
import com.tcb.sensenet.internal.log.TaskLogType;
import com.tcb.sensenet.internal.task.aggregation.factories.ShowColumnsTaskFactory;
import com.tcb.sensenet.internal.task.correlation.CorrelationFactorsTaskConfig;

public class ActionCorrelationFactorsTaskFactory {
	private AppGlobals appGlobals;
	
	private static final TaskLogType taskLogType = TaskLogType.CORRELATION_FACTORS;
	
	public ActionCorrelationFactorsTaskFactory(AppGlobals appGlobals){
		this.appGlobals = appGlobals;
	}
	
	public TaskIterator createTaskIterator(CorrelationFactorsTaskConfig config){
		TaskIterator it = new TaskIterator();
		it.append(new CorrelationFactorsTaskFactory(appGlobals).createTaskIterator(config));
		it.append(new ShowColumnsTaskFactory(
				TableType.EDGE,
				config.getEdgeTableWriter().getColumns(),
				taskLogType,
				appGlobals).createTaskIterator());
		return it;
	}
}
