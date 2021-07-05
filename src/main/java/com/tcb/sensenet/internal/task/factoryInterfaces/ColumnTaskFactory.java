package com.tcb.sensenet.internal.task.factoryInterfaces;

import org.cytoscape.work.TaskIterator;

import com.tcb.sensenet.internal.app.AppColumns;

public interface ColumnTaskFactory {
	public TaskIterator createTaskIterator(AppColumns column);
}
