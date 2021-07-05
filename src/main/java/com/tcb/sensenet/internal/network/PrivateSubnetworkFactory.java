package com.tcb.sensenet.internal.network;

import com.tcb.cytoscape.cyLib.cytoApiWrappers.CyNetworkAdapter;

public interface PrivateSubnetworkFactory {

	CyNetworkAdapter create(CyNetworkAdapter network);

	void destroy(CyNetworkAdapter network);

}