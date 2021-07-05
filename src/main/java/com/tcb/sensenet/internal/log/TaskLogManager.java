package com.tcb.sensenet.internal.log;

import java.util.ArrayList;
import java.util.List;

import com.tcb.sensenet.internal.meta.network.MetaNetwork;
import com.tcb.sensenet.internal.util.BasicMetaNetworkManager;
import com.tcb.cytoscape.cyLib.log.LogBuilder;

public class TaskLogManager extends BasicMetaNetworkManager<LogStore> {
	private List<LogBuilder> logs;
		
	public TaskLogManager(){
		this.logs = new ArrayList<>();
	}
	
	public LogBuilder registerNew(MetaNetwork metaNetwork, TaskLogType type){
		LogBuilder log = new LogBuilder();
		this.get(metaNetwork).putOrReplace(type.name(), log);
		this.logs.add(log);
		return log;
	}
	
	public LogBuilder getGlobalLog(){
		LogBuilder b = new LogBuilder();
		logs.forEach(l -> b.write(l));
		return b;
	}
	
	public List<LogBuilder> getGlobalLogs(){
		return logs;
	}
	
	public void setGlobalLogs(List<LogBuilder> logs){
		this.logs = logs;
	}
}
