package com.tcb.sensenet.internal.path;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.cytoscape.model.CyNode;

import com.tcb.cytoscape.cyLib.cytoApiWrappers.CyNetworkAdapter;
import com.tcb.cytoscape.cyLib.data.DefaultColumns;

public class NodeNamePrinter {
	private CyNetworkAdapter network;

	public NodeNamePrinter(CyNetworkAdapter network) {
		this.network = network;
	}
	
	public String toString(List<CyNode> nodes) {
		return nodes.stream()
				.map(n -> network.getRow(n).get(DefaultColumns.SHARED_NAME, String.class))
				.collect(Collectors.joining(","));
	}
	
	public String toString(CyNode node){
		return toString(Arrays.asList(node));
	}
}
