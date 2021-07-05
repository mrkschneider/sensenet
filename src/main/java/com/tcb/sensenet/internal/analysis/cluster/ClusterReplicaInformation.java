package com.tcb.sensenet.internal.analysis.cluster;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.google.common.collect.ImmutableList;
import com.tcb.cluster.Cluster;
import com.tcb.common.util.SafeMap;
import com.tcb.sensenet.internal.analysis.correlation.mutualInformation.MutualInformation;
import com.tcb.sensenet.internal.util.iterator.IntIterable;

public class ClusterReplicaInformation {
	
	private final int blocks;

	public ClusterReplicaInformation(Integer blocks){
		this.blocks = blocks;
	}
	
	public double calculate(List<Cluster> clusters){
		List<String> frames = clusters.stream()
				.map(c -> c.getData())
				.flatMap(l -> l.stream())
				.sorted()
				.map(i -> i.toString())
				.collect(ImmutableList.toImmutableList());
		
		Map<String,Integer> frameToIndex = new SafeMap<>();
		for(Integer i=0;i<frames.size();i++){
			String frame = frames.get(i);
			frameToIndex.put(frame, i);
		}
				
		final int frameCount = frames.size();
		if(frameCount % blocks != 0) throw new IllegalArgumentException("Replicas must be a factor of frame count in clusters");
		final int framesPerReplica = frameCount / blocks;
		
		int[] replicaIndices = new int[frameCount];
		int[] clusterIndices = new int[frameCount];
		
		
		for(int i=0;i<blocks;i++){
			for(int j=0;j<framesPerReplica;j++){
				int idx = framesPerReplica * i + j;
				replicaIndices[idx] = i;
			}
		}
		
		for(int i=0;i<clusters.size();i++){
			Cluster cluster = clusters.get(i);
			for(String frame:cluster.getData()){
				Integer idx = frameToIndex.get(frame);
				clusterIndices[idx] = i;
			}
		}
		return new MutualInformation().calculate(
				IntIterable.of(replicaIndices),
				IntIterable.of(clusterIndices));
		
	}
}
