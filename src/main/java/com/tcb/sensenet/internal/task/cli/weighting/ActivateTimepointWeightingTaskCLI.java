package com.tcb.sensenet.internal.task.cli.weighting;

import org.cytoscape.work.TaskIterator;
import org.cytoscape.work.Tunable;

import com.tcb.sensenet.internal.aggregation.methods.timeline.FrameWeightMethod;
import com.tcb.sensenet.internal.app.AppGlobals;
import com.tcb.sensenet.internal.task.cli.AbstractWrappedTask;
import com.tcb.sensenet.internal.task.weighting.factories.ActivateSingleFrameWeightingTaskFactory;
import com.tcb.cytoscape.cyLib.util.NullUtil;

public class ActivateTimepointWeightingTaskCLI extends AbstractWrappedTask {
	
	@Tunable(description="Frame weight")
	public String weightMethod;
	
	@Tunable(description="Frame index")
	public Integer frameIndex;
	
	public ActivateTimepointWeightingTaskCLI(AppGlobals appGlobals){
		super(appGlobals);
	}
	
	@Override
	public TaskIterator createWrappedTasks() {
		NullUtil.requireNonNull(weightMethod, "Frame weight");
		NullUtil.requireNonNull(frameIndex, "Frame index");
		
		FrameWeightMethod weightMethodV = FrameWeightMethod.valueOfCLI
				(weightMethod);
		
		return new ActivateSingleFrameWeightingTaskFactory(appGlobals,
				weightMethodV, frameIndex)
		.createTaskIterator();
	}
	
}
