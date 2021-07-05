package com.tcb.sensenet.internal.init.groups.edges;

import java.util.Optional;

import org.cytoscape.model.CyEdge;

import com.tcb.cytoscape.cyLib.cytoApiWrappers.CyRootNetworkAdapter;
import com.tcb.cytoscape.cyLib.data.DefaultColumns;

public class StandardMetaEdgeDefinition implements MetaEdgeDefinition {

	private String interactionType;

	public StandardMetaEdgeDefinition(String interactionType){
		this.interactionType = interactionType;
	}
	
	@Override
	public boolean test(CyEdge edge, CyRootNetworkAdapter rootNetwork) {
		Optional<String> interactionTypeString = getInteractionTypeString(edge, rootNetwork);
		if(!interactionTypeString.isPresent()){
			return false;
		}
		return interactionType.equals(interactionTypeString.get());
	}
	
	private Optional<String> getInteractionTypeString(CyEdge edge, CyRootNetworkAdapter rootNetwork){
		Optional<String> interactionTypeString = rootNetwork.getRow(edge)
				.getMaybe(DefaultColumns.SHARED_INTERACTION, String.class);
		return interactionTypeString;
	}

	@Override
	public void setEdgeValid(CyEdge edge, CyRootNetworkAdapter rootNetwork) {
		rootNetwork.getRow(edge).set(DefaultColumns.SHARED_INTERACTION, interactionType);
	}

}
