package com.tcb.sensenet.internal.meta.network;

import java.util.List;
import java.util.stream.Collectors;

import org.cytoscape.model.CyEdge;
import org.cytoscape.model.CyNode;

import com.tcb.sensenet.internal.app.AppColumns;
import com.tcb.sensenet.internal.tree.TreeFactory;
import com.tcb.cytoscape.cyLib.cytoApiWrappers.CyRootNetworkAdapter;
import com.tcb.tree.tree.Tree;




public class MetaNetworkFactoryImpl implements MetaNetworkFactory {
	
	private CyRootNetworkAdapter rootNetwork;
	

	public MetaNetworkFactoryImpl(CyRootNetworkAdapter rootNetwork){
		this.rootNetwork = rootNetwork;
	}
	
	@Override
	public MetaNetwork create() {
		List<CyNode> nodes = rootNetwork.getNodeList().stream()
				.filter(n -> true != rootNetwork.getHiddenRow(n).get(AppColumns.IS_METANODE, Boolean.class))
				.collect(Collectors.toList());
		List<CyNode> metanodes = nodes.stream()
				.map(n -> rootNetwork.getHiddenRow(n).get(AppColumns.METANODE_SUID, Long.class))
				.map(s -> rootNetwork.getNode(s))
				.collect(Collectors.toList());
		List<CyEdge> edges = rootNetwork.getEdgeList();
		Tree tree = new TreeFactory().create(rootNetwork, nodes, metanodes, edges);
		MetaNetwork metaNetwork = new CachingMetaNetworkImpl(rootNetwork, tree);
		return metaNetwork;
	}
			
}

