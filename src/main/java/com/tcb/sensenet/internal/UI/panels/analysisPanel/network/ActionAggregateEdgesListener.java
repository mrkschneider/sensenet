package com.tcb.sensenet.internal.UI.panels.analysisPanel.network;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import org.cytoscape.work.TaskIterator;

import com.tcb.sensenet.internal.app.AppGlobals;
import com.tcb.sensenet.internal.task.aggregation.MetaTimelineAggregationTaskConfig;
import com.tcb.sensenet.internal.task.aggregation.factories.ActionMetaTimelineAggregationTaskFactory;
import com.tcb.sensenet.internal.util.ObjMap;
import com.tcb.cytoscape.cyLib.cytoApiWrappers.CyNetworkAdapter;

public class ActionAggregateEdgesListener implements ActionListener {

	private AppGlobals appGlobals;
	private MetaTimelineAggregationTaskConfig config;

	public ActionAggregateEdgesListener(AppGlobals appGlobals,
			MetaTimelineAggregationTaskConfig config) {
		this.appGlobals = appGlobals;
		this.config = config;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		CyNetworkAdapter network = appGlobals.applicationManager.getCurrentNetwork();
		if(listenerShouldBreak(network)) return;
		
		ObjMap results = new ObjMap();
		
		TaskIterator tasks = new ActionMetaTimelineAggregationTaskFactory(appGlobals)
				.createTaskIterator(results, config);
		
        appGlobals.taskManager.execute(tasks);          
		
	}
		
	private boolean listenerShouldBreak(CyNetworkAdapter network){
		if(!appGlobals.state.metaNetworkManager.belongsToMetaNetwork(network)) return true;
		return false;
	}

}
