package com.tcb.sensenet.internal.labeling;

import org.cytoscape.model.CyNode;

import com.tcb.sensenet.internal.app.AppColumns;
import com.tcb.cytoscape.cyLib.cytoApiWrappers.CyRootNetworkAdapter;
import com.tcb.cytoscape.cyLib.cytoApiWrappers.CyRowAdapter;

public class NodeLabeler implements CyIdentifiableLabeler<CyNode> {

	private CyRootNetworkAdapter rootNetwork;
	private NodeLabelFactory labelFactory;

	public NodeLabeler(CyRootNetworkAdapter rootNetwork, NodeLabelFactory labelFactory){
		this.rootNetwork = rootNetwork;
		this.labelFactory = labelFactory;
	}
	
	@Override
	public String generateLabel(CyNode cyId) {
		CyRowAdapter row = rootNetwork.getRow(cyId);
		
		String name = labelFactory.createLabel(
				row.get(AppColumns.CHAIN, String.class),
				row.get(AppColumns.ALTLOC, String.class),
				row.get(AppColumns.RESNAME, String.class),
				row.get(AppColumns.RESINSERT_LABEL, String.class),
				row.get(AppColumns.RESINDEX_LABEL, Integer.class),
				row.get(AppColumns.MUTATED_RESNAME, String.class),
				row.get(AppColumns.ATOM_NAME, String.class),
				row.get(AppColumns.GROUP_TAG, String.class)
				);
								
		return name;
	}
		
	
}
