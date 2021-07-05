package com.tcb.sensenet.internal.analysis.cluster;

import java.util.Map;

import com.tcb.cluster.Clusterer;
import com.tcb.sensenet.internal.analysis.matrix.ContactMatrix;
import com.tcb.cytoscape.cyLib.log.ParameterReporter;
import com.tcb.matrix.LabeledMatrix;
import com.tcb.matrix.TriangularMatrix;


public interface ClustererFactory extends ParameterReporter {
	public Clusterer create(LabeledMatrix<String> distances);
}
