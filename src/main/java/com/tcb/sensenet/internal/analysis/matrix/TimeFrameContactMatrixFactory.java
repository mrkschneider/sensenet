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
import com.tcb.sensenet.internal.analysis.matrix.weight.TimeFrameEdgeWeighter;
import com.tcb.sensenet.internal.app.AppColumns;
import com.tcb.sensenet.internal.meta.network.MetaNetwork;
import com.tcb.sensenet.internal.meta.timeline.MetaTimeline;
import com.tcb.sensenet.internal.meta.timeline.factories.NetworkMetaTimelineFactory;
import com.tcb.sensenet.internal.util.CancelledException;
import com.tcb.sensenet.internal.util.IndexMap;
import com.tcb.cytoscape.cyLib.cytoApiWrappers.CyNetworkAdapter;
import com.tcb.matrix.SparseMatrix;
import com.tcb.common.util.SafeMap;
import com.tcb.common.util.Tuple;

public class TimeFrameContactMatrixFactory {
	
	private MetaNetwork metaNetwork;
	private NetworkMetaTimelineFactory metaTimelineFactory;
	private Integer sieveStep;
	
	private volatile boolean cancelled = false;

	public TimeFrameContactMatrixFactory(
			Integer sieveStep,
			MetaNetwork metaNetwork,
			NetworkMetaTimelineFactory metaTimelineFactory){
		this.sieveStep = sieveStep;
		this.metaNetwork = metaNetwork;
		this.metaTimelineFactory = metaTimelineFactory;
	}
	
	public TimeFrameContactMatrixFactory(
			MetaNetwork metaNetwork,
			NetworkMetaTimelineFactory metaTimelineFactory){
		this(1,metaNetwork,metaTimelineFactory);
	}
		
	public Map<Integer,ContactMatrix> createTimelineMatrices(CyNetworkAdapter network){
		int timelineLength = metaNetwork.getHiddenDataRow().get(AppColumns.TIMELINE_LENGTH, Integer.class);
		SafeMap<Integer,ContactMatrix> matrices = new SafeMap<>();
		
		for(int i=0;i<timelineLength;i+=sieveStep){
			matrices.put(i,createMatrix(i,network));
			if(cancelled) throw new CancelledException("Cancelled matrix creation");
		}
		return matrices;
	}
	
	private ContactMatrix createMatrix(
			int frame,
			CyNetworkAdapter network){
		EdgeWeighter edgeWeighter = new TimeFrameEdgeWeighter(
				frame, metaNetwork, metaTimelineFactory);
		ContactMatrixFactory contactMatrixFactory = new ContactMatrixFactory(
				edgeWeighter);
		return contactMatrixFactory.create(network);
	}
	
	public void cancel() {
		cancelled = true;
	}
}
