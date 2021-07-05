package com.tcb.sensenet.internal.meta.view;

import org.cytoscape.event.CyEventHelper;

import com.tcb.sensenet.internal.meta.network.MetaNetwork;
import com.tcb.cytoscape.cyLib.cytoApiWrappers.CyNetworkViewManagerAdapter;

public class MetaNetworkViewFactory {

	private CyEventHelper eventHelper;
	private CyNetworkViewManagerAdapter viewManager;

	public MetaNetworkViewFactory(CyEventHelper eventHelper,
			CyNetworkViewManagerAdapter viewManager){
		this.eventHelper = eventHelper;
		this.viewManager = viewManager;
	}
	
	public MetaNetworkView createMetaNetworkView(MetaNetwork metaNetwork){
		return new MetaNetworkView(metaNetwork,eventHelper,viewManager);
	}
	
}
