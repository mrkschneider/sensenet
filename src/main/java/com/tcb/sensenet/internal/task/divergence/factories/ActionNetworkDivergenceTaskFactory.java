package com.tcb.sensenet.internal.task.divergence.factories;

import java.util.ArrayList;
import java.util.List;

import org.cytoscape.work.TaskIterator;

import com.tcb.sensenet.internal.UI.table.TableType;
import com.tcb.sensenet.internal.aggregation.aggregators.table.RowWriter;
import com.tcb.sensenet.internal.app.AppGlobals;
import com.tcb.sensenet.internal.log.TaskLogType;
import com.tcb.sensenet.internal.task.aggregation.factories.ActionMetaTimelineAggregationTaskFactory;
import com.tcb.sensenet.internal.task.aggregation.factories.MetaTimelineAggregationTaskFactory;
import com.tcb.sensenet.internal.task.aggregation.factories.ShowColumnsTaskFactory;
import com.tcb.sensenet.internal.task.degree.WeightedDegreeTaskConfig;
import com.tcb.sensenet.internal.task.divergence.DivergenceTaskConfig;
import com.tcb.sensenet.internal.task.divergence.AggregationToNetworkDivergenceTask;
import com.tcb.sensenet.internal.task.path.centrality.WeightedNodeCentralityTask;
import com.tcb.sensenet.internal.task.path.centrality.WeightedNodeCentralityTaskConfig;
import com.tcb.sensenet.internal.task.plot.ShowDivergenceTask;
import com.tcb.sensenet.internal.util.ObjMap;

public class ActionNetworkDivergenceTaskFactory {

	private AppGlobals appGlobals;

	public ActionNetworkDivergenceTaskFactory(
			AppGlobals appGlobals){
		this.appGlobals = appGlobals;
	}
	
	public TaskIterator createTaskIterator(
			ObjMap results, DivergenceTaskConfig config) {
		TaskIterator it = new TaskIterator();
				
		it.append(
				new ActionMetaTimelineAggregationTaskFactory(appGlobals)
					.createTaskIterator(results,config)
				);
		//it.append(
		//		new AggregationToNetworkDivergenceTask(results));
		//it.append(
		//		new ShowDivergenceTask(results,appGlobals));
		return it;
	}

}
