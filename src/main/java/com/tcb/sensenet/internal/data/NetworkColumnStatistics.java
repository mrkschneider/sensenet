package com.tcb.sensenet.internal.data;

import java.util.List;

import com.google.common.collect.ImmutableList;
import com.tcb.cytoscape.cyLib.cytoApiWrappers.CyTableAdapter;

public class NetworkColumnStatistics {
	private CyTableAdapter table;

	public NetworkColumnStatistics(CyTableAdapter table){
		this.table = table;
	}
	
	public List<String> getColumns(Class<?> type){
		return table.getColumns().stream()
				.filter(c -> c.getType().isAssignableFrom(type))
				.map(c -> c.getName())
				.collect(ImmutableList.toImmutableList());
	}
		
}
