package com.tcb.sensenet.internal.task.cli;

import org.cytoscape.work.AbstractTask;
import org.cytoscape.work.Task;
import org.cytoscape.work.TaskIterator;
import org.cytoscape.work.TaskMonitor;

import com.tcb.sensenet.internal.app.AppGlobals;
import com.tcb.cytoscape.cyLib.task.cli.CLITask;

public abstract class AbstractWrappedTask extends AbstractTask implements CLITask {

	protected AppGlobals appGlobals;

	public abstract TaskIterator createWrappedTasks();
	
	public AbstractWrappedTask(AppGlobals appGlobals){
		this.appGlobals = appGlobals;
	}
	
	@Override
	public void run(TaskMonitor taskMon){
		TaskIterator it = new TaskIterator();
		it.append(createWrappedTasks());
		//appGlobals.synTaskManager.execute(it);
		it.forEachRemaining(t -> {
				try {
					t.run(taskMon);
				} catch (Exception e) {
					throw new RuntimeException(e);
				}
			});	
	}
	
}
