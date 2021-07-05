package com.tcb.sensenet.internal.UI.table;

import java.util.stream.Stream;

import javax.swing.table.DefaultTableModel;

import com.tcb.sensenet.internal.data.NamespaceUtil;

public class TableModelImpl extends DefaultTableModel {
	
	public static TableModelImpl create(Object[][] data, String[] columnNames){
		columnNames = Stream.of(columnNames)
				.map(s -> NamespaceUtil.removeNamespacePrefix(s))
				.toArray(String[]::new);
		return new TableModelImpl(data,columnNames);
	}
	
	private TableModelImpl(
			Object[][] data,
            Object[] columnNames){
		super(data,columnNames);
	}
		
	@Override
	public Class<?> getColumnClass(int column) {
	        if(column < 0 || column >= getColumnCount()) 
	        	throw new IllegalArgumentException("Column index out of bounds");
	        final int rowCount = this.getRowCount();
	        for(int i=0;i<rowCount;i++){
	        	Object val = getValueAt(i,column);
	        	if(val!=null) return val.getClass();
	        }
	        return Object.class;
	      }
	 
	 @Override
	 public boolean isCellEditable(int row, int column) {
	       return false;
	    }
}

