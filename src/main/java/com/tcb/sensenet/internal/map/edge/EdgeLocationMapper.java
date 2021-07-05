package com.tcb.sensenet.internal.map.edge;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.cytoscape.model.CyEdge;
import org.cytoscape.model.CyNode;

import com.tcb.sensenet.internal.app.AppColumns;
import com.tcb.sensenet.internal.labeling.LabelSettings;
import com.tcb.sensenet.internal.map.node.NodeNameMapper;
import com.tcb.sensenet.internal.meta.edge.EdgeType;
import com.tcb.sensenet.internal.meta.network.MetaNetwork;
import com.tcb.cytoscape.cyLib.data.DefaultColumns;

public class EdgeLocationMapper implements EdgeMapper {

	private MetaNetwork refMetaNetwork;
	private NodeNameMapper nodeMapper;

	public static EdgeLocationMapper create(MetaNetwork refMetaNetwork) {
		LabelSettings labelSettings = new LabelSettings();
		labelSettings.useNames = false;
		NodeNameMapper nodeMapper = NodeNameMapper.create(refMetaNetwork,labelSettings);
		return new EdgeLocationMapper(refMetaNetwork,nodeMapper);
	}
	
	public EdgeLocationMapper(MetaNetwork refMetaNetwork, NodeNameMapper nodeMapper) {
		this.refMetaNetwork = refMetaNetwork;
		this.nodeMapper = nodeMapper;
	}
	
	private String getInteractionType(CyEdge edge, MetaNetwork metaNetwork) {
		return metaNetwork.getRow(edge).get(DefaultColumns.SHARED_INTERACTION, String.class);
	}
	
	private String getBridgeName(CyEdge edge, MetaNetwork metaNetwork){
		List<String> bridgeNames = metaNetwork.getRow(edge)
				.getListMaybe(AppColumns.BRIDGE_NAME, String.class)
				.orElse(new ArrayList<>());
		return String.join(" ", bridgeNames); 
	}
	
	@Override
	public Optional<CyEdge> getMapped(CyEdge a, MetaNetwork metaNetwork) {
		Optional<CyNode> refSource = nodeMapper.getMapped(a.getSource(), metaNetwork);
		Optional<CyNode> refTarget = nodeMapper.getMapped(a.getTarget(), metaNetwork);
		if(!refSource.isPresent() || !refTarget.isPresent()) return Optional.empty();
		String type = getInteractionType(a,metaNetwork);
		String bridgeName = getBridgeName(a,metaNetwork);
		List<CyEdge> connectingRef = refMetaNetwork.getRootNetwork().getConnectingEdgeList(
				refSource.get(), refTarget.get(), CyEdge.Type.ANY)
				.stream()
				.filter(e -> getInteractionType(e,refMetaNetwork).equals(type))
				.filter(e -> getBridgeName(e,refMetaNetwork).equals(bridgeName))
				.collect(Collectors.toList());
		if(connectingRef.isEmpty()) return Optional.empty();
		else if(connectingRef.size() > 1) throw new IllegalArgumentException("More than one match for edge: " + a.toString());
		else return Optional.of(connectingRef.get(0));
	}
	

}
