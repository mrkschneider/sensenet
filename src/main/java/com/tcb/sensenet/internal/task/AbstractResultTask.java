package com.tcb.sensenet.internal.task;

import org.cytoscape.work.AbstractTask;
import org.cytoscape.work.TaskMonitor;

import com.tcb.sensenet.internal.util.CancelledException;
import com.tcb.sensenet.internal.util.ObjMap;

public abstract class AbstractResultTask extends AbstractTask {
		
	protected ObjMap results;
	
	public abstract ObjMap start(TaskMonitor taskMon) throws Exception;

	public AbstractResultTask(ObjMap results) {
		this.results = results;
	}
			
	@Override
	public void run(TaskMonitor taskMon) throws Exception {
		this.results = start(taskMon);
	}
	
	public void cancel() throws CancelledException {
		throw new CancelledException("Task cancelled");
	}
	
	public ObjMap getResults() {
		return results;
	}
}
