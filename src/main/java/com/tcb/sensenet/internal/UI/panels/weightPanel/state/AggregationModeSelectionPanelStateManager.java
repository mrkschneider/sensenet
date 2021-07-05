package com.tcb.sensenet.internal.UI.panels.weightPanel.state;

import java.util.Optional;

import com.tcb.sensenet.internal.UI.panels.weightPanel.AggregationModeSelectionPanel;
import com.tcb.sensenet.internal.aggregation.aggregators.edges.TimelineWeightMethod;
import com.tcb.sensenet.internal.analysis.cluster.ClusteringStoreManager;
import com.tcb.sensenet.internal.app.AppColumns;
import com.tcb.sensenet.internal.app.AppGlobals;
import com.tcb.sensenet.internal.meta.network.MetaNetwork;
import com.tcb.sensenet.internal.meta.network.MetaNetworkManager;
import com.tcb.sensenet.internal.util.KeyButtonGroup;
import com.tcb.cytoscape.cyLib.UI.state.AbstractStateManager;

public class AggregationModeSelectionPanelStateManager extends AbstractStateManager<AggregationModeSelectionPanel> {

	private MetaNetworkManager metaNetworkManager;
	
	public AggregationModeSelectionPanelStateManager(
			MetaNetworkManager metaNetworkManager){
		this.metaNetworkManager = metaNetworkManager;
	}
	
	@Override
	public void updateState() {
		if(!metaNetworkManager.currentNetworkBelongsToMetaNetwork()) return;
		
		MetaNetwork metaNetwork = metaNetworkManager.getCurrentMetaNetwork();
					
		AggregationModeSelectionPanel panel = getRegisteredObject();
		
		updateAggregationModeSelectionButtons(metaNetwork, panel);
	}
	
	private void updateAggregationModeSelectionButtons(
			MetaNetwork metaNetwork,
			AggregationModeSelectionPanel panel){
		
		Integer timelineLength = metaNetwork.getHiddenDataRow()
				.get(AppColumns.TIMELINE_LENGTH, Integer.class);
		TimelineWeightMethod aggregationMode = 
				TimelineWeightMethod.valueOf(
						metaNetwork.getHiddenDataRow().get(
								AppColumns.AGGREGATION_MODE, String.class));
		Boolean clustersPresent = metaNetwork.getHiddenDataRow()
				.getMaybe(AppColumns.CLUSTERING_MODE, String.class).isPresent();
		
		
		KeyButtonGroup aggregationModeButtonGroup = panel.getAggregationModeButtonGroup();
		
		if(!clustersPresent){
			panel.getClusterAggregationButton().setEnabled(false);
		}
				
		if(timelineLength <= 1){
			panel.getTimepointAggregationButton().setEnabled(false);
			panel.getClusterAggregationButton().setEnabled(false);
		}
		
		aggregationModeButtonGroup.setSelected(aggregationMode.name(), true);
	}
}
