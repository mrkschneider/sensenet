package com.tcb.sensenet.internal.labeling;

import java.util.Arrays;
import java.util.List;

import org.cytoscape.model.CyEdge;
import org.cytoscape.model.CyNode;

import com.tcb.sensenet.internal.app.AppColumns;
import com.tcb.sensenet.internal.init.row.StandardizedEdgeNameGenerator;
import com.tcb.cytoscape.cyLib.cytoApiWrappers.CyRootNetworkAdapter;
import com.tcb.cytoscape.cyLib.cytoApiWrappers.CyRowAdapter;
import com.tcb.cytoscape.cyLib.data.DefaultColumns;

public class StandardizedEdgeNameLabeler implements CyIdentifiableLabeler<CyEdge> {

	protected CyRootNetworkAdapter rootNetwork;

	public StandardizedEdgeNameLabeler(CyRootNetworkAdapter rootNetwork){
		this.rootNetwork = rootNetwork;
	}
	
	@Override
	public String generateLabel(CyEdge edge) {
		CyNode source = edge.getSource();
		CyNode target = edge.getTarget();
		String sourceName = getName(source);
		String targetName = getName(target);
		CyRowAdapter row = rootNetwork.getRow(edge);
		List<String> bridgeNames = row
				.getListMaybe(AppColumns.BRIDGE_NAME, String.class)
				.orElse(Arrays.asList());
		String type = row.get(DefaultColumns.SHARED_INTERACTION, String.class);
		String bridgeName = getBridgeName(bridgeNames);
		return generateLabel(sourceName,targetName,bridgeName,type);		
	}
	
	protected String getName(CyNode node){
		CyRowAdapter row = rootNetwork.getRow(node);
		String name = row.get(DefaultColumns.SHARED_NAME, String.class);
		return name;
	}
	
	protected String getBridgeName(List<String> bridgeNames){
		return String.join("-", bridgeNames);
	}

	protected String generateLabel(String sourceName, String targetName, String bridgeName,
			String interactionType) {
		return new StandardizedEdgeNameGenerator().getName(sourceName, targetName, interactionType, bridgeName);
	}

}
