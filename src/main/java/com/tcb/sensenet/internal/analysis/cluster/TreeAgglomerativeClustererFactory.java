package com.tcb.sensenet.internal.analysis.cluster;

import java.util.List;
import java.util.Map;

import com.google.common.collect.ImmutableList;
import com.tcb.cluster.Clusterer;
import com.tcb.cluster.TreeClusterer;
import com.tcb.cluster.agglomerative.AgglomerativeClusterer;
import com.tcb.cluster.linkage.Linkage;
import com.tcb.sensenet.internal.analysis.matrix.ContactDistanceMatrixFactory;
import com.tcb.sensenet.internal.analysis.matrix.ContactMatrix;
import com.tcb.sensenet.internal.util.IntMapUtil;
import com.tcb.cytoscape.cyLib.log.LogBuilder;
import com.tcb.cytoscape.cyLib.log.ParameterReporter;
import com.tcb.matrix.LabeledMatrix;
import com.tcb.matrix.TriangularMatrix;
import com.tcb.common.util.Tuple;


public class TreeAgglomerativeClustererFactory implements TreeClustererFactory, ParameterReporter {

	private Linkage linkage;

	public TreeAgglomerativeClustererFactory(Linkage linkage){
		this.linkage = linkage;
	}
	
	private String getMethod(){
		return "Agglomerative";
	}

	@Override
	public String reportParameters() {
		LogBuilder log = new LogBuilder();
		log.write(String.format("Tree clustering: %s", getMethod()));
		log.write(linkage.reportParameters());
		return log.get();
	}
	
	@Override
	public TreeClusterer create(LabeledMatrix<String> distances) {
			return new AgglomerativeClusterer(distances,linkage);
		}
}

