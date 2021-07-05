package com.tcb.sensenet.internal.tree;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.cytoscape.model.CyEdge;
import org.cytoscape.model.CyIdentifiable;
import org.cytoscape.model.CyNetwork;
import org.cytoscape.model.CyNode;

import com.tcb.cytoscape.cyLib.cytoApiWrappers.CySessionAdapter;
import com.tcb.tree.tree.Tree;
import com.tcb.tree.tree.TreeUpdater;

public class SessionTreeUpdater {
	
	private CySessionAdapter session;

	public SessionTreeUpdater(CySessionAdapter session){
		this.session = session;
	}
		
	public Tree updateSuids(Tree tree){
		List<Long> oldNodeSuids = tree.getNodes().stream()
				.filter(n -> !n.equals(tree.getRoot()))
				.map(n -> n.getSuid())
				.collect(Collectors.toList());
		List<Long> oldEdgeSuids = tree.getEdges().stream()
				.map(e -> e.getSuid())
				.collect(Collectors.toList());
		Long oldNetworkSuid = tree.getRoot().getSuid();
				
		Map<Long,Long> nodeSuidMap = oldNodeSuids.stream()
				.collect(getUpdatedSUIDCollector(session,CyNode.class));
		Map<Long,Long> edgeSuidMap = oldEdgeSuids.stream()
				.collect(getUpdatedSUIDCollector(session,CyEdge.class));
		Map<Long,Long> networkSuidMap = Collections.singleton(oldNetworkSuid).stream()
				.collect(getUpdatedSUIDCollector(session,CyNetwork.class));

		Map<Long,Long> suidMap = new HashMap<Long,Long>();
		suidMap.putAll(nodeSuidMap);
		suidMap.putAll(edgeSuidMap);
		suidMap.putAll(networkSuidMap);
		
		TreeUpdater updater = new TreeUpdater(suidMap);
		
		return updater.updateSuids(tree);
	}
	
	private static Collector<Long,?,Map<Long,Long>> getUpdatedSUIDCollector(CySessionAdapter session,
			Class<? extends CyIdentifiable> clazz){
		return Collectors.toMap(s -> s, s -> session.getUpdatedSUID(s, clazz));
	}
}
