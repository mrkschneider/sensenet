package com.tcb.sensenet.internal.UI.panels.appPanel.state;

import javax.swing.JPanel;

import com.tcb.sensenet.internal.UI.panels.appPanel.AppPanel;
import com.tcb.sensenet.internal.UI.state.AlwaysActive;
import com.tcb.sensenet.internal.UI.state.ExclusiveForNormalTimeline;
import com.tcb.sensenet.internal.UI.state.NestedComponentActivator;
import com.tcb.sensenet.internal.meta.network.MetaNetwork;
import com.tcb.sensenet.internal.meta.network.MetaNetworkManager;
import com.tcb.cytoscape.cyLib.UI.state.AbstractStateManager;
import com.tcb.aifgen.importer.TimelineType;

public class AppPanelStateManager extends AbstractStateManager<AppPanel> {
	
	private MetaNetworkManager metaNetworkManager;

	public AppPanelStateManager(MetaNetworkManager metaNetworkManager){
		this.metaNetworkManager = metaNetworkManager;
	}
	
	@Override
	public void updateState() {
		JPanel contentPanel = getRegisteredObject().getContentPanel();
			
		if(!metaNetworkManager.currentNetworkBelongsToMetaNetwork()){
			NestedComponentActivator.setAllEnabled(contentPanel, false);
			NestedComponentActivator.setAllEnabled(contentPanel, true, AlwaysActive.class);
			return;
		} else {
			NestedComponentActivator.setAllEnabled(contentPanel, true);
		}
		
		MetaNetwork metaNetwork = metaNetworkManager.getCurrentMetaNetwork();
		if(metaNetwork.getTimelineType().equals(TimelineType.DIFFERENCE_TIMELINE)){
			NestedComponentActivator.setAllEnabled(contentPanel, false, ExclusiveForNormalTimeline.class);			
		}		
	}
	
	
	
	
}