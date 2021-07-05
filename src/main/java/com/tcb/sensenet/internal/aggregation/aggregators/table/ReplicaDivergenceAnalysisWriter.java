package com.tcb.sensenet.internal.aggregation.aggregators.table;

import java.util.Arrays;
import java.util.List;

import com.google.common.collect.ImmutableList;
import com.tcb.sensenet.internal.app.AppColumns;
import com.tcb.sensenet.internal.util.ObjMap;
import com.tcb.cytoscape.cyLib.cytoApiWrappers.CyRowAdapter;
import com.tcb.cytoscape.cyLib.data.Columns;
import com.tcb.mdAnalysis.statistics.regression.Regression;

public class ReplicaDivergenceAnalysisWriter extends DoubleWriter {
	
	public ReplicaDivergenceAnalysisWriter(){
		super(Arrays.asList(
				AppColumns.DIVERGENCE,
				AppColumns.CONVERGENCE_TIME),
			  Arrays.asList("divergences","divergencesRegressionRoot"));
	}
	
	@Override
	public void write(CyRowAdapter row, ObjMap analysis) {
		List<Double> divergences = analysis.getList(keys.get(0), Double.class);
		// Last corresponds to full replicas
		Double divergence = divergences.get(divergences.size()-1);		
		row.set(columns.get(0), round(divergence));
		
		Double root = analysis.get(keys.get(1), Double.class);
		row.set(columns.get(1), round(root));
	}
	
}
