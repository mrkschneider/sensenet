package com.tcb.sensenet.internal.init.table;

import org.cytoscape.model.CyNetwork;
import org.cytoscape.model.CyNode;

import com.tcb.sensenet.internal.app.AppColumns;
import com.tcb.sensenet.internal.init.Initializer;
import com.tcb.cytoscape.cyLib.cytoApiWrappers.CyNetworkAdapter;
import com.tcb.cytoscape.cyLib.cytoApiWrappers.CyTableAdapter;

public class NodeTableInitializer implements Initializer {
	
	private CyNetworkAdapter network;

	public NodeTableInitializer(CyNetworkAdapter network){
		this.network = network;
		
	}
		
	public void init(){
		CyTableAdapter sharedTable = network.getDefaultNodeTable();
		
		sharedTable.createColumn(AppColumns.CENTRALITY, Double.class, false);
		sharedTable.createColumn(AppColumns.VISITED, Double.class, false);
	}
		
	
}
