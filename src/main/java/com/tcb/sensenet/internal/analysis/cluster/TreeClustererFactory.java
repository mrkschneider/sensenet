package com.tcb.sensenet.internal.analysis.cluster;

import java.util.Map;

import com.tcb.cluster.Clusterer;
import com.tcb.cluster.TreeClusterer;
import com.tcb.sensenet.internal.analysis.matrix.ContactMatrix;
import com.tcb.matrix.LabeledMatrix;

public interface TreeClustererFactory {
	public TreeClusterer create(LabeledMatrix<String> distances);
}
