package com.tcb.sensenet.internal.UI.panels.weightPanel.state;

import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import javax.swing.JComboBox;

import com.google.common.collect.ImmutableList;
import com.tcb.sensenet.internal.UI.panels.weightPanel.ClusterSelectionPanel;
import com.tcb.sensenet.internal.analysis.cluster.ClusteringStoreManager;
import com.tcb.sensenet.internal.app.AppColumns;
import com.tcb.sensenet.internal.app.AppGlobals;
import com.tcb.sensenet.internal.meta.network.MetaNetwork;
import com.tcb.sensenet.internal.meta.network.MetaNetworkManager;
import com.tcb.cytoscape.cyLib.UI.state.AbstractStateManager;

public class SelectedClusterPanelStateManager extends AbstractStateManager<ClusterSelectionPanel> {

	private MetaNetworkManager metaNetworkManager;
	private ClusteringStoreManager clusteringStoreManager;
	
	public SelectedClusterPanelStateManager(
			MetaNetworkManager metaNetworkManager,
			ClusteringStoreManager clusteringStoreManager){
		this.metaNetworkManager = metaNetworkManager;
		this.clusteringStoreManager = clusteringStoreManager;
	}
	
	@Override
	public void updateState() {
		if(!metaNetworkManager.currentNetworkBelongsToMetaNetwork()) return;
		
		MetaNetwork metaNetwork = metaNetworkManager.getCurrentMetaNetwork();
						
		ClusterSelectionPanel panel = getRegisteredObject();
		if(!panel.isVisible()) return;		
		
		updateClusterSelections(panel, metaNetwork);
		//updateFrameTextField(panel,network);
		
	}
	
	private synchronized void updateClusterSelections(ClusterSelectionPanel panel, MetaNetwork metaNetwork){
		JComboBox<Integer> selectionBox = panel.getClusterSelectionBox();
		
		Optional<Integer> selectedClusterIndex = metaNetwork.getHiddenDataRow()
				.getMaybe(AppColumns.SELECTED_CLUSTER_INDEX, Integer.class);
		List<Integer> centroids = clusteringStoreManager
				.get(metaNetwork).stream()
				.map(c -> c.getCentroid())
				.map(s -> Integer.valueOf(s))
				.collect(ImmutableList.toImmutableList());
				
		List<ItemListener> listeners = new ArrayList<ItemListener>();
		Stream.of(selectionBox.getItemListeners()).forEach(l -> listeners.add(l));
		
		listeners.forEach(l -> selectionBox.removeItemListener(l));
		selectionBox.removeAllItems();
				
		for(int i=0;i<centroids.size();i++){
			selectionBox.addItem(i);
		}

		if(selectedClusterIndex.isPresent()){
			selectionBox.setSelectedIndex(selectedClusterIndex.get());
		} else {
			selectionBox.setSelectedIndex(0);
		}
		
		listeners.forEach(l -> selectionBox.addItemListener(l));
		
	}
		
	
	
}
