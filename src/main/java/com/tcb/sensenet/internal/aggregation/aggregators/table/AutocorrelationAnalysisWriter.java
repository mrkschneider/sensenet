package com.tcb.sensenet.internal.aggregation.aggregators.table;

import java.util.Arrays;
import java.util.List;

import com.google.common.collect.ImmutableList;
import com.tcb.sensenet.internal.app.AppColumns;
import com.tcb.sensenet.internal.util.ObjMap;
import com.tcb.cytoscape.cyLib.cytoApiWrappers.CyRowAdapter;
import com.tcb.cytoscape.cyLib.data.Columns;

public class AutocorrelationAnalysisWriter extends DoubleWriter {

	public AutocorrelationAnalysisWriter(){
		super(
				Arrays.asList(
						AppColumns.AUTOCORRELATION_TIME,
						AppColumns.AUTOCORRELATION_SAMPLE_SIZE,
						AppColumns.ERROR_ESTIMATE
						),
				Arrays.asList(
						"autocorrelationTime",
						"effectiveSampleSize",
						"error"));
	}

}
