package com.tcb.sensenet.internal.cleanup;



import java.util.List;

import org.cytoscape.model.events.NetworkAboutToBeDestroyedEvent;
import org.cytoscape.model.events.NetworkAboutToBeDestroyedListener;

import com.tcb.sensenet.internal.app.AppGlobals;
import com.tcb.sensenet.internal.meta.network.MetaNetwork;
import com.tcb.cytoscape.cyLib.cytoApiWrappers.CyNetworkAdapter;

public class NetworkDeletedListener implements NetworkAboutToBeDestroyedListener {

	private AppGlobals appGlobals;
	
	public NetworkDeletedListener(AppGlobals appGlobals){
		this.appGlobals = appGlobals;
	}
	
	@Override
	public void handleEvent(NetworkAboutToBeDestroyedEvent e) {
		CyNetworkAdapter network = new CyNetworkAdapter(e.getNetwork());
		appGlobals.state.nodePositionStoreManager.remove(network);
		
		if(!appGlobals.state.metaNetworkManager.belongsToMetaNetwork(network)) return;		
		MetaNetwork metaNetwork = appGlobals.state.metaNetworkManager.get(network);
		List<CyNetworkAdapter> subNetworks = metaNetwork.getSubNetworks();	
		
		if(subNetworks.size() > 1) return; 
				
		appGlobals.state.metaNetworkManager.remove(network);
		appGlobals.metaNetworkViewManager.remove(metaNetwork);
		appGlobals.state.logManager.remove(metaNetwork);
		appGlobals.state.networkSettingsManager.remove(metaNetwork);
		appGlobals.state.timelineManager.remove(metaNetwork);
		
		
		appGlobals.metaTimelineFactoryManager.reset();		
	}

}
