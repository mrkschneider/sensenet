package com.tcb.sensenet.internal.init.row;

import com.tcb.sensenet.internal.app.AppColumns;
import com.tcb.sensenet.internal.init.ImportConfig;
import com.tcb.sensenet.internal.init.Initializer;
import com.tcb.cytoscape.cyLib.cytoApiWrappers.CyNetworkAdapter;
import com.tcb.cytoscape.cyLib.cytoApiWrappers.CyRootNetworkAdapter;
import com.tcb.cytoscape.cyLib.cytoApiWrappers.CyRowAdapter;
import com.tcb.cytoscape.cyLib.cytoApiWrappers.CyTableAdapter;
import com.tcb.cytoscape.cyLib.data.DefaultColumns;

public class NetworkSharedRowInitializer implements Initializer {

	private CyNetworkAdapter network;
	private ImportConfig config;

	public NetworkSharedRowInitializer(
			ImportConfig importConfig,
			CyNetworkAdapter network) {
		this.config = importConfig;
		this.network = network;
	}
	
	@Override
	public void init() {
		CyTableAdapter sharedTable = network.getDefaultNetworkTable();
		
		CyRowAdapter sharedRow = sharedTable.getRow(network.getSUID());

		sharedRow.set(DefaultColumns.SHARED_NAME, config.getNetworkName());
		sharedRow.set(DefaultColumns.NAME, config.getNetworkName());
	}

}
