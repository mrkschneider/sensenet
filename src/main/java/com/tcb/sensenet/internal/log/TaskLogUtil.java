package com.tcb.sensenet.internal.log;

import com.tcb.sensenet.internal.meta.network.MetaNetwork;
import com.tcb.cytoscape.cyLib.cytoApiWrappers.CyNetworkAdapter;
import com.tcb.cytoscape.cyLib.log.LogBuilder;
import com.tcb.cytoscape.cyLib.log.ParameterReporter;

public class TaskLogUtil {
	public static LogBuilder createTaskLog(MetaNetwork metaNetwork, TaskLogType type, TaskLogManager logManager){
		LogBuilder log = logManager.registerNew(metaNetwork, type);
		return log;
	}
	
	public static LogBuilder continueTaskLog(MetaNetwork metaNetwork, TaskLogType oldType, TaskLogType newType, TaskLogManager logManager){
		LogBuilder oldLog = logManager.get(metaNetwork).get(oldType);
		LogBuilder newLog = createTaskLog(metaNetwork,newType,logManager);
		newLog.write(oldLog);
		return newLog;
	}
		
	public static void startTaskLog(
			LogBuilder log,
			TaskLogType type,
			MetaNetwork metaNetwork,
			CyNetworkAdapter network,
			ParameterReporter parameters,
			boolean logNetwork){
				
		log.writeDashedLine();
		log.write("Starting task log.");
		log.write("Log type: " + type.toString());
		log.writeDate();
		log.writeEmptyLine();
		
		if(logNetwork){
			LogUtil.logNetwork(log, metaNetwork, network);
		}
				
		LogUtil.logParameters(log, parameters);
		
		log.writeEmptyLine();
	}
	
	public static void startTaskLog(
			LogBuilder log, TaskLogType type, MetaNetwork metaNetwork,
			CyNetworkAdapter network, ParameterReporter parameters){
		startTaskLog(log,type, metaNetwork,network,parameters,true);
	}
		
	public static void finishTaskLog(LogBuilder log){
		log.writeEmptyLine();
		log.write("Finished task.");
		
		log.writeDashedLine();
		//System.out.println(log.get());
	}
}
