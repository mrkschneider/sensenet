package com.tcb.sensenet.internal.task.cli.factories;

import java.util.Properties;

import org.cytoscape.work.AbstractTaskFactory;
import org.cytoscape.work.ServiceProperties;
import org.cytoscape.work.TaskIterator;

import com.tcb.sensenet.internal.CyActivator;
import com.tcb.sensenet.internal.app.AppGlobals;
import com.tcb.cytoscape.cyLib.task.cli.CLITaskFactory;

public abstract class AbstractCLITaskFactory extends AbstractTaskFactory implements CLITaskFactory {
	protected AppGlobals appGlobals;
	
	public abstract TaskIterator createTaskIterator();
	public abstract String getCommandName();
	public abstract String getCommandDescription();

	public AbstractCLITaskFactory(
			AppGlobals appGlobals){
		this.appGlobals = appGlobals;
	}
			
	public Properties getProperties(){
		Properties p = new Properties();
		p.setProperty(ServiceProperties.COMMAND_NAMESPACE, CyActivator.APP_NAMESPACE);
		p.setProperty(ServiceProperties.COMMAND,getCommandName());
		p.setProperty(ServiceProperties.COMMAND_DESCRIPTION,getCommandDescription());
		return p;
	}
}
