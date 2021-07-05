package com.tcb.sensenet.internal.structureViewer.events;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.cytoscape.model.CyEdge;
import org.cytoscape.model.CyNode;
import org.cytoscape.model.events.AboutToRemoveEdgesEvent;
import org.cytoscape.model.events.AboutToRemoveEdgesListener;
import org.cytoscape.model.events.AboutToRemoveNodesEvent;
import org.cytoscape.model.events.AboutToRemoveNodesListener;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.tcb.atoms.interactions.Interaction;
import com.tcb.atoms.residues.Residue;
import com.tcb.sensenet.internal.UI.util.SingletonErrorDialog;
import com.tcb.sensenet.internal.app.AppGlobals;
import com.tcb.sensenet.internal.data.rows.EdgeInteractionFactory;
import com.tcb.sensenet.internal.data.rows.NodeResidueFactory;
import com.tcb.sensenet.internal.meta.network.MetaNetwork;
import com.tcb.sensenet.internal.structureViewer.StructureModel;
import com.tcb.cytoscape.cyLib.cytoApiWrappers.CyNetworkAdapter;
import com.tcb.cytoscape.cyLib.cytoApiWrappers.CyRootNetworkAdapter;
import com.tcb.cytoscape.cyLib.cytoApiWrappers.CyRowAdapter;
import com.tcb.cytoscape.cyLib.data.DefaultColumns;
import com.tcb.netmap.structureViewer.StructureViewer;

public class UpdateStructureViewUponNodeEdgeDeletionListener 
	extends AbstractStructureViewListener
	implements AboutToRemoveEdgesListener, AboutToRemoveNodesListener
{

	private AppGlobals appGlobals;

	public UpdateStructureViewUponNodeEdgeDeletionListener(AppGlobals appGlobals){
		super(appGlobals);
		this.appGlobals = appGlobals;
	}
	
	@Override
	public void handleEvent(AboutToRemoveNodesEvent e) {
		CyNetworkAdapter network = new CyNetworkAdapter(e.getSource());
		if(!appGlobals.state.metaNetworkManager.belongsToMetaNetwork(network)) return;
		
		MetaNetwork metaNetwork = appGlobals.state.metaNetworkManager.get(network);
		if(!modelIsActiveInViewer(metaNetwork)) return;
				
		removeUnselectedResidues(e.getNodes(), network, metaNetwork);
	}
	
	private void removeUnselectedResidues(
			Collection<CyNode> removedNodes,
			CyNetworkAdapter network,
			MetaNetwork metaNetwork){
		NodeResidueFactory fac = new NodeResidueFactory();
		Set<Residue> remainingResidues = network.getSelectedNodes().stream()
				.filter(n -> !removedNodes.contains(n))
				.map(n -> fac.create(n, metaNetwork.getRootNetwork()))
				.collect(ImmutableSet.toImmutableSet());
		List<Residue> residuesToHide = removedNodes.stream()
				.map(n -> fac.create(n, metaNetwork.getRootNetwork()))
				.filter(r -> !remainingResidues.contains(r))
				.collect(ImmutableList.toImmutableList());
				
		StructureViewer viewer = appGlobals.structureViewerManager.getViewer();
		StructureModel model = appGlobals.structureViewerManager.getModels().get(metaNetwork);
				
		try{
			model.hideResidues(viewer, residuesToHide);
		} catch(IOException ex){
			SingletonErrorDialog.showNonBlocking(ex);
			ex.printStackTrace();
		}
	}
		
	@Override
	public void handleEvent(AboutToRemoveEdgesEvent e) {
		CyNetworkAdapter network = new CyNetworkAdapter(e.getSource());
		if(!appGlobals.state.metaNetworkManager.belongsToMetaNetwork(network)) return;
		
		MetaNetwork metaNetwork = appGlobals.state.metaNetworkManager.get(network);
		if(!modelIsActiveInViewer(metaNetwork)) return;
		
		List<CyEdge> subEdges = getSubedges(e.getEdges(),metaNetwork);
		Set<CyNode> removedNodes = new HashSet<CyNode>();
		removedNodes.addAll(subEdges.stream().map(edge -> edge.getSource()).collect(Collectors.toList()));
		removedNodes.addAll(subEdges.stream().map(edge -> edge.getTarget()).collect(Collectors.toList()));
		
		removeUnselectedResidues(removedNodes,network,metaNetwork);
		
		EdgeInteractionFactory fac = new EdgeInteractionFactory(appGlobals.state.timelineManager);
		List<Interaction> interactions = subEdges.stream()
				.map(edge -> fac.create(edge, metaNetwork, false))
				.collect(ImmutableList.toImmutableList());
			
		StructureViewer viewer = appGlobals.structureViewerManager.getViewer();
		StructureModel model = appGlobals.structureViewerManager.getModels().get(metaNetwork);
				
		try{
			model.hideInteractions(viewer, interactions);
		} catch(IOException ex){
			SingletonErrorDialog.showNonBlocking(ex);
			ex.printStackTrace();
		}
	}
		
	private List<CyEdge> getSubedges(Iterable<CyEdge> edges, MetaNetwork metaNetwork){
		ImmutableList.Builder<CyEdge> result = ImmutableList.builder();
		for(CyEdge edge:edges){
			List<CyEdge> subEdges = metaNetwork.getSubedges(edge);
			result.addAll(subEdges);
		}
		return result.build();
	}

}
