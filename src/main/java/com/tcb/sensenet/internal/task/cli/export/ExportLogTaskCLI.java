package com.tcb.sensenet.internal.task.cli.export;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

import org.cytoscape.task.TableTaskFactory;
import org.cytoscape.work.AbstractTask;
import org.cytoscape.work.Task;
import org.cytoscape.work.TaskIterator;
import org.cytoscape.work.TaskMonitor;
import org.cytoscape.work.Tunable;

import com.tcb.sensenet.internal.app.AppGlobals;
import com.tcb.sensenet.internal.data.DefaultFileWriter;
import com.tcb.sensenet.internal.log.LogType;
import com.tcb.sensenet.internal.log.TaskLogType;
import com.tcb.sensenet.internal.log.select.GlobalLogSelecter;
import com.tcb.sensenet.internal.log.select.LogSelecter;
import com.tcb.sensenet.internal.log.select.TaskLogSelecter;
import com.tcb.sensenet.internal.meta.network.MetaNetwork;
import com.tcb.sensenet.internal.task.cli.AbstractWrappedTask;
import com.tcb.sensenet.internal.task.export.file.WriteFileTaskConfig;
import com.tcb.sensenet.internal.task.export.file.WriteFileTaskFactory;
import com.tcb.sensenet.internal.task.export.table.ExportNodeTableTask;
import com.tcb.sensenet.internal.util.EnumUtil;
import com.tcb.cytoscape.cyLib.cytoApiWrappers.CyNetworkAdapter;
import com.tcb.cytoscape.cyLib.cytoApiWrappers.CyTableAdapter;
import com.tcb.cytoscape.cyLib.log.LogBuilder;
import com.tcb.cytoscape.cyLib.util.NullUtil;
import com.tcb.aifgen.importer.InteractionImporter;
import com.tcb.aifgen.importer.aifImporter.AifImporter;



public class ExportLogTaskCLI extends AbstractWrappedTask {

	@Tunable(description="out .log file path")
	public String exportPath;
	
	@Tunable(description="log type")
	public String logType;
	
	@Tunable(description="task log type")
	public String taskLogType;
	
	public ExportLogTaskCLI(AppGlobals appGlobals) {
		super(appGlobals);
	}
	
	@Override
	public TaskIterator createWrappedTasks() {
		NullUtil.requireNonNull(exportPath, "exportPath");
		NullUtil.requireNonNull(logType, "logType");
		
		LogType logTypeSelection = EnumUtil.valueOfCLI(logType, LogType.class);
		Optional<TaskLogType> taskLogTypeSelection = getTaskLogType();
		Path path = Paths.get(exportPath);
		LogSelecter logSelecter = getLogSelecter(logTypeSelection,taskLogTypeSelection);
		
		WriteFileTaskConfig config = WriteFileTaskConfig.create(path, logSelecter.getLog().get());
		TaskIterator it = new WriteFileTaskFactory(appGlobals).createTaskIterator(config);
		
		return it;
	}
	
	private Optional<TaskLogType> getTaskLogType(){
		if(taskLogType==null) return Optional.empty();
		TaskLogType taskLogTypeEnum = EnumUtil.valueOfCLI(taskLogType, TaskLogType.class);
		return Optional.of(taskLogTypeEnum);
	}
	
	private LogSelecter getLogSelecter(LogType logTypeSelection, Optional<TaskLogType> taskLogTypeSelection){
		switch(logTypeSelection){
		case GLOBAL: return new GlobalLogSelecter(appGlobals);
		case TASK: 
			MetaNetwork metaNetwork = appGlobals.state.metaNetworkManager.getCurrentMetaNetwork();
			TaskLogType taskLogTypeEnum = taskLogTypeSelection
					.orElseThrow(() -> new IllegalArgumentException("Must specify task log type"));
			return new TaskLogSelecter(taskLogTypeEnum,metaNetwork,appGlobals);
		default: throw new IllegalArgumentException();
		}
	}
	
	

}
