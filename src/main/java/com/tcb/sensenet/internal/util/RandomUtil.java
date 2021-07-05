package com.tcb.sensenet.internal.util;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class RandomUtil {

	private Random rnd;
	
	public RandomUtil(){
		this.rnd = new Random();
		
	}
	
	public void setSeed(Long seed){
		rnd.setSeed(seed);
	}
	
	public Random getAdapted(){
		return rnd;
	}
	
	public <T> T pickRandom(List<T> coll, List<Double> weights){
		if(coll.size() == 0) throw new IllegalArgumentException("Collection is empty");
		if(!(coll.size()==weights.size())) 
			throw new IllegalArgumentException("Collection and weights do not have the same size");
		List<Double> cumSums = CumSum.getCumSums(weights);
		double max = cumSums.stream().mapToDouble(d -> d).max().getAsDouble();
		
		Double r = 1.0 - rnd.nextDouble();
		for(int i=0;i<cumSums.size();i++){
			if(r < cumSums.get(i) / max) return coll.get(i);
		}
		throw new RuntimeException("Failure during picking; Should never happen.");
	}
	
	public <T> T pickRandom(List<T> coll){
		Double[] weights = new Double[coll.size()];
		Arrays.fill(weights, 1.);
		return pickRandom(coll,Arrays.asList(weights));
	}
	
	
	
}
