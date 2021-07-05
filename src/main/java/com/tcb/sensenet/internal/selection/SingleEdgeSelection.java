package com.tcb.sensenet.internal.selection;

import java.util.List;

import org.cytoscape.model.CyEdge;

import com.google.auto.value.AutoValue;
import com.tcb.cytoscape.cyLib.cytoApiWrappers.CyNetworkAdapter;
import com.tcb.cytoscape.cyLib.data.DefaultColumns;
import com.tcb.cytoscape.cyLib.log.ParameterReporter;
import com.tcb.common.util.ListFilter;

@AutoValue
public abstract class SingleEdgeSelection implements ParameterReporter {
		
	public static SingleEdgeSelection create(CyNetworkAdapter network, CyEdge edge){
		return new AutoValue_SingleEdgeSelection(network,edge);
	}
	
	public static SingleEdgeSelection create(CyNetworkAdapter network) throws InvalidSelectionException {
		List<CyEdge> selectedEdges = network.getSelectedEdges();
		if(selectedEdges.size()!=1) throw new InvalidSelectionException("Must select exactly one edge");
		CyEdge selectedEdge = 
				ListFilter.singleton(network.getSelectedEdges())
				.get();
		return create(network,selectedEdge);
	}
	
	public abstract CyNetworkAdapter getNetwork();
	public abstract CyEdge getEdge();
	
	@Override
	public String reportParameters(){
		String name = getNetwork().getRow(getEdge()).get(DefaultColumns.SHARED_NAME, String.class);
		return name;
	}
}
