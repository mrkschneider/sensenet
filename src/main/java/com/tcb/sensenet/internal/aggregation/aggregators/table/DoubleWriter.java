package com.tcb.sensenet.internal.aggregation.aggregators.table;

import java.util.Arrays;
import java.util.List;

import com.google.common.collect.ImmutableList;
import com.tcb.common.util.Rounder;
import com.tcb.sensenet.internal.util.ObjMap;
import com.tcb.cytoscape.cyLib.cytoApiWrappers.CyRowAdapter;
import com.tcb.cytoscape.cyLib.data.Columns;

public class DoubleWriter implements RowWriter {

	protected List<Columns> columns;
	protected List<String> keys;

	public DoubleWriter(List<Columns> columns, List<String> keys){
		this.columns = columns;
		this.keys = keys;
	}
	
	@Override
	public void write(CyRowAdapter row, ObjMap value) {
		for(int i=0;i<columns.size();i++) {
			Columns column = columns.get(i);
			String key = keys.get(i);
			Double v = value.get(key,Double.class);
			row.set(column, round(v));	
		}
	}

	@Override
	public List<String> getColumns() {
		return columns.stream()
				.map(c -> c.toString())
				.collect(ImmutableList.toImmutableList());
	}
	
	public Double round(Double x, Integer afterDecimalDigits){
		if(x==null) return null;
		return Rounder.round(x,afterDecimalDigits);
	}
	
	public Double round(Double x){
		return round(x,getRoundingDigits());
	}
		
	public Integer getRoundingDigits(){
		return 2;
	}

}
