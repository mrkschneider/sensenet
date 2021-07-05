package com.tcb.sensenet.internal.UI.panels.weightPanel.listeners;

import org.cytoscape.work.TaskIterator;

import com.tcb.sensenet.internal.aggregation.methods.timeline.FrameWeightMethod;
import com.tcb.sensenet.internal.app.AppColumns;
import com.tcb.sensenet.internal.app.AppGlobals;
import com.tcb.sensenet.internal.events.FrameSetEvent;
import com.tcb.sensenet.internal.events.FrameSetListener;
import com.tcb.sensenet.internal.events.FrameSetRecord;
import com.tcb.sensenet.internal.meta.network.MetaNetwork;
import com.tcb.sensenet.internal.task.weighting.factories.ActivateSingleFrameWeightingTaskFactory;

public class UpdateWeightsWhenFrameSelectedListener implements FrameSetListener {

	private AppGlobals appGlobals;

	public UpdateWeightsWhenFrameSelectedListener(AppGlobals appGlobals){
		this.appGlobals = appGlobals;
	}
	
	@Override
	public void handleEvent(FrameSetEvent e) {
		FrameSetRecord record = e.getSource();
		MetaNetwork metaNetwork = record.getMetaNetwork();
		Integer selectedFrame = record.getFrame();
		
		FrameWeightMethod weightMethod = FrameWeightMethod.valueOf(
				metaNetwork.getHiddenDataRow().get(AppColumns.METATIMELINE_TYPE, String.class));
		
		TaskIterator tasks = new TaskIterator();
		
		tasks.append(new ActivateSingleFrameWeightingTaskFactory(appGlobals, 
				weightMethod, selectedFrame)
			.createTaskIterator());
		
		appGlobals.synTaskManager.execute(tasks);		
	}

}
