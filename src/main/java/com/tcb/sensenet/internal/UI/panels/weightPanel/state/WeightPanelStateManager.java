package com.tcb.sensenet.internal.UI.panels.weightPanel.state;

import java.util.Optional;

import com.tcb.sensenet.internal.UI.panels.weightPanel.WeightPanel;
import com.tcb.sensenet.internal.aggregation.methods.timeline.FrameWeightMethod;
import com.tcb.sensenet.internal.app.AppColumns;
import com.tcb.sensenet.internal.meta.network.MetaNetwork;
import com.tcb.sensenet.internal.meta.network.MetaNetworkManager;
import com.tcb.sensenet.internal.util.KeyButtonGroup;
import com.tcb.cytoscape.cyLib.UI.state.AbstractStateManager;
import com.tcb.cytoscape.cyLib.data.Columns;

public class WeightPanelStateManager extends AbstractStateManager<WeightPanel> {

	private MetaNetworkManager metaNetworkManager;

	public WeightPanelStateManager(MetaNetworkManager metaNetworkManager){
		this.metaNetworkManager = metaNetworkManager;
	}
	
	
	@Override
	public void updateState() {
		if(!metaNetworkManager.currentNetworkBelongsToMetaNetwork()) return;
		
		MetaNetwork metaNetwork = metaNetworkManager.getCurrentMetaNetwork();
		
		Optional<String> lastWeightMethodEntry = 
				metaNetwork.getHiddenDataRow().getMaybe(AppColumns.METATIMELINE_TYPE, String.class);
		if(!lastWeightMethodEntry.isPresent()) return;
		
		WeightPanel panel = getRegisteredObject();
		FrameWeightMethod lastWeightMethod = FrameWeightMethod.valueOf(lastWeightMethodEntry.get());
		
		KeyButtonGroup selectedWeightMethodButtonGroup = 
				panel.getSelectWeightModeButtonGroup();
		setSelectedWeightMethod(selectedWeightMethodButtonGroup, lastWeightMethod);
		
		Double edgeCutoff = metaNetwork.getHiddenDataRow().getMaybe(
				AppColumns.CUTOFF_VALUE, Double.class)
				.orElse(0d);
		
		Optional<String> cutoffColumnEntry = 
				metaNetwork.getHiddenDataRow().getMaybe(AppColumns.CUTOFF_COLUMN, String.class);
		Columns cutoffColumn = null;
		if(cutoffColumnEntry.isPresent()){
			cutoffColumn = AppColumns.valueOf(cutoffColumnEntry.get());
		}
		
		panel.setEdgeCutoff(edgeCutoff);
		panel.setCutoffColumn(cutoffColumn);
		
	}

	
	public void setSelectedWeightMethod(KeyButtonGroup group, FrameWeightMethod method){
		group.setSelected(method.name(),true);
	}
	
	
	
	
	

}
