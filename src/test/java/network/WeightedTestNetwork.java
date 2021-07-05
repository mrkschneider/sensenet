package network;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.cytoscape.model.CyEdge;
import org.cytoscape.model.CyNode;

import com.google.common.collect.ComparisonChain;
import com.tcb.sensenet.internal.app.AppColumns;
import com.tcb.cytoscape.cyLib.cytoApiWrappers.CyNetworkAdapter;
import com.tcb.cytoscape.cyLib.cytoApiWrappers.CyRowAdapter;

public class WeightedTestNetwork {
	private CyNetworkAdapter network;

	public WeightedTestNetwork(CyNetworkAdapter network, Double[] weights){
		this.network = network;
		
		network.getDefaultEdgeTable().createColumn(AppColumns.WEIGHT, Double.class, false);
		
		addWeights(getSortedEdges(), weights, network);		
	}
	
	private class EdgeSorter implements Comparator<CyEdge> {
		@Override
		public int compare(CyEdge a, CyEdge b) {
			return ComparisonChain.start()
					.compare(a.getSource().getSUID(), b.getSource().getSUID())
					.compare(a.getTarget().getSUID(), b.getTarget().getSUID())
					.result();
		}
		
	}

	private void addWeights(List<CyEdge> edges, Double[] weights, CyNetworkAdapter network){
		for(int i=0;i<edges.size();i++){
			CyEdge e = edges.get(i);
			Double weight = weights[i];
			CyRowAdapter row = network.getRow(e);
			row.set(AppColumns.WEIGHT, weight);
		}
	}
	
	public CyNetworkAdapter getNetwork(){
		return network;
	}
	
	public List<CyNode> getSortedNodes(){
		return network.getNodeList().stream()
				.sorted(Comparator.comparing(n -> n.getSUID()))
				.collect(Collectors.toList());
	}
	
	public List<CyEdge> getSortedEdges(){
		return network.getEdgeList().stream()
				.sorted(new EdgeSorter())
				.collect(Collectors.toList());
	}
}
