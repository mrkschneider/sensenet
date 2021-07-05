package com.tcb.sensenet.internal.meta.view;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.cytoscape.event.CyEventHelper;
import org.cytoscape.model.CyEdge;
import org.cytoscape.model.CyNode;

import com.tcb.sensenet.internal.events.NodeGroupAboutToCollapseEvent;
import com.tcb.sensenet.internal.events.NodeGroupCollapsedEvent;
import com.tcb.sensenet.internal.meta.network.MetaNetwork;
import com.tcb.cytoscape.cyLib.cytoApiWrappers.CyNetworkAdapter;
import com.tcb.cytoscape.cyLib.cytoApiWrappers.CyNetworkViewManagerAdapter;

public class MetaNetworkView {

	private CyEventHelper eventHelper;
	private CyNetworkViewManagerAdapter viewManager;
	private MetaNetwork metaNetwork;

	public MetaNetworkView(
			MetaNetwork metaNetwork,
			CyEventHelper eventHelper,
			CyNetworkViewManagerAdapter viewManager){
		this.metaNetwork = metaNetwork;
		this.eventHelper = eventHelper;
		this.viewManager = viewManager;
	}
			
	public void collapse(CyNode headNode, CyNetworkAdapter network){
		boolean collapsing = true;
		eventHelper.fireEvent(new NodeGroupAboutToCollapseEvent(headNode,network,collapsing));
		eventHelper.flushPayloadEvents();
		silenceTableEvents(network);
		List<CyNode> subNodes = metaNetwork.getSubnodes(headNode);
		Set<CyEdge> subNodeEdges = subNodes.stream()
				.map(n -> network.getAdjacentEdgeList(n, CyEdge.Type.ANY))
				.flatMap(l -> l.stream())
				.collect(Collectors.toSet());
		network.removeNodes(subNodes);
		network.removeEdges(subNodeEdges);
		network.restoreNode(headNode);
		getVisibleEdges(headNode,network).forEach(e -> network.restoreEdge(e));
		eventHelper.fireEvent(new NodeGroupCollapsedEvent(headNode,network,collapsing));
		eventHelper.flushPayloadEvents();
		unsilenceTableEvents(network);
	}
	
	public void collapseAll(CyNetworkAdapter network){
		for(CyNode node:metaNetwork.getMetanodes()){
			collapse(node, network);
		}
	}
	
	private Set<CyEdge> getVisibleEdges(CyNode node, CyNetworkAdapter network){
		return metaNetwork.getRootNetwork().getAdjacentEdgeList(node, CyEdge.Type.ANY).stream()
				.filter(e -> sourceAndTargetPresent(e, network))
				.collect(Collectors.toSet());
	}
	
	private static boolean sourceAndTargetPresent(CyEdge edge, CyNetworkAdapter network){
		return network.containsNode(edge.getSource()) && network.containsNode(edge.getTarget());
	}
			
	public void expand(CyNode node, CyNetworkAdapter network){
		boolean collapsing = false;
		eventHelper.fireEvent(new NodeGroupAboutToCollapseEvent(node,network,collapsing));
		eventHelper.flushPayloadEvents();
		silenceTableEvents(network);
		network.removeEdges(metaNetwork.getRootNetwork().getAdjacentEdgeList(node,CyEdge.Type.ANY).stream()
				.collect(Collectors.toList()));
		network.removeNodes(Collections.singleton(node));
		List<CyNode> subNodes = metaNetwork.getSubnodes(node).stream()
				.collect(Collectors.toList());
		subNodes.forEach(n -> network.restoreNode(n));
		List<CyEdge> subEdges = subNodes.stream()
				.map(n -> getVisibleEdges(n, network))
				.flatMap(l -> l.stream())
				.collect(Collectors.toList());
		subEdges.forEach(e -> network.restoreEdge(e));
		eventHelper.fireEvent(new NodeGroupCollapsedEvent(node,network,collapsing));
		eventHelper.flushPayloadEvents();
		unsilenceTableEvents(network);
	}
	
	public void expandAll(CyNetworkAdapter network){
		for(CyNode node:metaNetwork.getMetanodes()){
			expand(node, network);
		}
	}
	
	private void silenceTableEvents(CyNetworkAdapter network){
		eventHelper.silenceEventSource(network.getDefaultNodeTable().getAdaptedTable());
		eventHelper.silenceEventSource(network.getDefaultEdgeTable().getAdaptedTable());
		viewManager.getNetworkViews(network)
			.forEach(v -> eventHelper.silenceEventSource(v));
	}
	
	
	private void unsilenceTableEvents(CyNetworkAdapter network){
		eventHelper.unsilenceEventSource(network.getDefaultNodeTable().getAdaptedTable());
		eventHelper.unsilenceEventSource(network.getDefaultEdgeTable().getAdaptedTable());
		viewManager.getNetworkViews(network)
			.forEach(v -> eventHelper.unsilenceEventSource(v));
	}
	
	public static boolean isCollapsed(CyNode node, CyNetworkAdapter network){
		return network.containsNode(node);
	}
	
	public MetaNetwork getMetaNetwork(){
		return metaNetwork;
	}
}
