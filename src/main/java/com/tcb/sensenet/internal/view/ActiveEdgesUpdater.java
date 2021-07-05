package com.tcb.sensenet.internal.view;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.cytoscape.model.CyEdge;
import org.cytoscape.model.subnetwork.CySubNetwork;

import com.google.common.collect.Sets;
import com.tcb.sensenet.internal.app.AppColumns;
import com.tcb.sensenet.internal.filter.AbsoluteCutoffFilter;
import com.tcb.sensenet.internal.meta.network.MetaNetwork;
import com.tcb.cytoscape.cyLib.cytoApiWrappers.CyNetworkAdapter;
import com.tcb.cytoscape.cyLib.cytoApiWrappers.CyNetworkViewManagerAdapter;
import com.tcb.cytoscape.cyLib.data.Columns;
import com.tcb.cytoscape.cyLib.data.DefaultColumns;
import com.tcb.common.util.Predicate;

public class ActiveEdgesUpdater implements Runnable {

	private MetaNetwork metaNetwork;

	public ActiveEdgesUpdater(
			MetaNetwork metaNetwork){
		this.metaNetwork = metaNetwork;
	}

	@Override
	public void run() {
		Columns weightColumn = AppColumns.valueOf(
				metaNetwork.getHiddenDataRow()
				.get(AppColumns.CUTOFF_COLUMN, String.class));
		Double cutoff = metaNetwork.getHiddenDataRow()
				.get(AppColumns.CUTOFF_VALUE, Double.class);
		Set<String> selectedInteractionTypes = metaNetwork.getHiddenDataRow()
				.getList(AppColumns.SELECTED_INTERACTION_TYPES, String.class)
				.stream()
				.collect(Collectors.toSet());
		verifySelection(selectedInteractionTypes);		
		
		for(CyNetworkAdapter network:metaNetwork.getSubNetworks()){
			List<CyEdge> deletedEdges = getDeletedEdges(network,metaNetwork);
			List<CyEdge> edges = network.getEdgeList();
			RemoveEdgeFilter filter = 
					new RemoveEdgeFilter(
							metaNetwork,
							selectedInteractionTypes,
							cutoff,
							weightColumn);
			List<CyEdge> removeEdges = filter.filter(edges);
			List<CyEdge> addEdges = filter.retain(deletedEdges);
						
			network.removeEdges(removeEdges);
			addEdges(addEdges,network);
		}
	}
	
	private List<CyEdge> getDeletedEdges(CyNetworkAdapter network, MetaNetwork metaNetwork){
		List<CyEdge> edges = metaNetwork.getRootNetwork().getDeletedEdges(network);
		return edges;
	}
	
	private void addEdges(Iterable<CyEdge> edges, CyNetworkAdapter network){
		CySubNetwork subNetwork = (CySubNetwork)network.getAdaptedNetwork();
		edges.forEach(e -> subNetwork.addEdge(e));
	}
	
	private void verifySelection(Set<String> selectedInteractionTypes){
		Set<String> availableInteractionTypes = metaNetwork.getHiddenDataRow()
				.getList(AppColumns.AVAILABLE_INTERACTION_TYPES, String.class)
				.stream()
				.collect(Collectors.toSet());
		Set<String> difference = Sets.difference(selectedInteractionTypes, availableInteractionTypes);
		if(!difference.isEmpty()){
			throw new IllegalArgumentException("Unknown interaction types: " + String.join(",",difference));
		}
	}
		
	private class RemoveEdgeFilter {

		private final Set<String> selectedInteractionTypes;
		private final Double cutoff;
		private final Columns cutoffColumn;
		private final MetaNetwork metaNetwork;
		private final Columns weightColumn;
		private final Double timepointCutoff; 

		public RemoveEdgeFilter(
				MetaNetwork metaNetwork,
				Set<String> selectedInteractionTypes,
				Double cutoff,
				Columns cutoffColumn){
			this.metaNetwork = metaNetwork;
			this.selectedInteractionTypes = selectedInteractionTypes;
			this.cutoff = cutoff;
			this.cutoffColumn = cutoffColumn;
			this.weightColumn = AppColumns.WEIGHT;
			this.timepointCutoff = metaNetwork.getHiddenDataRow()
				.get(AppColumns.TIMEPOINT_WEIGHT_CUTOFF, Double.class);
		}
		
		public List<CyEdge> filter(Collection<CyEdge> edges) {
			return edges.stream()
					.filter(e -> shouldRemove(e))
					.collect(Collectors.toList());
		}
		
		public List<CyEdge> retain(Collection<CyEdge> edges){
			return edges.stream()
					.filter(e -> shouldRetain(e))
					.collect(Collectors.toList());
		}
		
		private boolean shouldRemove(CyEdge e){
			return !shouldRetain(e);
		}
		
		private boolean shouldRetain(CyEdge e){
			return hasSelectedType(e) 
					&& isOverCutoff(e,cutoff,cutoffColumn)
					&& isOverCutoff(e,timepointCutoff,weightColumn);
		}
		
		private boolean hasSelectedType(CyEdge e){
			String edgeType = metaNetwork.getRow(e)
					.get(DefaultColumns.SHARED_INTERACTION, String.class);
			return selectedInteractionTypes.contains(edgeType);
		}
		
		private boolean isOverCutoff(CyEdge e, Double cutoff, Columns cutoffColumn){
			Predicate<CyEdge> filter = new AbsoluteCutoffFilter<>(
					metaNetwork.getRootNetwork(),cutoffColumn,cutoff);
			return filter.test(e);
		}
		
		
		
	}
}
