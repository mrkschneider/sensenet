package com.tcb.sensenet.internal.aggregation.aggregators.table;

import java.util.Arrays;
import java.util.List;

import com.tcb.sensenet.internal.app.AppColumns;
import com.tcb.cytoscape.cyLib.data.Columns;

public class NodeCentralityWriter extends DoubleWriter {

	public NodeCentralityWriter() {
		super(Arrays.asList(AppColumns.CENTRALITY), Arrays.asList("centrality"));
	}
	
	@Override
	public Integer getRoundingDigits() {
		return 3;
	}

}
