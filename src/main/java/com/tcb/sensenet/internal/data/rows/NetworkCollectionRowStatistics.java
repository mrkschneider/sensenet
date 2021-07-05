package com.tcb.sensenet.internal.data.rows;

import java.util.Collection;
import java.util.List;

import org.cytoscape.model.CyIdentifiable;

import com.google.common.collect.ImmutableList;
import com.tcb.cytoscape.cyLib.cytoApiWrappers.CyNetworkAdapter;
import com.tcb.cytoscape.cyLib.cytoApiWrappers.CyRowAdapter;

public class NetworkCollectionRowStatistics {
	private Iterable<CyNetworkAdapter> networks;

	public NetworkCollectionRowStatistics(Iterable<CyNetworkAdapter> networks){
		this.networks = networks;
	}
	
	public List<CyRowAdapter> getEdgeRows(Collection<? extends CyIdentifiable> cyIds){		
		ImmutableList.Builder<CyRowAdapter> rows = ImmutableList.builder();
		for(CyNetworkAdapter network:networks){
			for(CyIdentifiable cyId:cyIds){
				Long suid = cyId.getSUID();
				if(network.getNode(suid)!=null || network.getEdge(suid)!=null){
				CyRowAdapter row = network.getRow(cyId);
				rows.add(row);
				}
			}
		}
		return rows.build();
	}
}
