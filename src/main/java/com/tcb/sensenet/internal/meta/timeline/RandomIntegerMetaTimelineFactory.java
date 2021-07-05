package com.tcb.sensenet.internal.meta.timeline;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.commons.math3.distribution.EnumeratedDistribution;
import org.apache.commons.math3.util.Pair;

import com.tcb.matrix.TriangularMatrix;

public class RandomIntegerMetaTimelineFactory {

	private TriangularMatrix transitionProbs;
	private List<EnumeratedDistribution<Integer>> transitionDistributions;
	private Integer length;
	private Integer startState;

	public RandomIntegerMetaTimelineFactory(
			Integer startState,
			Integer length,
			TriangularMatrix transitionProbs){
		this.startState = startState;
		this.length = length;
		this.transitionProbs = transitionProbs;
		this.transitionDistributions = createDistributions(transitionProbs);
	}
		
	public MetaTimeline create(){
		double[] timeline = new double[length];
		timeline[0] = startState;
		for(int i=1;i<length;i++){
			timeline[i] = getNextState((int)timeline[i-1]); 
		}
		return MetaTimelineImpl.create(timeline);
	}
	
	private int getNextState(int currentState){
		EnumeratedDistribution<Integer> dist = transitionDistributions.get(currentState);
		int nextState = dist.sample();
		return nextState;
	}
	
	private static List<EnumeratedDistribution<Integer>> createDistributions(TriangularMatrix transitionProbs){
		List<EnumeratedDistribution<Integer>> dists = new ArrayList<>();
		for(int i=0;i<transitionProbs.getDim();i++){
			List<Pair<Integer,Double>> pmf = new ArrayList<>();
			for(int j=0;j<transitionProbs.getDim();j++){
				int idxA = Math.min(i, j);
				int idxB = Math.max(i, j);
				Pair<Integer,Double> pair = Pair.create(j, transitionProbs.get(idxA,idxB));
				pmf.add(pair);
			}
			EnumeratedDistribution<Integer> dist = new EnumeratedDistribution<>(pmf);
			dists.add(dist);
		}
		return dists;
	}
	
}
