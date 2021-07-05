package com.tcb.sensenet.internal.meta.timeline.factories;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.tcb.common.util.ListFilter;
import com.tcb.sensenet.internal.meta.timeline.MetaTimeline;


public abstract class AbstractMetaTimelineFactory implements MetaTimelineFactory {
		
	@Override
	public MetaTimeline createFromStrings(List<String> timelines){
		int timelineSize = ListFilter.singleton(
				timelines.stream().
				map(t -> t.length()).
				collect(Collectors.toSet())).get();
		double[][] timelinesArr = new double[timelines.size()][timelineSize];
		for(int i=0;i<timelines.size();i++){
			String timeline = timelines.get(i);
			for(int j=0;j<timelineSize;j++){
				double c = Double.valueOf(String.valueOf(timeline.charAt(j)));
				timelinesArr[i][j] = c;
			}
		}
		return create(timelinesArr);
	}
	
	@Override
	public MetaTimeline create(List<MetaTimeline> timelines){
		final int size = timelines.size();
		final int timelineLength = ListFilter.singleton(timelines.stream()
				.map(t -> t.getLength())
				.collect(Collectors.toSet()))
				.get();
		double[][] data = new double[size][timelineLength];
		for(int i=0;i<size;i++){
			MetaTimeline metaTimeline = timelines.get(i);
			for(int j=0;j<timelineLength;j++){
				data[i][j] = metaTimeline.get(j);
			}
		}
		return create(data);
	}
			
	protected double[] mergeTimelines(double[][] timelines){
		Set<Integer> lengths = Stream.of(timelines)
				.map(l -> l.length)
				.collect(Collectors.toSet());
		Integer length = ListFilter.singleton(lengths).orElseThrow(() -> 
			new IllegalArgumentException("Timelines must be of equal lengths"));
		double[] sums = new double[length];
		Arrays.fill(sums, 0f);
		for(int i=0;i<timelines.length;i++){
			for(int j=0;j<length;j++){
				sums[j] += timelines[i][j];
			}
		}
		return sums;
	}
				
	
}
