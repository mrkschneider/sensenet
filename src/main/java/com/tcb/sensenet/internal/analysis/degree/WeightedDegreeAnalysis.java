package com.tcb.sensenet.internal.analysis.degree;

import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.MapUtils;
import org.cytoscape.model.CyNode;

import com.tcb.cytoscape.cyLib.cytoApiWrappers.CyNetworkAdapter;
import com.tcb.common.util.SafeMap;
import com.tcb.sensenet.internal.analysis.normalization.NormalizationStrategy;
import com.tcb.sensenet.internal.path.analysis.centrality.weight.negative.NegativeValuesStrategy;
import com.tcb.sensenet.internal.util.ObjMap;

public class WeightedDegreeAnalysis {
	
	private WeightedDegreeStrategy weightStrategy;
	private NormalizationStrategy normalizationStrategy;

	public WeightedDegreeAnalysis(
			WeightedDegreeStrategy weightStrategy,
			NormalizationStrategy normalizationStrategy){
		this.weightStrategy = weightStrategy;
		this.normalizationStrategy = normalizationStrategy;
	}
	
	public ObjMap analyse(CyNetworkAdapter network){
		Map<CyNode,Double> degrees = getDegrees(network);
		ObjMap results = new ObjMap();
		results.put("degrees", degrees);
		return results;
	}
	
	private Map<CyNode,Double> getDegrees(CyNetworkAdapter network){
		Map<CyNode,Double> result = new SafeMap<>();
		List<CyNode> nodes = network.getNodeList();
		double[] degrees = nodes.stream()
				.map(n -> weightStrategy.getDegree(n, network))
				.mapToDouble(d -> d)
				.toArray();
		normalizationStrategy.normalize(degrees);
		for(int i=0;i<nodes.size();i++){
			result.put(nodes.get(i), degrees[i]);
		}
		return result;
	}
}
