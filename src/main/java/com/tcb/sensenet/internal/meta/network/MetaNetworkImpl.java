package com.tcb.sensenet.internal.meta.network;


import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.cytoscape.model.CyEdge;
import org.cytoscape.model.CyIdentifiable;
import org.cytoscape.model.CyNetwork;
import org.cytoscape.model.CyNode;
import org.cytoscape.model.subnetwork.CyRootNetwork;

import com.google.common.collect.ImmutableList;
import com.tcb.sensenet.internal.meta.serialization.MetaObjectInputStream;
import com.tcb.sensenet.internal.protectNetwork.ProtectNetworkUtil;
import com.tcb.sensenet.internal.tree.SessionTreeUpdater;
import com.tcb.sensenet.internal.uuid.UuidUtil;
import com.tcb.cytoscape.cyLib.cytoApiWrappers.CyNetworkAdapter;
import com.tcb.cytoscape.cyLib.cytoApiWrappers.CyRootNetworkAdapter;
import com.tcb.cytoscape.cyLib.cytoApiWrappers.CyRowAdapter;
import com.tcb.cytoscape.cyLib.cytoApiWrappers.CySessionAdapter;
import com.tcb.cytoscape.cyLib.data.DefaultColumns;
import com.tcb.aifgen.importer.TimelineType;
import com.tcb.tree.node.Node;
import com.tcb.tree.tree.Tree;

public class MetaNetworkImpl implements MetaNetwork,Serializable {
	private static final long serialVersionUID = 3L;
	
	private transient CyRootNetworkAdapter rootNetwork;
	
	private Tree tree;
	private Long rootNetworkSuid;
	private final TimelineType type;
	private UUID uuid;
	
	public MetaNetworkImpl(
			CyRootNetworkAdapter rootNetwork,
			Tree tree){
		this.tree = tree;
		setRootNetwork(rootNetwork);
		
		this.type = TimelineType.valueOf(
				getHiddenDataRow()
				.get(DefaultColumns.TYPE, String.class));
		
		this.uuid = UuidUtil.createUuid();
		UuidUtil.setUuid(rootNetwork,uuid);
		}
	
	private void setRootNetwork(CyRootNetworkAdapter rootNetwork){
		this.rootNetwork = rootNetwork;
		this.rootNetworkSuid = rootNetwork.getSUID();
	}
		
	private void readObject(ObjectInputStream in) throws  IOException, ClassNotFoundException{
		in.defaultReadObject();
		CySessionAdapter session = MetaObjectInputStream.create(in).session;
		updateBySession(session);
	}
				
	private void updateBySession(CySessionAdapter session) {
		tree = new SessionTreeUpdater(session).updateSuids(tree);
		CyRootNetworkAdapter rootNetwork = new CyRootNetworkAdapter(
				(CyRootNetwork) session.getObject(rootNetworkSuid, CyNetwork.class));
		setRootNetwork(rootNetwork);
		return;
	}
	
	@Override
	public CyRootNetworkAdapter getRootNetwork(){
		return this.rootNetwork;
	}

	@Override
	public Tree getTree() {
		return tree;
	}
	
	@Override
	public List<CyNode> getNodes(){
		return tree.getNodes().stream()
				.map(n -> n.getSuid())
				.filter(s -> !s.equals(rootNetworkSuid))
				.map(s -> getNode(s))
				.collect(ImmutableList.toImmutableList());
	}
	
	@Override
	public List<CyEdge> getEdges(){
		return tree.getEdges().stream()
				.map(n -> n.getSuid())
				.map(s -> getEdge(s))
				.collect(ImmutableList.toImmutableList());
	}


	@Override
	public List<CyNode> getMetanodes() {
		return tree.getMetanodes().stream()
				.map(n -> n.getSuid())
				.filter(s -> !s.equals(rootNetworkSuid))
				.map(s -> getNode(s))
				.collect(ImmutableList.toImmutableList());
	}


	@Override
	public List<CyEdge> getMetaedges() {
		return tree.getMetaedges().stream()
				.map(e -> e.getSuid())
				.map(s -> getEdge(s))
				.collect(ImmutableList.toImmutableList());
	}

	@Override
	public List<CyNode> getSubnodes(CyNode node) {
		Node node2 = tree.getNode(node.getSUID());
		return tree.getSubnodes(node2).stream()
				.map(n -> n.getSuid())
				.map(s -> getNode(s))
				.collect(ImmutableList.toImmutableList());
	}

	@Override
	public List<CyEdge> getSubedges(CyEdge edge) {
		String edgeType = getInteractionType(edge);
		Node source = tree.getNode(edge.getSource().getSUID());
		Node target = tree.getNode(edge.getTarget().getSUID());
		return tree.getConnectingSubedges(
				source,target)
					.stream()
					.map(e -> e.getSuid())
					.map(s -> getEdge(s))
					.filter(e -> interactionTypesMatch(edgeType, getInteractionType(e)))
					.collect(ImmutableList.toImmutableList());
	}

	@Override
	public List<CyRowAdapter> getSubRows(CyNode node) {
		return getSubnodes(node).stream()
				.map(n -> getRow(n))
				.collect(ImmutableList.toImmutableList());
	}

	@Override
	public List<CyRowAdapter> getSubRows(CyEdge edge) {
		return getSubedges(edge).stream()
				.map(e -> getRow(e))
				.collect(ImmutableList.toImmutableList());
	}

	@Override
	public List<CyRowAdapter> getHiddenSubRows(CyNode node) {
		return getSubnodes(node).stream()
				.map(n -> getHiddenRow(n))
				.collect(ImmutableList.toImmutableList());
	}

	@Override
	public List<CyRowAdapter> getHiddenSubRows(CyEdge edge) {
		return getSubedges(edge).stream()
				.map(e -> getHiddenRow(e))
				.collect(ImmutableList.toImmutableList());
	}

	@Override
	public CyRowAdapter getRow(CyIdentifiable c) {
		return rootNetwork.getRow(c);
	}

	@Override
	public CyRowAdapter getHiddenRow(CyIdentifiable c) {
		return rootNetwork.getHiddenRow(c);
	}


	@Override
	public CyNode getNode(Long suid) {
		return rootNetwork.getNode(suid);
	}


	@Override
	public CyEdge getEdge(Long suid) {
		return rootNetwork.getEdge(suid);
	}


	@Override
	public TimelineType getTimelineType() {
		return type;
	}
	
	private boolean interactionTypesMatch(String metaEdgeType, String subEdgeType){
		return metaEdgeType.equals(subEdgeType);
	}
	
	private String getInteractionType(CyEdge edge){
		return rootNetwork.getRow(edge).get(DefaultColumns.SHARED_INTERACTION, String.class);
	}


	@Override
	public CyRowAdapter getHiddenDataRow() {
		return rootNetwork.getHiddenNetworkTable().getRow(rootNetwork.getSUID());
	}
	
	@Override
	public CyRowAdapter getSharedDataRow() {
		return rootNetwork.getDefaultNetworkTable().getRow(rootNetwork.getSUID());
	}

	@Override
	public Long getSUID() {
		return rootNetwork.getSUID();
	}

	@Override
	public List<CyNetworkAdapter> getSubNetworks() {
		List<CyNetworkAdapter> subNetworks = rootNetwork.getSubNetworkList();
		List<CyNetworkAdapter> result = new ArrayList<>();
		for(CyNetworkAdapter network:subNetworks){
			Boolean isProtectionNetwork = ProtectNetworkUtil.isProtectNetwork(network);
			if(isProtectionNetwork) continue;
			result.add(network);
		}
		return result;
	}

	@Override
	public UUID getUuid() {
		return uuid;
	}
	
	@Override
	public String getName(){
		return getSharedDataRow().get(DefaultColumns.SHARED_NAME, String.class);
	}
	
	@Override
	public String toString(){
		return getName();
	}
			
}
