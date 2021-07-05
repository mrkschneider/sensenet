package com.tcb.sensenet.internal.task.aggregation.factories;

import java.util.List;

import org.cytoscape.work.AbstractTaskFactory;
import org.cytoscape.work.TaskIterator;

import com.tcb.sensenet.internal.UI.table.TableType;
import com.tcb.sensenet.internal.app.AppGlobals;
import com.tcb.sensenet.internal.log.TaskLogType;
import com.tcb.sensenet.internal.task.aggregation.ShowColumnsTask;

public class ShowColumnsTaskFactory extends AbstractTaskFactory {

	private AppGlobals appGlobals;
	private List<String> columns;
	private TaskLogType taskLogType;
	private TableType tableType;


	public ShowColumnsTaskFactory(
			TableType tableType, List<String> columns,
			TaskLogType taskLogType,
			AppGlobals appGlobals){
		this.appGlobals = appGlobals;
		this.taskLogType = taskLogType;
		this.columns = columns;
		this.tableType = tableType;
	}
	
	
	@Override
	public TaskIterator createTaskIterator() {
		return new TaskIterator(
				new ShowColumnsTask(
						tableType, columns, taskLogType, appGlobals));
	}
	

}
