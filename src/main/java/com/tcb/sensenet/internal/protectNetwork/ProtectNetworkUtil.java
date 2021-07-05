package com.tcb.sensenet.internal.protectNetwork;

import com.tcb.sensenet.internal.app.AppColumns;
import com.tcb.cytoscape.cyLib.cytoApiWrappers.CyNetworkAdapter;
import com.tcb.cytoscape.cyLib.cytoApiWrappers.CyTableAdapter;

public class ProtectNetworkUtil {
	
	public static Boolean isProtectNetwork(CyNetworkAdapter network){
		CyTableAdapter networkTable = getTable(network);
		if(!networkTable.columnExists(AppColumns.IS_PROTECTION_NETWORK)) return false;
		return networkTable
				.getRow(network.getSUID())
				.getMaybe(AppColumns.IS_PROTECTION_NETWORK, Boolean.class)
				.orElse(false);
	}
	
	public static void setAsProtectNetwork(CyNetworkAdapter network){
		CyTableAdapter networkTable = getTable(network);
		networkTable.createColumn(AppColumns.IS_PROTECTION_NETWORK, Boolean.class, false);
		networkTable.getRow(network.getSUID()).set(AppColumns.IS_PROTECTION_NETWORK, true);
	}
	
	private static CyTableAdapter getTable(CyNetworkAdapter network){
		return network.getHiddenNetworkTable();
	}
}
