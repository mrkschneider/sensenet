package com.tcb.sensenet.internal.analysis.diffusion;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.cytoscape.model.CyNode;

import com.tcb.sensenet.internal.util.CancelledException;
import com.tcb.sensenet.internal.util.ObjMap;
import com.tcb.cytoscape.cyLib.cytoApiWrappers.CyNetworkAdapter;

public class RandomWalk {
	
	public boolean cancelled = false;
	
	public ObjMap perform(CyNetworkAdapter network, CyNode source,
			WalkStrategy walkStrategy, Integer numRuns){
		
		List<ObjMap> walks = IntStream.range(0,numRuns)
				.boxed().unordered().parallel()
				.map(i -> perform(network,source,walkStrategy))
				.collect(Collectors.toList());
		
		ObjMap result = merge(walks);
		
		return result;
	}
	
	private ObjMap perform(CyNetworkAdapter network,
			CyNode source, WalkStrategy walkStrategy){
		Map<CyNode,Long> visited = new HashMap<>();
		for(CyNode n:network.getNodeList()) visited.put(n, 0l);
		
		WalkStrategy.Run walker = walkStrategy.createRun();
		CyNode node = source;
		while(node!=null){
			if(cancelled) throw new CancelledException("Cancelled");
			node = walker.next(network, node);
		}
		for(CyNode n:walker.getVisited()) 
			visited.compute(n, (k,v) -> v + 1);
		ObjMap m = new ObjMap();
		m.put("visited", visited);
		return m;
	}
	
	private ObjMap merge(List<ObjMap> walks){
		@SuppressWarnings("unchecked")
		Map<CyNode,Long> visited =  walks.stream()
				.map(w -> w.get("visited",Map.class))
				.flatMap(v -> ((Map<CyNode, Long>)v).entrySet().stream())
				.collect(Collectors.toMap(
						Map.Entry::getKey,
						Map.Entry::getValue,
						(v1,v2) -> v1 + v2));
		ObjMap m = new ObjMap();
		m.put("visited",visited);
		return m;
	}

}
