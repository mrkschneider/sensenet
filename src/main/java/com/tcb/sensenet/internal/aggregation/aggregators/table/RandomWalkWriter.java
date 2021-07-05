package com.tcb.sensenet.internal.aggregation.aggregators.table;

import java.util.Arrays;
import java.util.List;

import com.tcb.sensenet.internal.app.AppColumns;
import com.tcb.cytoscape.cyLib.data.Columns;

public class RandomWalkWriter extends DoubleWriter {

	public RandomWalkWriter() {
		super(Arrays.asList(AppColumns.VISITED), Arrays.asList("visited"));
	}

}
