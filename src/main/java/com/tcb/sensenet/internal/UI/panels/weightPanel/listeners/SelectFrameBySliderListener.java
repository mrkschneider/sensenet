package com.tcb.sensenet.internal.UI.panels.weightPanel.listeners;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.tcb.sensenet.internal.UI.panels.weightPanel.SelectFrameSlider;
import com.tcb.sensenet.internal.aggregation.aggregators.edges.TimelineWeightMethod;
import com.tcb.sensenet.internal.app.AppColumns;
import com.tcb.sensenet.internal.app.AppGlobals;
import com.tcb.sensenet.internal.events.FrameSetEvent;
import com.tcb.sensenet.internal.events.FrameSetRecord;
import com.tcb.sensenet.internal.meta.network.MetaNetwork;


public class SelectFrameBySliderListener implements ChangeListener {

	private AppGlobals appGlobals;
	private SelectFrameSlider slider;
	
	public SelectFrameBySliderListener(SelectFrameSlider slider, 
			AppGlobals appGlobals){
		this.slider = slider;
		this.appGlobals = appGlobals;
	}
	
	
	@Override
	public void stateChanged(ChangeEvent e) {
		MetaNetwork metaNetwork = appGlobals.state.metaNetworkManager.getCurrentMetaNetwork();
		if(listenerShouldBreak(metaNetwork)) return;
		Integer selectedFrame = slider.getValue();
		
		FrameSetRecord record = new FrameSetRecord(metaNetwork,selectedFrame);
		
		appGlobals.eventHelper.fireEvent(new FrameSetEvent(record));
	}
	
	private boolean listenerShouldBreak(MetaNetwork metaNetwork){
		if(slider.isSilent()) return true;
		if(slider.getValueIsAdjusting()) return true;
		if(Integer.valueOf(slider.getValue()).equals(
				metaNetwork.getHiddenDataRow()
				.get(AppColumns.SELECTED_FRAME, Integer.class))) return true;
		if(!TimelineWeightMethod.valueOf(
						metaNetwork.getHiddenDataRow()
						.get(AppColumns.AGGREGATION_MODE, String.class))
				.equals(TimelineWeightMethod.SINGLE_FRAME)) return true;
		return false;
	}

}
