package com.tcb.sensenet.internal.UI.panels.weightPanel.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import org.cytoscape.work.TaskIterator;

import com.tcb.sensenet.internal.aggregation.methods.timeline.FrameWeightMethod;
import com.tcb.sensenet.internal.app.AppColumns;
import com.tcb.sensenet.internal.app.AppGlobals;
import com.tcb.sensenet.internal.meta.network.MetaNetwork;
import com.tcb.sensenet.internal.task.weighting.factories.ActivateAverageFrameWeightingTaskFactory;


public class ActionActivateTimelineWeightingListener implements ActionListener {

	private AppGlobals appGlobals;
	
	public ActionActivateTimelineWeightingListener(AppGlobals appGlobals){
		this.appGlobals = appGlobals;
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		MetaNetwork metaNetwork = appGlobals.state.metaNetworkManager.getCurrentMetaNetwork();

		FrameWeightMethod method = FrameWeightMethod.valueOf(
				metaNetwork.getHiddenDataRow().get(AppColumns.METATIMELINE_TYPE, String.class));
		
		TaskIterator tasks = new TaskIterator();
		
		tasks.append(
				new ActivateAverageFrameWeightingTaskFactory(
				appGlobals,method).createTaskIterator());
				
		appGlobals.taskManager.execute(tasks);
	}
	

	


}
