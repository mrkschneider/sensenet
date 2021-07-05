package com.tcb.sensenet.internal.path;

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

import com.tcb.sensenet.internal.analysis.normalization.NoNormalizationStrategy;
import com.tcb.sensenet.internal.analysis.normalization.NormalizationStrategy;
import com.tcb.sensenet.internal.app.AppGlobals;
import com.tcb.sensenet.internal.network.PrivateSubnetworkFactoryImpl;
import com.tcb.sensenet.internal.path.analysis.centrality.distance.EdgeDistanceStrategy;
import com.tcb.sensenet.internal.path.analysis.centrality.weight.accumulation.WeightAccumulationStrategy;
import com.tcb.sensenet.internal.path.analysis.centrality.weight.negative.NegativeValuesStrategy;
import com.tcb.sensenet.internal.util.CancelledException;
import com.tcb.sensenet.internal.util.ObjMap;
import com.tcb.cytoscape.cyLib.cytoApiWrappers.CyNetworkAdapter;
import com.tcb.cytoscape.cyLib.cytoApiWrappers.CyRootNetworkAdapter;
import com.tcb.mdAnalysis.statistics.StandardStatistics;
import com.tcb.common.util.SafeMap;

public class WeightedAveragePathLengthAnalyzer implements AveragePathLengthAnalyzer {
	
	private WeightedNodeBetweennessCentralityAnalyzer bcAnalyzer;

	public WeightedAveragePathLengthAnalyzer(
			WeightAccumulationStrategy weightStrategy,
			EdgeDistanceStrategy distanceTransformationStrategy,
			NegativeValuesStrategy negativeWeightStrategy){
		this.bcAnalyzer = new WeightedNodeBetweennessCentralityAnalyzer(
				weightStrategy,distanceTransformationStrategy,
				new NoNormalizationStrategy(), negativeWeightStrategy);
	}
	
	@Override
	public ObjMap analyze(CyNetworkAdapter network){
		double avgPathLength = getAveragePathLength(network);
		ObjMap result = new ObjMap();
		result.put("averagePathLength", avgPathLength);
		return result;
	}
		
	private double getAveragePathLength(CyNetworkAdapter network) {
		Map<CyNode,ObjMap> ana = bcAnalyzer.analyze(network);
		final int n = network.getNodeList().size();
		double sum = ana.values().stream()
				.mapToDouble(m -> m.get("distance",double.class))
				.sum();
		double avg = sum / (0.5 * (n * (n - 1)));
		return avg;			
	}

	@Override
	public void cancel() {
		bcAnalyzer.cancel();		
	}
	
	
}
