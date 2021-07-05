package com.tcb.sensenet.internal.analysis.path;

import com.tcb.sensenet.internal.meta.network.MetaNetwork;
import com.tcb.sensenet.internal.meta.timeline.TimelineManager;
import com.tcb.sensenet.internal.path.Path;
import com.tcb.sensenet.internal.path.weight.OccurencePathWeighter;
import com.tcb.sensenet.internal.path.weight.PathWeighter;
import com.tcb.sensenet.internal.path.weight.SumPathWeighter;
import com.tcb.sensenet.internal.util.ObjMap;
import com.tcb.cytoscape.cyLib.cytoApiWrappers.CyNetworkAdapter;

public class PathWeightAnalysis {

	public static ObjMap calculate(Path path, MetaNetwork metaNetwork, CyNetworkAdapter network, TimelineManager timelineManager){
		Double sumWeight = calcSumWeight(path,metaNetwork,network, timelineManager);
		Double occWeight = calcOccurenceWeight(path,metaNetwork,network, timelineManager);
		ObjMap result = new ObjMap();
		result.put("sumWeight", sumWeight);
		result.put("occWeight", occWeight);
		return result;
	}
		
	private static Double calcSumWeight(Path path, MetaNetwork metaNetwork, CyNetworkAdapter network, TimelineManager timelineManager){
		PathWeighter weighter = new SumPathWeighter(metaNetwork,network, timelineManager);
		return weighter.getWeight(path);
	}
	
	private static Double calcOccurenceWeight(Path path, MetaNetwork metaNetwork, CyNetworkAdapter network, TimelineManager timelineManager){
		PathWeighter weighter = new OccurencePathWeighter(metaNetwork,network, timelineManager);
		return weighter.getWeight(path);
	}
	

}
