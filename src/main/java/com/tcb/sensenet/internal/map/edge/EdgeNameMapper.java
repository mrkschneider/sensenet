package com.tcb.sensenet.internal.map.edge;

import java.util.Map;
import java.util.Optional;

import org.cytoscape.model.CyEdge;

import com.tcb.sensenet.internal.meta.network.MetaNetwork;
import com.tcb.cytoscape.cyLib.data.DefaultColumns;
import com.tcb.cytoscape.cyLib.util.MapUtil;


public class EdgeNameMapper implements EdgeMapper {
	
	private Map<String, CyEdge> edgeNameMap;
	
	public static EdgeNameMapper create(
			MetaNetwork refMetaNetwork){
		Map<String,CyEdge> edgeNameMap = MapUtil.createMap(
				refMetaNetwork.getEdges(),
				(e) -> getName(e,refMetaNetwork)
				);
		return new EdgeNameMapper(edgeNameMap);
	}
	
	private EdgeNameMapper(Map<String,CyEdge> edgeNameMap){
		this.edgeNameMap = edgeNameMap;
	}

	@Override
	public Optional<CyEdge> getMapped(CyEdge a, MetaNetwork metaNetwork) {
		String name = getName(a,metaNetwork);
		if(edgeNameMap.containsKey(name)) return Optional.of(edgeNameMap.get(name));
		else return Optional.empty();
	}
	
	private static String getName(CyEdge e, MetaNetwork metaNetwork){
		return metaNetwork.getRow(e).get(DefaultColumns.SHARED_NAME, String.class);
	}
}
