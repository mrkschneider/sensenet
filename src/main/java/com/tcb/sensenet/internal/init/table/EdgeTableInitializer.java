package com.tcb.sensenet.internal.init.table;

import org.cytoscape.model.CyEdge;
import org.cytoscape.model.CyNetwork;

import com.tcb.sensenet.internal.app.AppColumns;
import com.tcb.sensenet.internal.init.Initializer;
import com.tcb.cytoscape.cyLib.cytoApiWrappers.CyNetworkAdapter;
import com.tcb.cytoscape.cyLib.cytoApiWrappers.CyTableAdapter;

public class EdgeTableInitializer implements Initializer {
	
	private CyNetworkAdapter network;

	public EdgeTableInitializer(CyNetworkAdapter network){
		this.network = network;
	}
		
	public void init(){
		CyTableAdapter hiddenTable = network.getTable(CyEdge.class, CyNetwork.HIDDEN_ATTRS);
		
		hiddenTable.createColumn(AppColumns.SELECTION_TIME, String.class, false);
	}
		
	
}
