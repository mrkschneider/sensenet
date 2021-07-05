package com.tcb.sensenet.internal.labeling;

import java.util.Arrays;
import java.util.List;

import org.cytoscape.model.CyEdge;
import org.cytoscape.model.CyNode;

import com.tcb.sensenet.internal.app.AppColumns;
import com.tcb.cytoscape.cyLib.cytoApiWrappers.CyRootNetworkAdapter;
import com.tcb.cytoscape.cyLib.cytoApiWrappers.CyRowAdapter;
import com.tcb.cytoscape.cyLib.data.DefaultColumns;

public class DefaultEdgeLabeler extends StandardizedEdgeNameLabeler {

	public DefaultEdgeLabeler(CyRootNetworkAdapter rootNetwork){
		super(rootNetwork);
	}
	
	@Override	
	protected String getName(CyNode node){
		CyRowAdapter row = rootNetwork.getRow(node);
		String name = row.get(AppColumns.LABEL, String.class);
		return name;
	}

}
