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
import com.tcb.sensenet.internal.task.divergence.EdgeDivergenceTask;
import com.tcb.sensenet.internal.task.divergence.EdgeDivergenceTaskConfig;
import com.tcb.sensenet.internal.task.divergence.AggregationToNetworkDivergenceTask;
import com.tcb.sensenet.internal.task.path.centrality.WeightedNodeCentralityTask;
import com.tcb.sensenet.internal.task.path.centrality.WeightedNodeCentralityTaskConfig;
import com.tcb.sensenet.internal.task.plot.ClearResultPanelTask;
import com.tcb.sensenet.internal.task.plot.ShowDivergenceTask;
import com.tcb.sensenet.internal.util.ObjMap;

public class ActionEdgeDivergenceTaskFactory {

	private AppGlobals appGlobals;

	public ActionEdgeDivergenceTaskFactory(
			AppGlobals appGlobals){
		this.appGlobals = appGlobals;
	}
	
	public TaskIterator createTaskIterator(
			ObjMap results, EdgeDivergenceTaskConfig config) {
		TaskIterator it = new TaskIterator();
				
		it.append(
				new EdgeDivergenceTask(results, appGlobals, config));
		it.append(
				new ClearResultPanelTask(appGlobals));
		it.append(
				new ShowDivergenceTask(results,appGlobals));
		return it;
	}

}
