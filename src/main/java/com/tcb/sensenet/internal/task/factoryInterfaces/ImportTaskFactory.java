package com.tcb.sensenet.internal.task.factoryInterfaces;

import org.cytoscape.work.TaskIterator;

import com.tcb.sensenet.internal.init.ImportConfig;

public interface ImportTaskFactory {
	public TaskIterator createTaskIterator(ImportConfig config);
}
