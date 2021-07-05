package com.tcb.sensenet.internal.task.export.file;

import org.cytoscape.work.AbstractTask;
import org.cytoscape.work.TaskMonitor;

import com.tcb.sensenet.internal.data.DefaultFileWriter;
import com.tcb.cytoscape.cyLib.log.LogBuilder;

public class WriteFileTask extends AbstractTask {

	private WriteFileTaskConfig config;

	public WriteFileTask(WriteFileTaskConfig config){
		this.config = config;
	}
	
	@Override
	public void run(TaskMonitor taskMon) throws Exception {
		DefaultFileWriter writer = new DefaultFileWriter();
		writer.write(config.getContent(), config.getPath());		
	}

}
