package com.tcb.sensenet.internal.data;

import com.tcb.cytoscape.cyLib.cytoApiWrappers.CyRowAdapter;

public class RowAutoTypeSetter {

	public void set(CyRowAdapter row, String column, String value){
		Class<?> clazz= 
				row.getAdaptedRow().getTable().getColumn(column).getType();
		Object o = null;
		if(clazz.equals(Double.class)){
			if(value == null || value.isEmpty()) o = null;
			else o = Double.parseDouble(value);
		} 
		else if(clazz.equals(Long.class)){
			if(value == null || value.isEmpty()) o = null;
			else o = Long.parseLong(value);
		}
		else if(clazz.equals(Integer.class)){
			if(value == null || value.isEmpty()) o = null;
			else o = Integer.parseInt(value);
		}
		else {
			o = value;
		}
		
		row.getAdaptedRow().set(column, o);
	}
}
