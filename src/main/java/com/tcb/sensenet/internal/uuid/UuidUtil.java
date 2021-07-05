package com.tcb.sensenet.internal.uuid;

import java.util.Optional;
import java.util.UUID;

import com.tcb.sensenet.internal.app.AppColumns;
import com.tcb.cytoscape.cyLib.cytoApiWrappers.CyNetworkAdapter;
import com.tcb.cytoscape.cyLib.cytoApiWrappers.CyRowAdapter;
import com.tcb.cytoscape.cyLib.cytoApiWrappers.CyTableAdapter;

public class UuidUtil {
	
	public static UUID createUuid(){
		return UUID.randomUUID();
	}
	
	public static void setUuid(CyNetworkAdapter network, UUID uuid){
		CyTableAdapter table = getTable(network);
		table.createColumn(AppColumns.UUID, String.class, false);
		CyRowAdapter row = table.getRow(network.getSUID());
		row.set(AppColumns.UUID, uuid.toString());
	}

	
	public static Optional<UUID> getUuid(CyNetworkAdapter network){
		Optional<String> uuid = getTable(network).getRow(network.getSUID()).getMaybe(AppColumns.UUID, String.class);
		if(uuid.isPresent()){
			return Optional.of(UUID.fromString(uuid.get()));
		} else {
			return Optional.empty();
		}
	}
	
	public static Boolean hasUuid(CyNetworkAdapter network){
		return getUuid(network).isPresent();
	}
	
	private static CyTableAdapter getTable(CyNetworkAdapter network){
		return network.getHiddenNetworkTable();
	}
}
