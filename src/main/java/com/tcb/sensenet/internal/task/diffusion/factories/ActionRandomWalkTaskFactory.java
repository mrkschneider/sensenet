package com.tcb.sensenet.internal.task.diffusion.factories;

import java.util.ArrayList;
import java.util.List;

import org.cytoscape.work.TaskIterator;

import com.tcb.sensenet.internal.UI.table.TableType;
import com.tcb.sensenet.internal.aggregation.aggregators.table.RowWriter;
import com.tcb.sensenet.internal.app.AppGlobals;
import com.tcb.sensenet.internal.task.aggregation.factories.ShowColumnsTaskFactory;
import com.tcb.sensenet.internal.task.degree.WeightedDegreeTaskConfig;
import com.tcb.sensenet.internal.task.diffusion.RandomWalkTask;
import com.tcb.sensenet.internal.task.path.centrality.WeightedNodeCentralityTask;
import com.tcb.sensenet.internal.task.path.centrality.WeightedNodeCentralityTaskConfig;

public class ActionRandomWalkTaskFactory {

	private AppGlobals appGlobals;

	public ActionRandomWalkTaskFactory(
			AppGlobals appGlobals){
		this.appGlobals = appGlobals;
	}
	
	public TaskIterator createTaskIterator(RandomWalkTask.Config config) {
		TaskIterator it = new TaskIterator();
		RowWriter writer = config.getRowWriter();
		
		List<String> columns = new ArrayList<>(writer.getColumns());
		
		it.append(
				new RandomWalkTaskFactory(appGlobals).createTaskIterator(config)
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
