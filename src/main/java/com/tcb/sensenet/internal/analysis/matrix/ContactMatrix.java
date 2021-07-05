package com.tcb.sensenet.internal.analysis.matrix;

import java.util.List;

import org.cytoscape.model.CyNode;

import com.tcb.matrix.LabeledMatrix;

public interface ContactMatrix extends LabeledMatrix<CyNode> {
	public double[][] getData(List<CyNode> nodes);
}
