package com.tcb.sensenet.internal.util;

import java.util.ArrayList;
import java.util.List;

public class Sieve<T> {
	
	private int sieveStep;
	private List<Integer> sieveIndices;
	private List<T> sieved;

	public Sieve(List<T> lst, int sieveStep){
		verifyInput(lst,sieveStep);
		this.sieveStep = sieveStep;
		this.sieveIndices = getSieveIndices(lst,sieveStep);
		this.sieved = getSieved(sieveIndices,lst);
	}
	
	private void verifyInput(List<T> lst, int sieveStep){
		if(sieveStep < 1) throw new IllegalArgumentException("Sieve step must be >= 1");
	}
	
	private List<Integer> getSieveIndices(List<T> lst, int sieveStep){
		List<Integer> indices = new ArrayList<>();
		for(int i=0;i<lst.size();i+=sieveStep){
			indices.add(i);
		}
		return indices;
	}
	
	private List<T> getSieved(List<Integer> sieveIndices, List<T> lst){
		List<T> sieved = new ArrayList<>();
		for(Integer i:sieveIndices){
			sieved.add(lst.get(i));
		}
		return sieved;
	}
		
	public List<Integer> getSievedIndices(){
		return sieveIndices;
	}
	
	public List<T> getSieved(){
		return sieved;
	}
	
	public Integer getSievedSize(){
		return sieveIndices.size();
	}
			
}
