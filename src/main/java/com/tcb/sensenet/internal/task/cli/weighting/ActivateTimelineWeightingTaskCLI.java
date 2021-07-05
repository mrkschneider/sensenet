package com.tcb.sensenet.internal.task.cli.weighting;

import org.cytoscape.work.TaskIterator;
import org.cytoscape.work.Tunable;

import com.tcb.sensenet.internal.aggregation.methods.timeline.FrameWeightMethod;
import com.tcb.sensenet.internal.app.AppGlobals;
import com.tcb.sensenet.internal.task.cli.AbstractWrappedTask;
import com.tcb.sensenet.internal.task.weighting.factories.ActivateAverageFrameWeightingTaskFactory;
import com.tcb.cytoscape.cyLib.util.NullUtil;

public class ActivateTimelineWeightingTaskCLI extends AbstractWrappedTask {
	
	@Tunable(description="Frame weight")
	public String weightMethod;
	
	public ActivateTimelineWeightingTaskCLI(AppGlobals appGlobals){
		super(appGlobals);
	}
	
	@Override
	public TaskIterator createWrappedTasks() {
		NullUtil.requireNonNull(weightMethod, "Metatimeline Type");
		
		FrameWeightMethod weightMethodV = FrameWeightMethod.valueOfCLI
				(weightMethod);
		
		return new ActivateAverageFrameWeightingTaskFactory(appGlobals,
				weightMethodV)
		.createTaskIterator();
	}
	
}
