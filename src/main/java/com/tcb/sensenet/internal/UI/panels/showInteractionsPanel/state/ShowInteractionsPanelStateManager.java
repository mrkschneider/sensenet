package com.tcb.sensenet.internal.UI.panels.showInteractionsPanel.state;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.tcb.sensenet.internal.UI.panels.showInteractionsPanel.ShowInteractionsPanel;
import com.tcb.sensenet.internal.app.AppColumns;
import com.tcb.sensenet.internal.meta.network.MetaNetwork;
import com.tcb.sensenet.internal.meta.network.MetaNetworkManager;
import com.tcb.cytoscape.cyLib.UI.state.AbstractStateManager;

public class ShowInteractionsPanelStateManager extends AbstractStateManager<ShowInteractionsPanel> {

	private MetaNetworkManager metaNetworkManager;

	public ShowInteractionsPanelStateManager(MetaNetworkManager metaNetworkManager){
		this.metaNetworkManager = metaNetworkManager;
	}
	
	
	@Override
	public void updateState() {
		if(!metaNetworkManager.currentNetworkBelongsToMetaNetwork()) return;
		
		MetaNetwork metaNetwork = metaNetworkManager.getCurrentMetaNetwork();
		
		Optional<List<String>> availableInteractionTypesEntry = 
				metaNetwork.getHiddenDataRow().getListMaybe(AppColumns.AVAILABLE_INTERACTION_TYPES,
						String.class);
		if(!availableInteractionTypesEntry.isPresent()) return;
		Optional<List<String>> selectedInteractionTypesEntry = 
				metaNetwork.getHiddenDataRow().getListMaybe(AppColumns.SELECTED_INTERACTION_TYPES,
						String.class);
		List<String> selectedInteractionTypes = selectedInteractionTypesEntry
				.orElse(new ArrayList<String>());
										
		ShowInteractionsPanel panel = getRegisteredObject();
		
		panel.setDisplayedInteractionTypes(availableInteractionTypesEntry.get());
		panel.setSelectedInteractionTypes(selectedInteractionTypes);
		
	}

	
	
	
	
	

}
