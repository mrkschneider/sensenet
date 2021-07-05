package com.tcb.sensenet.internal.path;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.cytoscape.model.CyNode;

import com.google.common.collect.ImmutableList;

public class Path {
	private ImmutableList<CyNode> nodes;
	private Set<Long> nodeSuids;

	public Path(List<CyNode> nodes){
		if(nodes.size() < 1) throw new IllegalArgumentException("Path cannot be empty");
		this.nodes = ImmutableList.copyOf(nodes);
		this.nodeSuids = nodes.stream()
				.map(n -> n.getSUID())
				.collect(Collectors.toSet());
	}
	
	public Path(CyNode node){
		this(Arrays.asList(node));
	}
	
	public Path add(CyNode node){	
		List<CyNode> newNodes = ImmutableList.<CyNode>builder()
				.addAll(nodes)
				.add(node)
				.build();
		return new Path(newNodes);
	}
	
	public ImmutableList<CyNode> getNodes(){
		return nodes;
	}
	
	public CyNode getLast(){
		return nodes.get(nodes.size()-1);
	}
	
	public CyNode getFirst() {
		return nodes.get(0);
	}
	
	public Boolean contains(CyNode node){
		return nodeSuids.contains(node.getSUID());
	}
	
	public Integer getLength() {
		return nodes.size() - 1;
	}
	
	@Override
	public String toString() {
		List<String> suids = nodes.stream().map(n -> n.getSUID().toString()).collect(Collectors.toList());
		return String.join(",", suids);
	}
}
