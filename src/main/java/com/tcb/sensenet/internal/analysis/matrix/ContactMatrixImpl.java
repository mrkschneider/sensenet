package com.tcb.sensenet.internal.analysis.matrix;

import java.util.List;
import java.util.Map;

import org.cytoscape.model.CyNode;

import com.tcb.matrix.LabeledMatrix;
import com.tcb.matrix.LabeledSquareMatrixImpl;
import com.tcb.matrix.Matrix;

public class ContactMatrixImpl implements ContactMatrix {

	private LabeledMatrix<CyNode> matrix;
	
	public ContactMatrixImpl(LabeledMatrix<CyNode> matrix){
		this.matrix = matrix;
	}
	
	@Override
	public Double get(CyNode a, CyNode b) {
		return matrix.get(a, b);
	}

	@Override
	public CyNode getLabel(Integer index) {
		return matrix.getLabel(index);
	}

	@Override
	public Integer getIndex(CyNode a) {
		return matrix.getIndex(a);
	}

	@Override
	public void set(CyNode a, CyNode b, double value) {
		matrix.set(a, b, value);		
	}

	@Override
	public Matrix getMatrix() {
		return matrix.getMatrix();
	}

	@Override
	public int getRowCount() {
		return matrix.getRowCount();
	}

	@Override
	public int getColumnCount() {
		return matrix.getColumnCount();
	}

	@Override
	public double[][] getData(List<CyNode> nodes) {
		final int size = nodes.size();
		double[][] result = new double[size][size];
		for(int i=0;i<size;i++){
			for(int j=0;j<size;j++){
				CyNode a = nodes.get(i);
				CyNode b = nodes.get(j);
				result[i][j] = matrix.get(a, b);
			}
		}
		return result;
	}

}
