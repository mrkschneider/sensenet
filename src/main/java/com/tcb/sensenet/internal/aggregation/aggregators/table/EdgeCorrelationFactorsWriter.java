package com.tcb.sensenet.internal.aggregation.aggregators.table;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.cytoscape.model.CyEdge;

import com.google.common.collect.ImmutableList;
import com.tcb.sensenet.internal.app.AppColumns;
import com.tcb.sensenet.internal.util.ObjMap;
import com.tcb.cytoscape.cyLib.cytoApiWrappers.CyNetworkAdapter;
import com.tcb.cytoscape.cyLib.cytoApiWrappers.CyRowAdapter;
import com.tcb.cytoscape.cyLib.data.Columns;
import com.tcb.cytoscape.cyLib.data.DefaultColumns;

public class EdgeCorrelationFactorsWriter extends DoubleWriter {

	public EdgeCorrelationFactorsWriter(){
		super(Arrays.asList(AppColumns.CORRELATION_FACTOR),Arrays.asList("correlationFactor"));
	}
		
}
