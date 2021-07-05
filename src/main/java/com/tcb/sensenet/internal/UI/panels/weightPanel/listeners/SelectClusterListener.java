package com.tcb.sensenet.internal.UI.panels.weightPanel.listeners;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JComboBox;

import org.cytoscape.work.TaskIterator;

import com.tcb.cluster.Cluster;
import com.tcb.sensenet.internal.aggregation.methods.timeline.FrameWeightMethod;
import com.tcb.sensenet.internal.app.AppColumns;
import com.tcb.sensenet.internal.app.AppGlobals;
import com.tcb.sensenet.internal.meta.network.MetaNetwork;
import com.tcb.sensenet.internal.task.weighting.factories.ActivateClusterWeightingTaskFactory;


public class SelectClusterListener implements ItemListener {

	private AppGlobals appGlobals;
	private JComboBox<Integer> selectedClusterBox;


	public SelectClusterListener(
			JComboBox<Integer> selectedClusterBox,
			AppGlobals appGlobals){
		this.appGlobals = appGlobals;
		this.selectedClusterBox = selectedClusterBox;
	}
	
	
	public void itemStateChanged(ItemEvent e) {
		if(e.getSource()!=selectedClusterBox) return;
		if(e.getStateChange()!=ItemEvent.SELECTED) return;
		MetaNetwork metaNetwork = appGlobals.state.metaNetworkManager.getCurrentMetaNetwork();
		
		Integer selectedClusterIndex = (Integer) selectedClusterBox.getSelectedItem();
		Cluster clusterSelection = appGlobals.state.clusteringStoreManager.get(metaNetwork)
				.get(selectedClusterIndex);
		
		FrameWeightMethod lastWeightMethod = 
				FrameWeightMethod.valueOf(
						metaNetwork.getHiddenDataRow()
						.get(AppColumns.METATIMELINE_TYPE, String.class));
								
		TaskIterator tasks = new TaskIterator();
		
		tasks.append(new ActivateClusterWeightingTaskFactory(
				appGlobals, 
				lastWeightMethod, selectedClusterIndex, clusterSelection)
				.createTaskIterator());
		
		appGlobals.synTaskManager.execute(tasks);
		
	}
	
}
