package com.tcb.sensenet.internal.analysis.matrix;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.cytoscape.model.CyNode;
import org.cytoscape.work.TaskMonitor;

import com.google.common.collect.ImmutableList;
import com.tcb.sensenet.internal.util.Cancellable;
import com.tcb.sensenet.internal.util.IntMapUtil;
import com.tcb.cytoscape.cyLib.util.ProgressTicker;
import com.tcb.matrix.LabeledMatrix;
import com.tcb.matrix.LabeledSquareMatrixImpl;
import com.tcb.matrix.Matrix;
import com.tcb.matrix.TriangularMatrix;
import com.tcb.common.util.Combinatorics;
import com.tcb.common.util.Tuple;

public class ContactDistanceMatrixFactory implements Cancellable {
	
	private volatile boolean cancelled = false;
	
	public ContactDistanceMatrixFactory(){

	}
		
	public LabeledMatrix<String> calcDistances(Map<Integer,ContactMatrix> contactMatrices){
		Tuple<List<Integer>,List<ContactMatrix>> packed = IntMapUtil.getSortedKeysValues(contactMatrices);
		List<String> labels = packed.one().stream()
				.map(i -> i.toString())
				.collect(Collectors.toList());
		List<ContactMatrix> matrices = packed.two();
				
		final int size = matrices.size();
		TriangularMatrix distances = new TriangularMatrix(size);
		List<Integer> indices = IntStream.range(0, size).boxed().collect(Collectors.toList());
        List<Tuple<Integer,Integer>> combinations = Combinatorics.getCombinationsNoSelf(
                indices);
     
        combinations.parallelStream()
        .forEach(c -> {
	        if(cancelled) return;
	        int i = c.one();
	        int j = c.two();
	        LabeledMatrix<CyNode> m1 = matrices.get(i);
	        LabeledMatrix<CyNode> m2 = matrices.get(j);
	        Matrix mm1 = m1.getMatrix().copy();
	        mm1.subtract(m2.getMatrix());  
	        Double distance = mm1.getFrobeniusNorm();
	        distances.set(i,j,distance);
        });
							
		return LabeledSquareMatrixImpl.create(labels, distances);
	}
	
	public void cancel(){
		cancelled = true;
	}

}
