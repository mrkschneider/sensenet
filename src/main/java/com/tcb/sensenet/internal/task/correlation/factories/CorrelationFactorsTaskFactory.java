package com.tcb.sensenet.internal.task.correlation.factories;

import org.cytoscape.work.Task;
import org.cytoscape.work.TaskIterator;

import com.tcb.sensenet.internal.aggregation.aggregators.nodes.NodeAdjacentEdgeColumnAggregator;
import com.tcb.sensenet.internal.aggregation.aggregators.nodes.NodeAggregator;
import com.tcb.sensenet.internal.aggregation.aggregators.table.RowWriter;
import com.tcb.sensenet.internal.app.AppColumns;
import com.tcb.sensenet.internal.app.AppGlobals;
import com.tcb.sensenet.internal.task.correlation.CorrelationFactorsTask;
import com.tcb.sensenet.internal.task.correlation.CorrelationFactorsTaskConfig;
import com.tcb.sensenet.internal.task.view.factories.UpdateActiveEdgesTaskFactory;
import com.tcb.cytoscape.cyLib.data.Columns;

public class CorrelationFactorsTaskFactory {
	private AppGlobals appGlobals;
	
	public CorrelationFactorsTaskFactory(AppGlobals appGlobals){
		this.appGlobals = appGlobals;
	}
	
	public TaskIterator createTaskIterator(CorrelationFactorsTaskConfig config){
		TaskIterator it = new TaskIterator();
		it.append(new CorrelationFactorsTask(config,appGlobals));
		it.append(new UpdateActiveEdgesTaskFactory(appGlobals).createTaskIterator());
		return it;
	}
		
}
