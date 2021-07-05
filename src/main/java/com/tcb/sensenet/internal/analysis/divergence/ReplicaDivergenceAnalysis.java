package com.tcb.sensenet.internal.analysis.divergence;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import com.tcb.sensenet.internal.analysis.correlation.Frequencies;
import com.tcb.sensenet.internal.analysis.correlation.IntFrequencies;
import com.tcb.sensenet.internal.analysis.correlation.IntProbabilities;
import com.tcb.sensenet.internal.analysis.correlation.Probabilities;
import com.tcb.sensenet.internal.meta.timeline.MetaTimeline;
import com.tcb.sensenet.internal.util.ListUtil;
import com.tcb.sensenet.internal.util.ObjMap;
import com.tcb.mdAnalysis.math.integration.ExponentialDecayIntegrator;
import com.tcb.mdAnalysis.math.integration.Integrator;
import com.tcb.mdAnalysis.statistics.regression.Regression;
import com.tcb.mdAnalysis.statistics.regression.WeightedExponentialRegression;
import com.tcb.common.util.ListFilter;

public class ReplicaDivergenceAnalysis {
	
	private DivergenceStrategy divergenceStrategy;
	
	public ReplicaDivergenceAnalysis(
			DivergenceStrategy divergenceStrategy) {
		this.divergenceStrategy = divergenceStrategy;
	}
	
	public ObjMap analyse(
			MetaTimeline metaTimeline,
			Integer replicas,
			Double convergenceLimit) {
		if(replicas <= 1) {
			throw new IllegalArgumentException("Cannot perform divergence analysis for less than 2 replicas");
		}
		int replicaLength = metaTimeline.getLength() / replicas;
		List<Double> divergences = new ArrayList<>();
		ObjMap result = new ObjMap();
				
		List<Frequencies<Integer>> freqs = IntStream.range(0,replicas)
				.mapToObj(i -> IntFrequencies.create())
				.collect(Collectors.toList());
		
		List<Frequencies<Integer>> refFreqs = new ArrayList<>();
		for(int i=0;i<replicas;i++) {
			Frequencies<Integer> f = IntFrequencies.create();
			int offset = i * replicaLength;
			for(int j=0;j<replicaLength;j++) {
				f.add(metaTimeline.getInt(offset + j));
			}
			refFreqs.add(f);
		}
				
		for(int i=0;i<replicaLength;i++) {
			for(int j=0;j<replicas;j++) {
				freqs.get(j).add(metaTimeline.getInt(i + j*replicaLength));
			}
			List<Double> repDivs = new ArrayList<>();
			for(int j=0;j<replicas;j++) {
				for(int k=j+1;k<replicas;k++) {
					Double div = divergenceStrategy.calculate(
							freqs.get(j), refFreqs.get(k));
					repDivs.add(div);
				}
			}
			divergences.add(repDivs.stream().mapToDouble(d -> d).average().getAsDouble());
		}
						
		Regression regression = new WeightedExponentialRegression(divergences);
		Double root = ListFilter.singleton(regression.getRoots(convergenceLimit)).get();
		
		if(root <= 0)
			root = 1.0;
		if(regression.getB() >= 0)
			root = null;
								
		result.put("divergences", divergences);
		result.put("divergencesRegression", regression);
		result.put("divergencesRegressionRoot", root);
		return result;
	}
}
