package com.tcb.sensenet.internal.path.analysis.centrality;

import java.util.List;
import java.util.Map;
import java.util.Stack;

import org.cytoscape.model.CyNode;

import com.tcb.sensenet.internal.analysis.normalization.NoNormalizationStrategy;
import com.tcb.sensenet.internal.path.analysis.centrality.distance.EdgeDistanceStrategy;
import com.tcb.sensenet.internal.path.analysis.centrality.weight.accumulation.WeightAccumulationStrategy;
import com.tcb.sensenet.internal.path.analysis.centrality.weight.negative.NegativeValuesStrategy;

public class WeightedNodeStressCentralityAnalyzer extends WeightedNodeBetweennessCentralityAnalyzer {
	
	public WeightedNodeStressCentralityAnalyzer(
			WeightAccumulationStrategy weightStrategy,
			EdgeDistanceStrategy distanceTransformationStrategy,
			NegativeValuesStrategy negativeWeightStrategy){
		super(
				weightStrategy,
				distanceTransformationStrategy,
				new NoNormalizationStrategy(),
				negativeWeightStrategy);
	}
	
	@Override
	protected void accumulate(
			CyNode source,
			int nodeCount,
			Stack<CyNode> stack,
			double[] shortestPathCounts,
			double[] distances,
			Map<CyNode,Integer> nodeIndices,
			List<List<CyNode>> predecessors,
			double[] centralities,
			double[] distancesOut){
		
		double[] dependencies = new double[nodeCount];
		while(!stack.isEmpty()){
			CyNode node = stack.pop();
			Integer index = nodeIndices.get(node);
			List<CyNode> nodePredecessors = predecessors.get(index);
			
			for(CyNode predecessor:nodePredecessors){
				Integer predecessorIndex = nodeIndices.get(predecessor);
				dependencies[predecessorIndex] += (1 + dependencies[index]);
			}
			if(!node.equals(source)){
				double increment = shortestPathCounts[index] * dependencies[index];
				centralities[index] += increment;
			}
		}
		
		assert(distances.length==distancesOut.length);
		for(int i=0;i<distancesOut.length;i++) {
			double d = distances[i];
			if(d == Double.POSITIVE_INFINITY) continue;
			distancesOut[i] += d;
		}
	}
		
}
