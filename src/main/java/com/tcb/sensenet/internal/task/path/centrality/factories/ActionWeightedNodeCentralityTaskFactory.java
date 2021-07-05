package com.tcb.sensenet.internal.task.path.centrality.factories;

import java.util.ArrayList;
import java.util.List;

import org.cytoscape.work.TaskIterator;

import com.tcb.sensenet.internal.UI.table.TableType;
import com.tcb.sensenet.internal.aggregation.aggregators.table.RowWriter;
import com.tcb.sensenet.internal.app.AppGlobals;
import com.tcb.sensenet.internal.task.aggregation.factories.ShowColumnsTaskFactory;
import com.tcb.sensenet.internal.task.path.centrality.WeightedNodeCentralityTask;
import com.tcb.sensenet.internal.task.path.centrality.WeightedNodeCentralityTaskConfig;

public class ActionWeightedNodeCentralityTaskFactory {

	private AppGlobals appGlobals;

	public ActionWeightedNodeCentralityTaskFactory(
			AppGlobals appGlobals){
		this.appGlobals = appGlobals;
	}
	
	public TaskIterator createTaskIterator(WeightedNodeCentralityTaskConfig config) {
		TaskIterator it = new TaskIterator();
		RowWriter writer = config.getRowWriter();
		
		List<String> columns = new ArrayList<>(writer.getColumns());
		
		it.append(
				new WeightedNodeCentralityTaskFactory(appGlobals).createTaskIterator(
						config)
				);
		it.append(
				new ShowColumnsTaskFactory(
						TableType.NODE,
						columns,
						config.getTaskLogType(),
						appGlobals).createTaskIterator());
		return it;
	}

}
