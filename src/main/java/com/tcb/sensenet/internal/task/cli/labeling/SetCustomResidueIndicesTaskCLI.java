package com.tcb.sensenet.internal.task.cli.labeling;

import org.cytoscape.work.TaskFactory;
import org.cytoscape.work.TaskIterator;
import org.cytoscape.work.Tunable;

import com.tcb.sensenet.internal.app.AppGlobals;
import com.tcb.sensenet.internal.task.cli.AbstractWrappedTask;
import com.tcb.sensenet.internal.task.labeling.SetCustomResidueIndicesTaskConfig;
import com.tcb.sensenet.internal.task.labeling.factories.SetCustomResidueIndicesTaskFactory;

public class SetCustomResidueIndicesTaskCLI extends AbstractWrappedTask {
	
	@Tunable(description="Chain")
	public String chain = "*";
	
	@Tunable(description="First residue index")
	public Integer firstResIndex = 0;
	
	@Tunable(description="Last residue index")
	public Integer lastResIndex = -1;

	@Tunable(description="offset")
	public Integer offset = 0;
	
	@Tunable(description="override residue insert code")
	public String resInsert = null;
	
		
	public SetCustomResidueIndicesTaskCLI(AppGlobals appGlobals) {
		super(appGlobals);
	}

	@Override
	public TaskIterator createWrappedTasks() {		
		SetCustomResidueIndicesTaskConfig config = 
				new SetCustomResidueIndicesTaskConfig(chain,firstResIndex,lastResIndex,offset,
						resInsert);
		
		TaskFactory fac = new SetCustomResidueIndicesTaskFactory(config,appGlobals);
		return fac.createTaskIterator();
	}

	
	
}
