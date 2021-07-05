package com.tcb.sensenet.internal.UI.panels.weightPanel.state;

import com.tcb.sensenet.internal.UI.panels.weightPanel.FrameSelectionPanel;
import com.tcb.sensenet.internal.aggregation.aggregators.edges.TimelineWeightMethod;
import com.tcb.sensenet.internal.app.AppColumns;
import com.tcb.sensenet.internal.meta.network.MetaNetwork;
import com.tcb.sensenet.internal.meta.network.MetaNetworkManager;
import com.tcb.cytoscape.cyLib.UI.state.AbstractStateManager;

public class SelectedFramePanelStateManager extends AbstractStateManager<FrameSelectionPanel> {

	private MetaNetworkManager metaNetworkManager;
	
	public SelectedFramePanelStateManager(
			MetaNetworkManager metaNetworkManager){
		this.metaNetworkManager = metaNetworkManager;
	}
	
	@Override
	public void updateState() {
		if(!metaNetworkManager.currentNetworkBelongsToMetaNetwork()) return;
		
		MetaNetwork metaNetwork = metaNetworkManager.getCurrentMetaNetwork();
				
		TimelineWeightMethod mode = TimelineWeightMethod.valueOf(
				metaNetwork.getHiddenDataRow().get(AppColumns.AGGREGATION_MODE, String.class));
		
		Integer selectedFrame = metaNetwork.getHiddenDataRow().get(AppColumns.SELECTED_FRAME, Integer.class);
		Integer timelineLength = metaNetwork.getHiddenDataRow().get(AppColumns.TIMELINE_LENGTH, Integer.class);
				
		FrameSelectionPanel panel = getRegisteredObject();
		if(!panel.isVisible()) return;		
		
		
		panel.setSliderBounds(0, timelineLength-1);
				
		switch(mode){
			case AVERAGE_FRAME: 
				panel.getTimelineSlider().setEnabled(false);
				panel.getFrameSelectionTextField().setEnabled(false);
				panel.setSelectedFrameToAll();
				panel.getFrameSelectionTextFieldLabel().setEnabled(false);
				break;
			case SINGLE_FRAME: 
				panel.getTimelineSlider().setEnabled(true);
				panel.getFrameSelectionTextField().setEnabled(true);
				panel.setSelectedFrame(selectedFrame);
				panel.getFrameSelectionTextFieldLabel().setEnabled(true);
				break;
		//$CASES-OMITTED$
		default: throw new IllegalArgumentException("Unknown AggregationModeState");
		}
				
	}
}
