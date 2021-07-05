package com.tcb.sensenet.internal.init.groups;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.cytoscape.model.CyEdge;
import org.cytoscape.model.CyNode;

import com.google.common.collect.ImmutableList;
import com.tcb.atoms.atoms.Atom;
import com.tcb.atoms.residues.Residue;
import com.tcb.sensenet.internal.app.AppColumns;
import com.tcb.sensenet.internal.data.rows.NodeAtomFactory;
import com.tcb.sensenet.internal.init.Initializer;
import com.tcb.sensenet.internal.init.groups.edges.MetaEdgeDefinition;
import com.tcb.sensenet.internal.init.groups.edges.StandardMetaEdgeDefinition;
import com.tcb.sensenet.internal.init.groups.nodes.NodeGroupDefinition;
import com.tcb.sensenet.internal.init.row.HiddenMetaNodeRowInitializer;
import com.tcb.sensenet.internal.init.row.HiddenSubNodeRowInitializer;
import com.tcb.sensenet.internal.init.row.MetaEdgeRowsInitializer;
import com.tcb.sensenet.internal.init.row.MetaNodeRowInitializer;
import com.tcb.cytoscape.cyLib.cytoApiWrappers.CyNetworkAdapter;
import com.tcb.cytoscape.cyLib.cytoApiWrappers.CyRootNetworkAdapter;
import com.tcb.cytoscape.cyLib.cytoApiWrappers.CyRowAdapter;
import com.tcb.cytoscape.cyLib.data.DefaultColumns;
import com.tcb.aifgen.importer.InteractionImportData;
import com.tcb.aifgen.importer.InteractionList;
import com.tcb.common.util.SafeMap;


public class GroupsInitializer implements Initializer {
	private CyNetworkAdapter network;
	private CyRootNetworkAdapter rootNetwork;
	private InteractionImportData interactionImporter;
	private NodeGroupDefinition groupDefinition;
	
	public GroupsInitializer(
			NodeGroupDefinition groupDefinition,
			CyRootNetworkAdapter rootNetwork,
			CyNetworkAdapter network,
			InteractionImportData interactionImporter){
		this.groupDefinition = groupDefinition;
		this.rootNetwork = rootNetwork;
		this.network = network;
		this.interactionImporter = interactionImporter;
	}
	
	public void init(){
		initNodeRows();
		initEdgeRows();
	}
	
	private void initNodeRows(){
		initNodeRows(groupDefinition);
	}
	
	private void initNodeRows(NodeGroupDefinition groupDefinition){
		
		Map<Residue,Map<String,Set<CyNode>>> subNodeMap = getGroupSubNodes(groupDefinition);
		
		for(Residue residue:subNodeMap.keySet()){
			Map<String,Set<CyNode>> groupMap = subNodeMap.get(residue);
			for(String groupTag:groupMap.keySet()){
				if(!groupMap.containsKey(groupTag)) continue;
				Set<CyNode> subNodes = groupMap.get(groupTag);
				CyNode groupNode = rootNetwork.addNode();
				
				initMetaNodeRows(groupNode, residue, groupTag);
				initSubNodeRows(subNodes, groupNode);
				addMetaEdges(groupNode,subNodes);	
			} 
		}
	}
	
	private void initMetaNodeRows(CyNode groupNode, Residue residue, String groupTag){
		
		MetaNodeRowInitializer metaNodeRowInitializer = 
				new MetaNodeRowInitializer(groupTag, interactionImporter);
		HiddenMetaNodeRowInitializer hiddenMetaNodeRowInitializer = new HiddenMetaNodeRowInitializer();
		
		CyRowAdapter row = rootNetwork.getRow(groupNode);
		metaNodeRowInitializer.init(row,residue);
		
		CyRowAdapter hiddenRow = rootNetwork.getHiddenRow(groupNode);
		hiddenMetaNodeRowInitializer.init(hiddenRow);
	}
	
	private void initSubNodeRows(Set<CyNode> subNodes, CyNode groupNode){
		for(CyNode subNode:subNodes){
			new HiddenSubNodeRowInitializer(groupNode.getSUID())
			.init(rootNetwork.getHiddenRow(subNode));
		}
	}
	
	
	private void addMetaEdges(CyNode groupNode, Set<CyNode> subNodes){
		for(String interactionType: InteractionList.getInteractionTypes(interactionImporter.getInteractions())){
			MetaEdgeDefinition metaEdgeDefinition = 
					new StandardMetaEdgeDefinition(interactionType);
			Set<CyEdge> subNodeEdges = subNodes.stream()
					.map(n -> rootNetwork.getAdjacentEdgeList(n, CyEdge.Type.ANY))
					.flatMap(l -> l.stream())
					.filter(e -> metaEdgeDefinition.test(e, rootNetwork))
					.collect(Collectors.toSet());			
			
			for(CyEdge e: subNodeEdges){
				addMetaEdge(e, groupNode, subNodes, metaEdgeDefinition);
			}
		}
	}
	
	private void addMetaEdge(CyEdge subNodeEdge, CyNode groupNode, Set<CyNode> subNodes,
			MetaEdgeDefinition metaEdgeDefinition){
		CyNode source = subNodeEdge.getSource();
		CyNode target = subNodeEdge.getTarget();
		
		if(subNodes.contains(source)){
			source = groupNode;
		}
		if(subNodes.contains(target)){
			target = groupNode;
		}
		
		if(source==target || metaEdgeExists(source, target, metaEdgeDefinition)){
			return;
		}
				
		CyEdge metaEdge = rootNetwork.addEdge(source, target, false);
		metaEdgeDefinition.setEdgeValid(metaEdge, rootNetwork);
		rootNetwork.getHiddenRow(metaEdge).set(AppColumns.IS_METAEDGE, true);
	}
	
	private boolean metaEdgeExists(CyNode source, CyNode target, MetaEdgeDefinition def){
		return rootNetwork.getConnectingEdgeList(source, target, CyEdge.Type.ANY).stream()
				.anyMatch(e -> def.test(e,rootNetwork));
		
	}
	
	private Map<Residue,Map<String,Set<CyNode>>> getGroupSubNodes(
			NodeGroupDefinition groupDefinition){
		NodeAtomFactory atomFac = new NodeAtomFactory();
		List<CyNode> nodes = network.getNodeList();
		
		Map<Residue,Map<String,Set<CyNode>>> result = new SafeMap<>();
		for(CyNode node:nodes){
			Atom atom = atomFac.create(node, rootNetwork);
			Residue residue = atom.getResidue();
			if(!result.containsKey(residue)) result.put(residue, new SafeMap<>());
			Map<String,Set<CyNode>> subMap = result.get(residue);
			String groupTag = groupDefinition.getGroupTag(atom);
			if(!subMap.containsKey(groupTag)) subMap.put(groupTag, new HashSet<>());
			subMap.get(groupTag).add(node);
		}
				
		return result;
	}
	
	private void initEdgeRows(){
		List<CyEdge> uninitializedMetaEdges = rootNetwork.getEdgeList().stream()
				.filter(e -> rootNetwork.getHiddenRow(e)
							 .getMaybe(AppColumns.IS_METAEDGE, Boolean.class).get())
				.collect(Collectors.toList());
		uninitializedMetaEdges.forEach(e -> new MetaEdgeRowsInitializer(rootNetwork).init(e));
	}
		
}
