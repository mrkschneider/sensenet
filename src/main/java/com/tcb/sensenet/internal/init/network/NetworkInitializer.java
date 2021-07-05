package com.tcb.sensenet.internal.init.network;

import java.util.Map;
import java.util.Optional;

import org.cytoscape.model.CyEdge;
import org.cytoscape.model.CyNode;

import com.tcb.atoms.atoms.Atom;
import com.tcb.atoms.interactions.Interaction;
import com.tcb.sensenet.internal.init.Initializer;
import com.tcb.sensenet.internal.init.groups.nodes.NodeGroupDefinition;
import com.tcb.sensenet.internal.init.row.EdgeRowInitializer;
import com.tcb.sensenet.internal.init.row.HiddenEdgeRowInitializer;
import com.tcb.sensenet.internal.init.row.HiddenNodeRowInitializer;
import com.tcb.sensenet.internal.init.row.NodeRowInitializer;
import com.tcb.cytoscape.cyLib.cytoApiWrappers.CyNetworkAdapter;
import com.tcb.cytoscape.cyLib.cytoApiWrappers.CyRootNetworkAdapter;
import com.tcb.cytoscape.cyLib.cytoApiWrappers.CyRowAdapter;
import com.tcb.aifgen.importer.InteractionImportData;
import com.tcb.aifgen.importer.InteractionList;
import com.tcb.common.util.SafeMap;



public class NetworkInitializer implements Initializer {
	
	private CyNetworkAdapter network;
	private InteractionImportData interactionImporter;
	private CyRootNetworkAdapter rootNetwork;
	private Optional<Map<Atom,CyNode>> nodes;
	private Optional<Map<Interaction,CyEdge>> edges;
	private NodeGroupDefinition groupDefinition;

	public NetworkInitializer(
			CyRootNetworkAdapter rootNetwork,
			CyNetworkAdapter network,
			InteractionImportData interactionImporter,
			NodeGroupDefinition groupDefinition
			){
		this.rootNetwork = rootNetwork;
		this.network = network;
		this.interactionImporter = interactionImporter;
		this.groupDefinition = groupDefinition;
		this.nodes = Optional.empty();
		this.edges = Optional.empty();
	}
	
	public void init(){
		this.nodes = Optional.of(createNodes());
		this.edges = Optional.of(createEdges(nodes.get()));
	}
	
	private Map<Atom,CyNode> createNodes(){
		Map<Atom,CyNode> nodes = new SafeMap<>();
		for(Atom atom:InteractionList.getInteractingAtoms(interactionImporter.getInteractions())){
			CyNode node = network.addNode();
			CyRowAdapter row = network.getRow(node);
			new NodeRowInitializer(interactionImporter, groupDefinition).init(row, atom);
			CyRowAdapter hiddenRow = rootNetwork.getHiddenRow(node);
			new HiddenNodeRowInitializer().init(hiddenRow);
			nodes.put(atom, node);
		}
		return nodes;
	}
	
	private Map<Interaction,CyEdge> createEdges(Map<Atom,CyNode> nodes){
		Map<Interaction,CyEdge> edges = new SafeMap<>();
		for(Interaction interaction: interactionImporter.getInteractions()){
			Atom sourceAtom = interaction.getSourceAtom();
			Atom targetAtom = interaction.getTargetAtom();
			CyEdge edge = network.addEdge(nodes.get(sourceAtom), nodes.get(targetAtom), false);
			CyRowAdapter sourceRow = network.getRow(edge.getSource());
			CyRowAdapter targetRow = network.getRow(edge.getTarget());
			CyRowAdapter row = network.getRow(edge);
			CyRowAdapter hiddenEdgeRow = rootNetwork.getHiddenRow(edge);
			new EdgeRowInitializer(interaction).init(row, sourceRow, targetRow);
			new HiddenEdgeRowInitializer(interaction).init(hiddenEdgeRow);
			edges.put(interaction, edge);
		}
		return edges;
	}
	
	public Map<Atom,CyNode> getNodes(){
		return nodes.orElseThrow(() -> new IllegalArgumentException("Not initialized"));
	}
	
	public Map<Interaction,CyEdge> getEdges(){
		return edges.orElseThrow(() -> new IllegalArgumentException("Not initialized"));
	}
	
	
}
