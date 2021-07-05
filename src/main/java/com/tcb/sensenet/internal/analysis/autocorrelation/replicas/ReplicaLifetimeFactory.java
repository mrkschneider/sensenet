package com.tcb.sensenet.internal.analysis.autocorrelation.replicas;

import java.util.List;

import com.tcb.mdAnalysis.statistics.autocorrelation.Autocorrelation;
import com.tcb.mdAnalysis.statistics.lifetime.Lifetime;

public class ReplicaLifetimeFactory extends ReplicaAutocorrelationFactory {

	public ReplicaLifetimeFactory(Double regressionLimit){
		super(regressionLimit);
	}
		
	@Override	
	protected Autocorrelation createAutocorrelation(List<Double> timeline){
		return new Lifetime(timeline);
	}
	
	
		
}
