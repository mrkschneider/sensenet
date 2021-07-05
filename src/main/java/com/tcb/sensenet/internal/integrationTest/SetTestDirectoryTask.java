package com.tcb.sensenet.internal.integrationTest;

import org.cytoscape.work.AbstractTask;
import org.cytoscape.work.TaskMonitor;
import org.cytoscape.work.Tunable;

import com.tcb.sensenet.internal.app.AppGlobals;
import com.tcb.sensenet.internal.properties.AppProperty;

public class SetTestDirectoryTask extends AbstractTask {

	@Tunable(description="directory")
	public String path="";
	private AppGlobals appGlobals;

	public SetTestDirectoryTask(AppGlobals appGlobals){
		this.appGlobals = appGlobals;
	}
	
	@Override
	public void run(TaskMonitor taskMon) throws Exception {
		appGlobals.appProperties.set(AppProperty.TEST_DIR, path);		
	}

}
