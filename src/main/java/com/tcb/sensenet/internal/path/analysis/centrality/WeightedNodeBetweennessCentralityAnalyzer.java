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
import com.tcb.sensenet.internal.path.analysis.centrality.distance.EdgeDistanceStrategy;
import com.tcb.sensenet.internal.path.analysis.centrality.weight.accumulation.WeightAccumulationStrategy;
import com.tcb.sensenet.internal.path.analysis.centrality.weight.negative.NegativeValuesStrategy;
import com.tcb.sensenet.internal.util.CancelledException;
import com.tcb.sensenet.internal.util.ObjMap;
import com.tcb.cytoscape.cyLib.cytoApiWrappers.CyNetworkAdapter;
import com.tcb.common.util.SafeMap;

public class WeightedNodeBetweennessCentralityAnalyzer implements NodeCentralityAnalyzer {
	
	private WeightAccumulationStrategy weightAccumulationStrategy;
	private NormalizationStrategy normalizationStrategy;
	private EdgeDistanceStrategy edgeDistanceStrategy;
	private NegativeValuesStrategy negativeWeightStrategy;
	private volatile boolean cancelled = false;
	
	public WeightedNodeBetweennessCentralityAnalyzer(
			WeightAccumulationStrategy weightStrategy,
			EdgeDistanceStrategy distanceTransformationStrategy,
			NormalizationStrategy normalizationStrategy,
			NegativeValuesStrategy negativeWeightStrategy){
		this.weightAccumulationStrategy = weightStrategy;
		this.edgeDistanceStrategy = distanceTransformationStrategy;
		this.normalizationStrategy = normalizationStrategy;
		this.negativeWeightStrategy = negativeWeightStrategy;
	}
	
	@Override
	public Map<CyNode,ObjMap> analyze(CyNetworkAdapter network){
		List<CyNode> nodes = network.getNodeList();
		final int size = nodes.size();
		double[] centralities = new double[size];
		double[] distances = new double[size];
		Map<CyNode,Integer> nodeIndices = createNodeIndexMap(network);
					
		for(int i=0;i<size;i++){
			if(cancelled) throw new CancelledException("Centrality analysis cancelled");
			incrementCentralities(i,nodeIndices, network,centralities, distances);
		}
				
		correctForUndirected(centralities);
		correctForUndirected(distances);
		
		normalizationStrategy.normalize(centralities);
		normalizationStrategy.normalize(distances);
		
		Map<CyNode,ObjMap> result = new SafeMap<>();
		for(int i=0;i<size;i++){
			ObjMap m = new ObjMap();
			m.put("centrality", centralities[i]);
			m.put("distance", distances[i]);
			result.put(nodes.get(i), m);
		}
		
		return result;
	}
	
	private Map<CyNode,Integer> createNodeIndexMap(CyNetworkAdapter network){
		Map<CyNode,Integer> indices = new SafeMap<>();
		List<CyNode> nodes = network.getNodeList();
		final int nodeCount = nodes.size();
		for(int i=0;i<nodeCount;i++){
			CyNode node = nodes.get(i);
			indices.put(node, i);
		}
		return indices;
	}
	
	private void incrementCentralities(
			int sourceIndex,
			Map<CyNode,Integer> nodeIndices,
			CyNetworkAdapter network,
			double[] centralitiesOut,
			double[] distancesOut){
		List<CyNode> nodes = network.getNodeList();
		final int nodeCount = nodes.size();
		CyNode source = nodes.get(sourceIndex);
				
		double[] distances = new double[nodeCount];
		double[] shortestPathCounts = new double[nodeCount];
						
		List<List<CyNode>> predecessors = new ArrayList<>();
		nodes.forEach(n -> predecessors.add(new ArrayList<>()));
		
		Arrays.fill(distances, Double.POSITIVE_INFINITY);
		distances[sourceIndex] = 0d;
		shortestPathCounts[sourceIndex] = 1;
		
		Queue<CyNode> queue = new PriorityQueue<>(
				new DistanceComparator(nodeIndices, distances));
		Stack<CyNode> stack = new Stack<>();
		
		queue.add(source);
		
		while(!queue.isEmpty()){
			CyNode node = queue.poll();
			stack.push(node);
			Integer index = nodeIndices.get(node);
			Set<CyNode> neighbors = new LinkedHashSet<>(
					network.getNeighborList(node, CyEdge.Type.ANY));
			for(CyNode neighbor:neighbors){
				Integer neighborIndex = nodeIndices.get(neighbor);
				Double edgesDistance = getDistance(node, neighbor, network);
								
				if(distances[neighborIndex] > distances[index] + edgesDistance){
					distances[neighborIndex] = distances[index] + edgesDistance;
					queue.remove(neighbor);
					queue.add(neighbor);
					shortestPathCounts[neighborIndex] = 0;
					predecessors.get(neighborIndex).clear();
				}
		
				if(distances[neighborIndex] == distances[index] + edgesDistance){
					shortestPathCounts[neighborIndex] += shortestPathCounts[index];
					predecessors.get(neighborIndex).add(node);
				}
								
			}
		}
						
		accumulate(
				source,nodeCount,
				stack,shortestPathCounts,distances,
				nodeIndices,predecessors,
				centralitiesOut,distancesOut);
	}
	
	private Double getDistance(CyNode a, CyNode b, CyNetworkAdapter network){
		List<CyEdge> connectingEdges = 
				network.getConnectingEdgeList(a, b, CyEdge.Type.ANY);
		Double edgesWeight = negativeWeightStrategy.transform(
				weightAccumulationStrategy.weight(connectingEdges));
		Double edgesDistance = edgeDistanceStrategy.getDistance(
				edgesWeight);
		if(edgesDistance < 0.0) throw new RuntimeException("No negative weights allowed");
		return edgesDistance;
	}
	
	protected void accumulate(
			CyNode source,
			int nodeCount,
			Stack<CyNode> stack,
			double[] shortestPathCounts,
			double[] distances,
			Map<CyNode,Integer> nodeIndices,
			List<List<CyNode>> predecessors,
			double[] centralitiesOut,
			double[] distancesOut){
		
		double[] dependencies = new double[nodeCount];
		while(!stack.isEmpty()){
			CyNode node = stack.pop();
			Integer index = nodeIndices.get(node);
			List<CyNode> nodePredecessors = predecessors.get(index);
			
			for(CyNode predecessor:nodePredecessors){
				Integer predecessorIndex = nodeIndices.get(predecessor);
				double s = ((double)shortestPathCounts[predecessorIndex]) / shortestPathCounts[index];
				dependencies[predecessorIndex] += s * (1 + dependencies[index]);
			}
			if(!node.equals(source)){
				centralitiesOut[index] += dependencies[index];
			}
		}
		
		assert(distances.length==distancesOut.length);
		for(int i=0;i<distancesOut.length;i++) {
			double d = distances[i];
			if(d == Double.POSITIVE_INFINITY) continue;
			distancesOut[i] += d;
		}
	}
	
	protected void correctForUndirected(double[] centralities){
		for(int i=0;i<centralities.length;i++){
			centralities[i] *= 0.5;
		}
	}
	
	
	public void cancel(){
		cancelled = true;
	}
	
	private class DistanceComparator implements Comparator<CyNode>{

		private Map<CyNode, Integer> nodeIndices;
		private double[] distances;

		public DistanceComparator(Map<CyNode,Integer> nodeIndices, double[] distances){
			this.nodeIndices = nodeIndices;
			this.distances = distances;
		}
		
		@Override
		public int compare(CyNode a, CyNode b) {
			double distanceA = distances[nodeIndices.get(a)];
			double distanceB = distances[nodeIndices.get(b)];
			return Double.compare(distanceA, distanceB);
		}
		
	}
}
