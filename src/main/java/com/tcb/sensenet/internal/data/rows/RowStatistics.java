package com.tcb.sensenet.internal.data.rows;

import java.util.Collection;
import java.util.DoubleSummaryStatistics;

import com.tcb.cytoscape.cyLib.cytoApiWrappers.CyRowAdapter;

public class RowStatistics {
	private Collection<CyRowAdapter> rows;

	public RowStatistics(Collection<CyRowAdapter> rows){
		this.rows = rows;
	}
	
	public DoubleSummaryStatistics getDoubleColumnStatistics(String columnName){
		return rows.stream()
				.map(r -> r.getAdaptedRow().get(columnName, Double.class))
				.filter(d -> d!=null)
				.filter(d -> d!=Double.NaN)
				.mapToDouble(d -> d)
				.summaryStatistics();
	}
		
}
