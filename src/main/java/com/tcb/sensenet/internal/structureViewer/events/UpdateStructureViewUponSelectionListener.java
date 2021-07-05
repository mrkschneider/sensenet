package com.tcb.sensenet.internal.structureViewer.events;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.cytoscape.model.CyEdge;
import org.cytoscape.model.CyNode;
import org.cytoscape.model.CyTable;
import org.cytoscape.model.events.RowSetRecord;
import org.cytoscape.model.events.RowsSetEvent;
import org.cytoscape.model.events.RowsSetListener;

import com.tcb.atoms.interactions.Interaction;
import com.tcb.atoms.residues.Residue;
import com.tcb.sensenet.internal.UI.util.SingletonErrorDialog;
import com.tcb.sensenet.internal.app.AppGlobals;
import com.tcb.sensenet.internal.data.rows.EdgeInteractionFactory;
import com.tcb.sensenet.internal.data.rows.NodeResidueFactory;
import com.tcb.sensenet.internal.meta.network.MetaNetwork;
import com.tcb.sensenet.internal.properties.AppProperties;
import com.tcb.sensenet.internal.properties.AppProperty;
import com.tcb.sensenet.internal.structureViewer.StructureModel;
import com.tcb.sensenet.internal.structureViewer.StructureModelManager;
import com.tcb.cytoscape.cyLib.cytoApiWrappers.CyNetworkAdapter;
import com.tcb.cytoscape.cyLib.cytoApiWrappers.CyRootNetworkAdapter;
import com.tcb.cytoscape.cyLib.cytoApiWrappers.CyRowAdapter;
import com.tcb.cytoscape.cyLib.data.DefaultColumns;
import com.tcb.netmap.structureViewer.StructureViewer;
import com.tcb.netmap.util.limiter.TooManyItemsException;


public class UpdateStructureViewUponSelectionListener extends AbstractStructureViewListener 
implements RowsSetListener {
	
	private AppGlobals appGlobals;
	
	private static final String selectedColumn = DefaultColumns.SELECTED.toString();
	
	private static final AppProperty zoomProperty = AppProperty.STRUCTURE_VIEWER_DEFAULT_ZOOM;
	
	public UpdateStructureViewUponSelectionListener(AppGlobals appGlobals){
		super(appGlobals);
		this.appGlobals = appGlobals;
	}
		
	@Override
	public synchronized void handleEvent(RowsSetEvent e) {
		if(!e.getColumns().contains(selectedColumn)) return;
		if(!appGlobals.state.metaNetworkManager.currentNetworkBelongsToMetaNetwork()) return;
		
		MetaNetwork metaNetwork = appGlobals.state.metaNetworkManager.getCurrentMetaNetwork();
		if(!modelIsActiveInViewer(metaNetwork)) return;
		
		CyNetworkAdapter network = appGlobals.state.metaNetworkManager.getCurrentNetwork();
		// Two RowSetEvents are fired each time, ignore one
		if(!isDefaultNodeOrEdgeTable(network, e.getSource())) return;
		
		StructureViewer viewer = appGlobals.structureViewerManager.getViewer();
		
		StructureModelManager models = appGlobals.structureViewerManager
				.getModels();
		if(!models.containsKey(metaNetwork)) return;
				
		StructureModel model = models.get(metaNetwork);
		
		RecordHandler handler = new RecordHandler(metaNetwork);
		
		for(RowSetRecord r:e.getColumnRecords(selectedColumn)){
			handler.handleRecord(r);		
		}
		
		List<Residue> totalSelectedResidues = getResidueForEachSelectedNode(metaNetwork);
		
		Set<Residue> shownResidues = handler.getShownResidues();
		Set<Residue> hiddenResidues = handler.getHiddenResidues();
		Set<Interaction> shownInteractions = handler.getShownInteractions();
		Set<Interaction> hiddenInteractions = handler.getHiddenInteractions();
						
		Boolean zoom = Boolean.valueOf(
				appGlobals.appProperties.getOrDefault(zoomProperty));
				
		List<Exception> errors = new ArrayList<>();
		
		AppProperties appProperties = appGlobals.appProperties;
		Color interactionColor = new Color(
				Integer.valueOf(
						appProperties.getOrDefault(AppProperty.STRUCTURE_VIEWER_DEFAULT_INTERACTION_COLOR)));
		Color selectionColor = new Color(
				Integer.valueOf(
						appProperties.getOrDefault(AppProperty.STRUCTURE_VIEWER_DEFAULT_SELECTED_COLOR)));
		
		try{
						
			try{model.showInteractions(viewer, shownInteractions,interactionColor);}
			catch(TooManyItemsException ex){
				errors.add(new RuntimeException("Selected too many edges for display"));
			}
			
			try{model.showResidues(viewer, shownResidues, selectionColor);}
			catch(TooManyItemsException ex){
				errors.add(new RuntimeException("Selected too many nodes for display"));
			}
						
			model.hideInteractions(viewer, hiddenInteractions);
			model.hideResidues(viewer, hiddenResidues);
			
			if(errors.isEmpty() && zoom){
				
				if(totalSelectedResidues.isEmpty())	model.zoomModel(viewer);
				else model.zoomResidues(viewer, totalSelectedResidues);
			}
						
		} catch(Exception ex){
			errors.add(ex);
		}
						
		for(Exception error:errors){
			SingletonErrorDialog.showNonBlocking(error);
			error.printStackTrace();
		}
				
	}
	
	private Set<CyNode> getTotalSelectedNodes(){
		CyNetworkAdapter network = appGlobals.state.metaNetworkManager.getCurrentNetwork();
		List<CyNode> selectedNodes = network.getSelectedNodes();
		List<CyNode> selectedSources = network.getSelectedEdges().stream()
				.map(e -> e.getSource())
				.collect(Collectors.toList());
		List<CyNode> selectedTargets = network.getSelectedEdges().stream()
				.map(e -> e.getTarget())
				.collect(Collectors.toList());
		Set<CyNode> selectedTotal = new HashSet<>();
		selectedTotal.addAll(selectedNodes);
		selectedTotal.addAll(selectedTargets);
		selectedTotal.addAll(selectedSources);
		return selectedTotal;
	}
	
	private List<Residue> getResidueForEachSelectedNode(MetaNetwork metaNetwork){
		Set<CyNode> selectedNodes = getTotalSelectedNodes();
		CyRootNetworkAdapter rootNetwork = metaNetwork.getRootNetwork();
		NodeResidueFactory residueFactory = new NodeResidueFactory();
		List<Residue> selectedResidues = selectedNodes.stream()
				.map(r -> residueFactory.create(r, rootNetwork))
				.collect(Collectors.toList());
		return selectedResidues;
	}
		
	private Boolean isDefaultNodeOrEdgeTable(CyNetworkAdapter network, CyTable table){
		CyTable nodeTable = network.getDefaultNodeTable().getAdaptedTable();
		CyTable edgeTable = network.getDefaultEdgeTable().getAdaptedTable();
		return table==nodeTable || table==edgeTable;
	}
		
	private Residue getResidue(CyNode n, CyRootNetworkAdapter rootNetwork){
		return new NodeResidueFactory().create(n, rootNetwork);
	}
		
	private Interaction getInteraction(CyEdge e, MetaNetwork metaNetwork){
		return new EdgeInteractionFactory(appGlobals.state.timelineManager).create(e,metaNetwork,false);
	}
	
	private class RecordHandler {
		private MetaNetwork metaNetwork;
		private Set<Residue> shownResidues;
		private Set<Residue> hiddenResidues;
		private Set<Interaction> hiddenInteractions;
		private Set<Interaction> shownInteractions;

		public RecordHandler(MetaNetwork metaNetwork){
			this.metaNetwork = metaNetwork;
			this.shownResidues = new HashSet<>();
			this.hiddenResidues = new HashSet<>();
			this.shownInteractions = new HashSet<>();
			this.hiddenInteractions = new HashSet<>();
		}
		
		public void handleRecord(RowSetRecord r){
			CyRowAdapter row = new CyRowAdapter(r.getRow());
			Long suid = row.get(DefaultColumns.SUID, Long.class);
			CyNode node = metaNetwork.getNode(suid);
			Boolean isSelected = (Boolean)r.getValue();
			
			// Return if record is outdated
			// May happen for very fast selection/deselection chains
			if(
					(!isSelected.equals(row.get(DefaultColumns.SELECTED, Boolean.class)))
			  ) return;
			
			if(node!=null) {
				handleNode(node, isSelected);
				return;
			}
			CyEdge edge = metaNetwork.getEdge(suid);
			if(edge!=null) {
				handleEdge(edge, isSelected);
				return;
			}
			return;
		}
		
		public Set<Residue> getShownResidues(){
			return shownResidues;
		}
		
		public Set<Residue> getHiddenResidues(){
			return hiddenResidues;
		}
		
		public Set<Interaction> getShownInteractions(){
			return shownInteractions;
		}
		
		public Set<Interaction> getHiddenInteractions(){
			return hiddenInteractions;
		}
		
		private void handleNode(CyNode node, Boolean isSelected){
			CyRootNetworkAdapter rootNetwork = metaNetwork.getRootNetwork();
			Residue residue = getResidue(node, rootNetwork);
			try{
				if(isSelected){
					shownResidues.add(residue);
				} else {
					if(hasNoSelectedNode(residue)){
						hiddenResidues.add(residue);
					}
				}
			} catch(Exception e){}		
		}
		
		private boolean hasNoSelectedNode(Residue residue){
			List<Residue> selectedResidues = getResidueForEachSelectedNode(metaNetwork);
			long count = selectedResidues.stream()
					.filter(r -> r.equals(residue))
					.count();
			if(count == 0) return true;
			else return false;
		}

		
		
		
		
		private void handleEdge(CyEdge edge, Boolean isSelected){
			CyNode source = edge.getSource();
			CyNode target = edge.getTarget();
			handleNode(source, isSelected);
			handleNode(target, isSelected);
			for(CyEdge subEdge:metaNetwork.getSubedges(edge)){
				Interaction interaction = getInteraction(subEdge, metaNetwork);
				try{
					if(isSelected){
						shownInteractions.add(interaction);
					} else {
						hiddenInteractions.add(interaction);
					}
				} catch(Exception e){}
			}
					
		}
	
	}
		
}


