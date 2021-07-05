package com.tcb.sensenet.internal.path.analysis.centrality;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;

import org.cytoscape.model.CyEdge;
import org.cytoscape.model.CyNode;

import com.tcb.sensenet.internal.analysis.normalization.NormalizationStrategy;
import com.tcb.sensenet.internal.app.AppGlobals;
import com.tcb.sensenet.internal.network.PrivateSubnetworkFactory;
import com.tcb.sensenet.internal.path.WeightedAveragePathLengthAnalyzer;
import com.tcb.sensenet.internal.path.analysis.centrality.distance.EdgeDistanceStrategy;
import com.tcb.sensenet.internal.path.analysis.centrality.weight.accumulation.WeightAccumulationStrategy;
import com.tcb.sensenet.internal.path.analysis.centrality.weight.negative.NegativeValuesStrategy;
import com.tcb.sensenet.internal.util.CancelledException;
import com.tcb.sensenet.internal.util.ObjMap;
import com.tcb.cytoscape.cyLib.cytoApiWrappers.CyNetworkAdapter;
import com.tcb.cytoscape.cyLib.cytoApiWrappers.CyRootNetworkAdapter;
import com.tcb.mdAnalysis.statistics.StandardStatistics;
import com.tcb.common.util.SafeMap;

public class WeightedPathLengthCentralityAnalyzer implements NodeCentralityAnalyzer {
	
	private WeightedAveragePathLengthAnalyzer plAnalyzer;
	private PrivateSubnetworkFactory networkFactory;
	private NormalizationStrategy normalizationStrategy;

	public WeightedPathLengthCentralityAnalyzer(
			PrivateSubnetworkFactory networkFactory,
			WeightAccumulationStrategy weightStrategy,
			EdgeDistanceStrategy distanceTransformationStrategy,
			NormalizationStrategy normalizationStrategy,
			NegativeValuesStrategy negativeWeightStrategy){
		this.plAnalyzer = new WeightedAveragePathLengthAnalyzer(
				weightStrategy,distanceTransformationStrategy,
				negativeWeightStrategy);
		this.networkFactory = networkFactory;
		this.normalizationStrategy = normalizationStrategy;
	}
	
	@Override
	public Map<CyNode,ObjMap> analyze(CyNetworkAdapter network){
		
		List<CyNode> nodes = network.getNodeList();
		final int size = nodes.size();
		double[] centralities = new double[size];
		double avgPathLength = getAveragePathLength(network);
		
		double[] avgPathLengthExcludes = nodes.stream()
				.mapToDouble(n -> getAveragePathLengthExcludingNode(network, n))
				.toArray();
				
		Map<CyNode,ObjMap> result = new SafeMap<>();
		for(int i=0;i<size;i++){
			double centrality = Math.abs(avgPathLengthExcludes[i] - avgPathLength);
			centralities[i] = centrality;
		}
		
		normalizationStrategy.normalize(centralities);
		
		for(int i=0;i<size;i++){
			ObjMap m = new ObjMap();
			m.put("centrality", centralities[i]);
			result.put(nodes.get(i), m);
		}
		
		return result;
	}
	
	private double getAveragePathLengthExcludingNode(CyNetworkAdapter network, CyNode node) {
		CyNetworkAdapter subNetwork = networkFactory.create(network);
		subNetwork.removeNodes(Arrays.asList(node));
		double result = getAveragePathLength(subNetwork);
		networkFactory.destroy(subNetwork);
		return result;
	}
	
	private double getAveragePathLength(CyNetworkAdapter network) {
		return plAnalyzer.analyze(network).get("averagePathLength", double.class);
	}

	@Override
	public void cancel() {
		plAnalyzer.cancel();		
	}
	
	
}
