package com.tcb.sensenet.internal.init.row;

import com.tcb.sensenet.internal.app.AppColumns;
import com.tcb.sensenet.internal.init.ImportConfig;
import com.tcb.sensenet.internal.init.Initializer;
import com.tcb.cytoscape.cyLib.cytoApiWrappers.CyRootNetworkAdapter;
import com.tcb.cytoscape.cyLib.cytoApiWrappers.CyRowAdapter;
import com.tcb.cytoscape.cyLib.cytoApiWrappers.CyTableAdapter;
import com.tcb.cytoscape.cyLib.data.DefaultColumns;

public class RootNetworkSharedRowInitializer implements Initializer {

	private CyRootNetworkAdapter rootNetwork;
	private ImportConfig config;

	public RootNetworkSharedRowInitializer(
			ImportConfig importConfig,
			CyRootNetworkAdapter rootNetwork) {
		this.config = importConfig;
		this.rootNetwork = rootNetwork;
	}
	
	@Override
	public void init() {
		CyTableAdapter sharedTable = rootNetwork.getDefaultNetworkTable();
		
		CyRowAdapter sharedRow = sharedTable.getRow(rootNetwork.getSUID());
		sharedRow.set(DefaultColumns.SHARED_NAME, "#" + config.getNetworkName());
		sharedRow.set(DefaultColumns.NAME, "#" + config.getNetworkName());
		
	}

}
