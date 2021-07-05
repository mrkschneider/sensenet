package com.tcb.sensenet.internal.meta;

import com.tcb.cytoscape.cyLib.cytoApiWrappers.CyNetworkAdapter;
import com.tcb.cytoscape.cyLib.cytoApiWrappers.CyRootNetworkAdapter;

public class MetaContext {
	public CyRootNetworkAdapter rootNetwork;
	public CyNetworkAdapter network;

	public MetaContext(CyRootNetworkAdapter rootNetwork, CyNetworkAdapter network){
		this.rootNetwork = rootNetwork;
		this.network = network;
	}
}
