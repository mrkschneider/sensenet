package com.tcb.sensenet.internal.aggregation.aggregators.table;

import java.util.List;

import com.tcb.cytoscape.cyLib.cytoApiWrappers.CyRowAdapter;
import com.tcb.common.util.Rounder;
import com.tcb.sensenet.internal.util.ObjMap;

public interface RowWriter {
	public void write(CyRowAdapter row, ObjMap value);
	public List<String> getColumns();
		
}
