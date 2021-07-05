package com.tcb.sensenet.internal.analysis.matrix;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.cytoscape.model.CyEdge;
import org.cytoscape.model.CyNode;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.tcb.sensenet.internal.analysis.matrix.weight.EdgeWeighter;
import com.tcb.sensenet.internal.analysis.matrix.weight.UniformEdgeWeighter;
import com.tcb.sensenet.internal.app.AppColumns;
import com.tcb.sensenet.internal.meta.network.MetaNetwork;
import com.tcb.sensenet.internal.meta.timeline.MetaTimeline;
import com.tcb.sensenet.internal.meta.timeline.factories.NetworkMetaTimelineFactory;
import com.tcb.sensenet.internal.util.IndexMap;
import com.tcb.cytoscape.cyLib.cytoApiWrappers.CyNetworkAdapter;
import com.tcb.matrix.LabeledMatrix;
import com.tcb.matrix.LabeledSquareMatrixImpl;
import com.tcb.matrix.SparseMatrix;
import com.tcb.common.util.SafeMap;
import com.tcb.common.util.Tuple;

public class ContactMatrixFactory {
	
	private EdgeWeighter edgeWeighter;

	public ContactMatrixFactory(
			EdgeWeighter edgeWeighter){
		this.edgeWeighter = edgeWeighter;
	}
	
	public ContactMatrixFactory(){
		this(new UniformEdgeWeighter());
	}
		
	public ContactMatrix create(CyNetworkAdapter network){
		List<CyNode> nodes = network.getNodeList()
				.stream()
				.sorted((n1,n2) -> n1.getSUID().compareTo(n2.getSUID()))
				.collect(ImmutableList.toImmutableList());
		List<CyEdge> edges = network.getEdgeList();
		int size = nodes.size();
		LabeledMatrix<CyNode> innerMatrix = LabeledSquareMatrixImpl.create(nodes, new SparseMatrix(size,size));
		ContactMatrix matrix = new ContactMatrixImpl(innerMatrix);
		
		for(CyEdge edge: edges) {
			CyNode source = edge.getSource();
			CyNode target = edge.getTarget();
			assertNoSelfEdge(source,target);
			Double weight = edgeWeighter.weight(edge);
			Double oldFrameValue = matrix.get(source,target);
			Double frameValue = oldFrameValue + weight;
			matrix.set(source,target,frameValue);
			matrix.set(target, source, frameValue);
		}
		return matrix;
	}
		
	private void assertNoSelfEdge(CyNode source, CyNode target){
		if(source.equals(target)){
			throw new RuntimeException("Contact matrix may not contain self-edges");
		}
	}

}
