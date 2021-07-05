package com.tcb.sensenet.internal.integrationTest;

import java.util.Properties;

import org.cytoscape.work.AbstractTaskFactory;
import org.cytoscape.work.ServiceProperties;
import org.cytoscape.work.TaskIterator;

import com.tcb.sensenet.internal.CyActivator;
import com.tcb.sensenet.internal.app.AppGlobals;

public class SetTestDirectoryTaskFactory extends AbstractTaskFactory {

	private AppGlobals appGlobals;

	public SetTestDirectoryTaskFactory(AppGlobals appGlobals){
		this.appGlobals = appGlobals;
	}
	
	@Override
	public TaskIterator createTaskIterator() {
		TaskIterator tasks = new TaskIterator();
		tasks.append(new SetTestDirectoryTask(appGlobals));		
		return tasks;
	}
	
		
	public static Properties getProperties(){
		Properties p = new Properties();
		p.setProperty(ServiceProperties.COMMAND_NAMESPACE, CyActivator.APP_TEST_NAMESPACE);
		p.setProperty(ServiceProperties.COMMAND,"setTestDir");
		return p;
	}
	
}
