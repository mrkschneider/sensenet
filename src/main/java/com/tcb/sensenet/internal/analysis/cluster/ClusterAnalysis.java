package com.tcb.sensenet.internal.analysis.cluster;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

import com.google.common.collect.ImmutableList;
import com.tcb.cluster.Cluster;
import com.tcb.matrix.TriangularMatrix;
import com.tcb.common.util.SafeMap;

public class ClusterAnalysis {
	private List<Cluster> clusters;

	public ClusterAnalysis(List<Cluster> clusters){
		this.clusters = clusters;
	}
	
	public List<Integer> getCounts(){
		return clusters.stream()
				.map(c -> c.getData().size())
				.collect(Collectors.toList());
	}
	
	public Integer getTotalCount(){
		return (int) getCounts().stream()
				.collect(Collectors.summarizingInt(i -> i))
				.getSum();
	}
	
	public List<Integer> getCentroids(){
		return clusters.stream()
				.map(c -> c.getCentroid())
				.map(s -> Integer.valueOf(s))
				.collect(Collectors.toList());
	}
	
	public List<Cluster> getClusters(){
		return clusters;
	}
	
	public Integer getClusterCount(){
		return clusters.size();
	}
	
	public Map<Integer,Integer> getClusterTimeline(){
		SafeMap<Integer,Integer> timeline = new SafeMap<>();
		for(int clusterIdx=0;clusterIdx<clusters.size();clusterIdx++){
			Cluster cluster = clusters.get(clusterIdx);
			for(String frame:cluster.getData()){
				timeline.put(Integer.valueOf(frame), clusterIdx);
			}
		}		
		return timeline;
	}
	
	public int[][] getTransitionMatrix(){
		Map<Integer,Integer> timeline = getClusterTimeline();
		final int size = clusters.size();
		int[][] result = new int[size][size];
		List<Integer> keys = timeline.keySet().stream()
				.sorted()
				.collect(ImmutableList.toImmutableList());
		final int lastIndex = keys.size() - 1;
		for(int i=0;i<lastIndex;i++){
			Integer frame = keys.get(i);
			Integer nextFrame = keys.get(i+1);
			Integer clusterIdx = timeline.get(frame);
			Integer nextClusterIdx = timeline.get(nextFrame);
			result[clusterIdx][nextClusterIdx] += 1;
		}
		return result;
	}
			
	public Double getCompensatedFlux(){
		int[][] transitionMatrix = getTransitionMatrix();
		Integer totalCount = getTotalCount();
		
		int[] clusterCounts = clusters.stream()
				.map(c -> c.getData().size())
				.mapToInt(i -> i)
				.toArray();
		
		int flux = 0;
		for(int i=0;i<clusters.size();i++){
			int p1 = clusterCounts[i];
			for(int j=i+1;j<clusters.size();j++){
				int k1 = transitionMatrix[i][j];
				int k2 = transitionMatrix[j][i];
				int p2 = clusterCounts[j];
				int inFlux = p1*k1;
				int outFlux = p2*k2;
				flux += Math.min(inFlux, outFlux);
			}
		}
		double result = ((double)flux) / totalCount;
		return result;
		
	}
	
	public Double getSumOfSquaredErrors(){
		return clusters.stream()
				.map(c -> c.getSquaredCentroidError())
				.mapToDouble(d -> d)
				.sum();
	}
	
	public List<Integer> getClusterSizes(){
		return clusters.stream()
				.map(c -> c.getData().size())
				.collect(Collectors.toList());
	}
	
}
