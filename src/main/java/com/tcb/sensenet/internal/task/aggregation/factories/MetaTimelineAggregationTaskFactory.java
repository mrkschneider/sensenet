package com.tcb.sensenet.internal.task.aggregation.factories;

import org.cytoscape.work.AbstractTaskFactory;
import org.cytoscape.work.TaskIterator;

import com.tcb.sensenet.internal.app.AppGlobals;
import com.tcb.sensenet.internal.task.aggregation.MetaTimelineAggregateTask;
import com.tcb.sensenet.internal.task.aggregation.MetaTimelineAggregationTaskConfig;
import com.tcb.sensenet.internal.task.view.factories.UpdateActiveEdgesTaskFactory;
import com.tcb.sensenet.internal.util.ObjMap;

public class MetaTimelineAggregationTaskFactory {
	
	private AppGlobals appGlobals;

	public MetaTimelineAggregationTaskFactory(
			AppGlobals appGlobals){
		this.appGlobals = appGlobals;
	}

	public TaskIterator createTaskIterator(ObjMap results, MetaTimelineAggregationTaskConfig config) {
		TaskIterator tasks = new TaskIterator();
		tasks.append(
					new MetaTimelineAggregateTask(
							results,
							appGlobals.state.metaNetworkManager,
							appGlobals.metaTimelineFactoryManager,
							appGlobals.state.logManager,
							config));
		tasks.append(new UpdateActiveEdgesTaskFactory(appGlobals).createTaskIterator());
		return tasks;
	}
	
	
}
