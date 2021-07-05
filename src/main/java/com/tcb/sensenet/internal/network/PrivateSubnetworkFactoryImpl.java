package com.tcb.sensenet.internal.network;

import org.cytoscape.model.SavePolicy;

import com.tcb.sensenet.internal.app.AppGlobals;
import com.tcb.cytoscape.cyLib.cytoApiWrappers.CyNetworkAdapter;
import com.tcb.cytoscape.cyLib.cytoApiWrappers.CyRootNetworkAdapter;

public class PrivateSubnetworkFactoryImpl implements PrivateSubnetworkFactory {

	private AppGlobals appGlobals;

	public PrivateSubnetworkFactoryImpl(AppGlobals appGlobals) {
		this.appGlobals = appGlobals;
	}
	
	@Override
	public CyNetworkAdapter create(CyNetworkAdapter network) {
		CyRootNetworkAdapter rootNetwork = appGlobals.rootNetworkManager.getRootNetwork(network);
		CyNetworkAdapter net = rootNetwork.addSubNetwork(network.getNodeList(), network.getEdgeList(),
				SavePolicy.DO_NOT_SAVE);
		return net;
	}
	
	@Override
	public void destroy(CyNetworkAdapter network) {
		CyRootNetworkAdapter rootNetwork = appGlobals.rootNetworkManager.getRootNetwork(network);
		rootNetwork.removeSubNetwork(network);
		//appGlobals.networkManager.destroyNetwork(network);
	}
}
