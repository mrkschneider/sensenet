package com.tcb.sensenet.internal.UI.panels.weightPanel.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import org.cytoscape.work.TaskIterator;

import com.tcb.cluster.Cluster;
import com.tcb.sensenet.internal.aggregation.methods.timeline.FrameWeightMethod;
import com.tcb.sensenet.internal.app.AppColumns;
import com.tcb.sensenet.internal.app.AppGlobals;
import com.tcb.sensenet.internal.meta.network.MetaNetwork;
import com.tcb.sensenet.internal.task.weighting.factories.ActivateClusterWeightingTaskFactory;


public class ActionActivateClusterWeightingListener implements ActionListener {

	private AppGlobals appGlobals;

	public ActionActivateClusterWeightingListener(AppGlobals appGlobals){
		this.appGlobals = appGlobals;
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		MetaNetwork metaNetwork = appGlobals.state.metaNetworkManager.getCurrentMetaNetwork();
		

		FrameWeightMethod method = FrameWeightMethod.valueOf(
				metaNetwork.getHiddenDataRow().get(AppColumns.METATIMELINE_TYPE, String.class));
		Integer selectedClusterIndex = 
				metaNetwork.getHiddenDataRow().get(AppColumns.SELECTED_CLUSTER_INDEX, Integer.class);
		Cluster cluster = appGlobals.state.clusteringStoreManager
				.get(metaNetwork).get(selectedClusterIndex);
		
		TaskIterator tasks = new TaskIterator();
		
		tasks.append(
				new ActivateClusterWeightingTaskFactory(
				appGlobals,method,
				selectedClusterIndex, cluster).createTaskIterator());
				
		appGlobals.taskManager.execute(tasks);
	}
	
	
	


}
