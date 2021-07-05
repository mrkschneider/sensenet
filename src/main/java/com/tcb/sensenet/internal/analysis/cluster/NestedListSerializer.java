package com.tcb.sensenet.internal.analysis.cluster;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class NestedListSerializer<T> {
	private static final String innerDelimiter = ",";
	private static final String outerDelimiter = ";";
		
	public String serializeList(List<List<T>> clusters){
		return clusters.stream()
				.map(c -> serialize(c))
				.collect(Collectors.joining(outerDelimiter));
	}
	
	public String serialize(List<T> cluster){
		List<String> data = cluster.stream()
				.map(i -> i.toString())
				.collect(Collectors.toList());
		return String.join(innerDelimiter, data);
	}
	
	public List<List<T>> deserializeList(String clusters, Function<String,T> parser){
		return Stream.of(clusters.split(outerDelimiter))
				.map(s -> deserialize(s,parser))
				.collect(Collectors.toList());
	}
	
	public List<T> deserialize(String cluster, Function<String,T> parser){
		List<T> data = Stream.of(cluster.split(innerDelimiter))
				.map(s -> parser.apply(s))
				.collect(Collectors.toList());
		return data;
	}

}
