package com.tcb.sensenet.internal.log.select;

import com.tcb.sensenet.internal.app.AppGlobals;
import com.tcb.sensenet.internal.log.LogStore;
import com.tcb.sensenet.internal.log.TaskLogType;
import com.tcb.sensenet.internal.meta.network.MetaNetwork;
import com.tcb.cytoscape.cyLib.log.LogBuilder;

public class TaskLogSelecter implements LogSelecter {

	private MetaNetwork metaNetwork;
	private AppGlobals appGlobals;
	private TaskLogType taskLogType;

	public TaskLogSelecter(TaskLogType taskLogType, MetaNetwork metaNetwork, AppGlobals appGlobals){
		this.taskLogType = taskLogType;
		this.metaNetwork = metaNetwork;
		this.appGlobals = appGlobals;
	}
	
	@Override
	public LogBuilder getLog() {
		LogStore logStore = appGlobals.state.logManager.get(metaNetwork);
		if(!logStore.containsKey(taskLogType)) throw new IllegalArgumentException(
				"No log data found for: " + taskLogType.toString());
		return logStore.get(taskLogType);
	}

}
