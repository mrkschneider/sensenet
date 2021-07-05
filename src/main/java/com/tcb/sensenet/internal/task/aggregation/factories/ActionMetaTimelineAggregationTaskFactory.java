package com.tcb.sensenet.internal.task.aggregation.factories;

import java.util.List;

import org.cytoscape.work.AbstractTaskFactory;
import org.cytoscape.work.TaskIterator;

import com.tcb.sensenet.internal.UI.table.TableType;
import com.tcb.sensenet.internal.app.AppGlobals;
import com.tcb.sensenet.internal.log.TaskLogType;
import com.tcb.sensenet.internal.task.aggregation.MetaTimelineAggregationTaskConfig;
import com.tcb.sensenet.internal.util.ObjMap;

public class ActionMetaTimelineAggregationTaskFactory {
	
	private AppGlobals appGlobals;

	public ActionMetaTimelineAggregationTaskFactory(
			AppGlobals appGlobals){
		this.appGlobals = appGlobals;
	}

	public TaskIterator createTaskIterator(ObjMap results, MetaTimelineAggregationTaskConfig config) {
		List<String> columns = config.getRowWriter().getColumns();
		TaskLogType logType = config.getTaskLogType();
		TaskIterator tasks = new TaskIterator();
		tasks.append(
				new MetaTimelineAggregationTaskFactory(appGlobals).createTaskIterator(results,config)
				);
		tasks.append(
				new ShowColumnsTaskFactory(TableType.EDGE, columns,logType,appGlobals).createTaskIterator()
				);
		
		return tasks;
	}
	
	
}
