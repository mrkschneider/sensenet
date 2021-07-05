package com.tcb.sensenet.internal.UI.panels.weightPanel.state;

import com.tcb.sensenet.internal.UI.panels.weightPanel.AggregationModeControlsPanelMaster;
import com.tcb.sensenet.internal.aggregation.aggregators.edges.TimelineWeightMethod;
import com.tcb.sensenet.internal.app.AppColumns;
import com.tcb.sensenet.internal.meta.network.MetaNetwork;
import com.tcb.sensenet.internal.meta.network.MetaNetworkManager;
import com.tcb.cytoscape.cyLib.UI.state.AbstractStateManager;

public class AggregationModeControlsPanelStateManager 
extends AbstractStateManager<AggregationModeControlsPanelMaster> {

	private MetaNetworkManager metaNetworkManager;
	
	public AggregationModeControlsPanelStateManager(
			MetaNetworkManager metaNetworkManager){
		this.metaNetworkManager = metaNetworkManager;
	}
	
	@Override
	public void updateState() {
		if(!metaNetworkManager.currentNetworkBelongsToMetaNetwork()) return;
		
		MetaNetwork metaNetwork = metaNetworkManager.getCurrentMetaNetwork();
				
		AggregationModeControlsPanelMaster panel = getRegisteredObject();
		
		updateSelectionPanel(metaNetwork, panel);
	}
		
	private void updateSelectionPanel(MetaNetwork metaNetwork,
			AggregationModeControlsPanelMaster panel){
		TimelineWeightMethod mode = TimelineWeightMethod.valueOf(
				metaNetwork.getHiddenDataRow().get(AppColumns.AGGREGATION_MODE, String.class));
		panel.showPanel(mode);
	}
		
}
