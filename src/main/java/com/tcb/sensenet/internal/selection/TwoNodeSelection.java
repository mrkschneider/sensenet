package com.tcb.sensenet.internal.selection;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.cytoscape.model.CyIdentifiable;
import org.cytoscape.model.CyNode;

import com.google.auto.value.AutoValue;
import com.tcb.cytoscape.cyLib.cytoApiWrappers.CyNetworkAdapter;
import com.tcb.cytoscape.cyLib.cytoApiWrappers.CyRootNetworkAdapter;

@AutoValue
public abstract class TwoNodeSelection {
	
	public static TwoNodeSelection create(
			CyNetworkAdapter network,
			CyRootNetworkAdapter rootNetwork) throws InvalidSelectionException {
		List<CyNode> selectedNodes = new ArrayList<>(network.getSelectedNodes());
		if(selectedNodes.size()!=2) throw new InvalidSelectionException("You must select exactly 2 nodes");
		
		Comparator<CyIdentifiable> comparator = new SelectionTimeComparator(rootNetwork);
		selectedNodes.sort(comparator);
		
		CyNode first = selectedNodes.get(0);
		CyNode second = selectedNodes.get(1);
		return create(network,first,second);
	}
	
	public static TwoNodeSelection create(
			CyNetworkAdapter network, CyNode first, CyNode second){
		return new AutoValue_TwoNodeSelection(network,first,second);
	}
	
	public abstract CyNetworkAdapter getNetwork();
	public abstract CyNode getFirst();
	public abstract CyNode getSecond();
		
}
