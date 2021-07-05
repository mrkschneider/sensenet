package com.tcb.sensenet.internal.layout;

import com.tcb.sensenet.internal.util.BasicNetworkManager;
import com.tcb.cytoscape.cyLib.cytoApiWrappers.CyNetworkAdapter;

public class NodePositionStoreManager extends BasicNetworkManager<NodePositionStore> {

	@Override
	public NodePositionStore get(CyNetworkAdapter network){
		if(!containsKey(network)) put(network, new NodePositionStore());
		return super.get(network);
	}
}
