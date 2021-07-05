package com.tcb.sensenet.internal.tree;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.cytoscape.model.CyEdge;
import org.cytoscape.model.CyIdentifiable;
import org.cytoscape.model.CyNode;

import com.tcb.cytoscape.cyLib.cytoApiWrappers.CyRootNetworkAdapter;
import com.tcb.tree.edge.Edge;
import com.tcb.tree.edge.EdgeImpl;
import com.tcb.tree.node.Node;
import com.tcb.tree.node.NodeImpl;
import com.tcb.tree.tree.Tree;
import com.tcb.tree.tree.TreeImpl;

public class TreeFactory {

	public Tree create(CyRootNetworkAdapter rootNetwork, List<CyNode> subNodes, List<CyNode> metaNodes, List<CyEdge> edges){
		Tree tree = TreeImpl.create(createNode(rootNetwork.getAdaptedNetwork()));
		Node root = tree.getRoot();
		
		Set<CyNode> metaNodeSet = new HashSet<>(metaNodes);
		
		for(CyNode metaNode:metaNodeSet){
			tree.addNode(createNode(metaNode), root);
			}

		for(int i=0;i<subNodes.size();i++){
			CyNode node = subNodes.get(i);
			CyNode parent = metaNodes.get(i);
			tree.addNode(createNode(node),tree.getNode(parent.getSUID()));
		}
		
		for(CyEdge edge:edges){
			tree.addEdge(createEdge(edge,tree));
		}
		return tree;
	}
	
	private Node createNode(CyIdentifiable cyId){
		return NodeImpl.create(cyId.getSUID());
	}
	
	private Edge createEdge(CyEdge edge, Tree tree){
		return EdgeImpl.create(
				edge.getSUID(),
				tree.getNode(edge.getSource().getSUID()),
				tree.getNode(edge.getTarget().getSUID()));
	}
}
