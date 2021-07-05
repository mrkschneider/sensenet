package com.tcb.sensenet.internal.task.create;

import org.cytoscape.work.AbstractTask;
import org.cytoscape.work.TaskMonitor;
import org.cytoscape.work.Tunable;

public class TestTask extends AbstractTask {
	
	@Tunable
	public String arg="def";
	
	@Tunable
	public Double a = 0.1;
	
	@Override
	public void run(TaskMonitor arg0) throws Exception {
		System.out.println(arg);
		System.out.println(a);
	}
	

}
