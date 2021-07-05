package com.tcb.sensenet.internal.aggregation.aggregators.table;

import java.util.Arrays;
import java.util.List;

import com.google.common.collect.ImmutableList;
import com.tcb.sensenet.internal.aggregation.aggregators.edges.timeline.TimelineWeightAnalysis;
import com.tcb.sensenet.internal.util.ObjMap;
import com.tcb.cytoscape.cyLib.cytoApiWrappers.CyRowAdapter;
import com.tcb.cytoscape.cyLib.data.Columns;

public class WeightWriter extends DoubleWriter {
	
	public WeightWriter(Columns weightColumn, Columns stdColumn){
		super(Arrays.asList(weightColumn,stdColumn),Arrays.asList("mean","std"));
	}
		
}
